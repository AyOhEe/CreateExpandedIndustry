package io.github.ayohee.expandedindustry.content.blockentities;

import io.github.ayohee.expandedindustry.CreateExpandedIndustry;
import io.github.ayohee.expandedindustry.multiblock.IHaveFluidStorage;
import io.github.ayohee.expandedindustry.multiblock.IMultiblockComponentBE;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.util.ITickingBlockEntity;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class HighPressurePortBlockEntity extends BlockEntity implements ITickingBlockEntity {
    private static final int CAPACITY = 500;
    private static final int TICK_FREQUENCY = 5;

    private FluidTank inv;
    private Map.Entry<ResourceKey<Fluid>, Fluid> filterFluid;
    private int delay;

    private List<Map.Entry<ResourceKey<Fluid>, Fluid>> allFluids;

    public HighPressurePortBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        inv = new FluidTank(CAPACITY);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                EIBlockEntityTypes.HIGH_PRESSURE_PORT.get(),
                (be, context) -> {
                    if (context == null
                            || context == be.getBlockState().getValue(BlockStateProperties.FACING)
                            || context == be.getBlockState().getValue(BlockStateProperties.FACING).getOpposite()) {
                        return be.inv;
                    }
                    return null;
                }
        );
    }

    @Override
    public void tick() {
        if (delay != 0) {
            delay -= 1;
            return;
        }
        delay = TICK_FREQUENCY;


        Direction facing = getBlockState().getValue(BlockStateProperties.FACING);
        boolean hasFluidStorageAhead = findFluidStorage(facing) != null;
        boolean hasFluidStorageBehind = findFluidStorage(facing.getOpposite()) != null;

        if (hasFluidStorageAhead && hasFluidStorageBehind) {
            return; // Do nothing if we're sandwiched between two forms of storage
        }

        if (hasFluidStorageAhead) {
            handleInsertion();
        }

        if (hasFluidStorageBehind) {
            handleDrainage();
        }

    }

    private void handleInsertion() {
        Direction facing = getBlockState().getValue(BlockStateProperties.FACING);
        IHaveFluidStorage storage = findFluidStorage(facing);

        int amountInserted = storage.insertFluid(inv.getFluid());
        inv.drain(amountInserted, FluidAction.EXECUTE);
    }

    private void handleDrainage() {
        Direction facing = getBlockState().getValue(BlockStateProperties.FACING);
        IHaveFluidStorage storage = findFluidStorage(facing.getOpposite());

        if (filterFluid != null) {
            inv.fill(storage.removeFluid(new FluidStack(filterFluid.getValue(), inv.getSpace())), FluidAction.EXECUTE);
        }
    }

    private IHaveFluidStorage findFluidStorage(Direction where) {
        BlockPos testPos = getBlockPos().relative(where);
        BlockEntity possibleComponent = level.getBlockEntity(testPos);

        if (!(possibleComponent instanceof IMultiblockComponentBE component)) {
            return null;
        }
        if (!(component.getController() instanceof IHaveFluidStorage storage)) {
            return null;
        }
        return storage;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", inv.writeToNBT(registries, new CompoundTag()));
        if (filterFluid != null) {
            tag.put("filter", serializeFilter());
        }

        super.saveAdditional(tag, registries);
    }

    private CompoundTag serializeFilter() {
        CompoundTag tag = new CompoundTag();
        NBTHelper.writeResourceLocation(tag, "registry", filterFluid.getKey().registry());
        NBTHelper.writeResourceLocation(tag, "location", filterFluid.getKey().location());
        return tag;
    }

    private void deserializeFilter(CompoundTag tag) {
        ResourceLocation registry = NBTHelper.readResourceLocation(tag, "registry");
        ResourceLocation location = NBTHelper.readResourceLocation(tag, "location");
        ResourceKey<Registry<Fluid>> registryKey = ResourceKey.createRegistryKey(registry);
        ResourceKey<Fluid> fluidKey = ResourceKey.create(registryKey, location);

        findFluids();
        // Might return null, but that's fine
        filterFluid = allFluids.stream().filter((kv) -> kv.getKey().compareTo(fluidKey) == 0).findFirst().get();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        inv.readFromNBT(registries, tag.getCompound("inventory"));
        int overflow = inv.getFluidAmount() - inv.getCapacity();
        if (overflow > 0)
            inv.drain(overflow, FluidAction.EXECUTE);

        if (!tag.getCompound("filter").isEmpty()) {
            deserializeFilter(tag.getCompound("filter"));
        }

        super.loadAdditional(tag, registries);
    }

    protected void findFluids() {
        if (allFluids != null) {
            return;
        }

        Optional<Registry<Fluid>> optionalRegistry = Minecraft.getInstance().getConnection().registryAccess().registry(BuiltInRegistries.FLUID.key());
        if (optionalRegistry.isEmpty()) {
            return;
        }
        Registry<Fluid> registry = optionalRegistry.get();
        Set<Map.Entry<ResourceKey<Fluid>, Fluid>> entries = registry.entrySet();
        allFluids = entries.stream().filter(
                (kv) -> kv.getValue().isSource(kv.getValue().defaultFluidState())
        ).collect(Collectors.toList());
    }

    public void cycleFilter() {
        findFluids();

        int currentIndex = -1; // We add 1, so it'll end up at 0
        if (filterFluid != null) {
            currentIndex = allFluids.indexOf(filterFluid);
        }
        currentIndex = (currentIndex + 1) % allFluids.size();
        filterFluid = allFluids.get(currentIndex);
        setChanged();
    }

    public Fluid getFilterFluid() {
        if (filterFluid == null) {
            return null;
        }
        return filterFluid.getValue();
    }
}
