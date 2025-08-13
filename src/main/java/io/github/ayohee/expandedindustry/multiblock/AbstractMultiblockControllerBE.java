package io.github.ayohee.expandedindustry.multiblock;

import io.github.ayohee.expandedindustry.util.ITickingBlockEntity;
import io.github.ayohee.expandedindustry.util.NBTHelperEI;
import net.createmod.catnip.data.Pair;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public abstract class AbstractMultiblockControllerBE extends BlockEntity implements ITickingBlockEntity, IMultiblockTooltips {
    List<BlockPos> _componentPositions = null;
    boolean initialised = false;

    protected Map<BlockPos, IMultiblockComponentBE> components = new HashMap<>();
    protected Map<BlockPos, MultiblockInventoryBE> inventories = new HashMap<>();
    protected Map<BlockPos, MultiblockFluidTankBE> fluidTanks = new HashMap<>();
    protected Map<BlockPos, MultiblockFluidInputBE> fluidInputs = new HashMap<>();
    protected Map<BlockPos, MultiblockKineticIOBE> shafts = new HashMap<>();

    boolean chunkUnloaded = false;
    boolean startedDestroy = false;


    public AbstractMultiblockControllerBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public void tick() {
        if (!initialised) {
            if (!level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                findComponents();
            }
            initialised = true;
        }
    }


    public void addComponent(IMultiblockComponentBE be) {
        components.put(be.getInstance().getBlockPos(), be);
        be.setController(this);
    }


    protected void findComponents() {
        if (!hasLevel()) {
            return;
        }
        if (_componentPositions == null) {
            return;
        }
        if (!components.isEmpty()) {
            return;
        }

        components = new HashMap<>();
        for (BlockPos pos : _componentPositions) {
            BlockEntity be = getLevel().getBlockEntity(pos);
            if (be instanceof IMultiblockComponentBE) {
                addComponent((IMultiblockComponentBE) be);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        findComponents();
        tag.put(
            "child_components",
            NBTHelper.writeCompoundList(components.entrySet(), e -> NBTHelperEI.posAsCompound(e.getKey()))
        );
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        _componentPositions = NBTHelper.readCompoundList(tag.getList("child_components", Tag.TAG_COMPOUND), NBTHelperEI::safeCompoundToPos);
    }


    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }



    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        chunkUnloaded = true;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (!chunkUnloaded)
            onDestroy();
    }

    // A "remove"-like method is ideal, as, in SmartBlockEntity, it already checks whether the chunk is unloaded
    public void onDestroy() {
        if (!initialised || !hasLevel() || getLevel().isClientSide || startedDestroy) {
            return;
        }
        startedDestroy = true;

        deconstructFunction().accept(level, getBlockPos());
    }


    public abstract BiConsumer<LevelAccessor, BlockPos> deconstructFunction();


    //FIXME bleh. Code duplication.
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        findComponents();
        List<Pair<Integer, List<Component>>> componentTooltips = new LinkedList<>();
        for (IMultiblockComponentBE be : components.values()) {
            if (be.multiblockGoggleTooltipPriority(isPlayerSneaking) != -1) {
                componentTooltips.add(Pair.of(
                        be.multiblockGoggleTooltipPriority(isPlayerSneaking),
                        be.multiblockGoggleTooltip(isPlayerSneaking)
                ));
            }
        }
        if (multiblockGoggleTooltipPriority(isPlayerSneaking) != -1) {
            componentTooltips.add(Pair.of(
                    multiblockGoggleTooltipPriority(isPlayerSneaking),
                    multiblockGoggleTooltip(isPlayerSneaking)
            ));
        }

        // The compiler gets confused unless I explicitly type this.
        Comparator<Pair<Integer, ?>> comparator = Comparator.comparingInt(Pair::getFirst);
        componentTooltips.sort(comparator.reversed()); // Descending order

        if (componentTooltips.isEmpty()) {
            return false;
        }

        for (Pair<Integer, List<Component>> tooltipPair : componentTooltips) {
            tooltip.addAll(tooltipPair.getSecond());
            tooltip.addLast(Component.literal(""));
        }
        // The last line will always be empty, so remove it
        if (!tooltip.isEmpty()) {
            tooltip.removeLast();
        }

        return !tooltip.isEmpty();
    }

    @Override
    public boolean addToTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        findComponents();
        List<Pair<Integer, List<Component>>> componentTooltips = new LinkedList<>();
        for (IMultiblockComponentBE be : components.values()) {
            if (be.multiblockTooltipPriority(isPlayerSneaking) != -1) {
                componentTooltips.add(Pair.of(
                        be.multiblockTooltipPriority(isPlayerSneaking),
                        be.multiblockTooltip(isPlayerSneaking)
                ));
            }
        }
        if (multiblockTooltipPriority(isPlayerSneaking) != -1) {
            componentTooltips.add(Pair.of(
                    multiblockTooltipPriority(isPlayerSneaking),
                    multiblockTooltip(isPlayerSneaking)
            ));
        }

        // The compiler gets confused unless I explicitly type this.
        Comparator<Pair<Integer, ?>> comparator = Comparator.comparingInt(Pair::getFirst);
        componentTooltips.sort(comparator.reversed()); // Descending order

        if (componentTooltips.isEmpty()) {
            return false;
        }

        for (Pair<Integer, List<Component>> tooltipPair : componentTooltips) {
            tooltip.addAll(tooltipPair.getSecond());
        }

        return !tooltip.isEmpty();
    }

    @Override
    public boolean containedFluidTooltip(List<Component> tooltip, boolean isPlayerSneaking, IFluidHandler handler) {
        return IMultiblockTooltips.super.containedFluidTooltip(tooltip, isPlayerSneaking, handler);
    }

    public void addInventory(MultiblockInventoryBE multiblockInventoryBE) {
        inventories.put(multiblockInventoryBE.getBlockPos(), multiblockInventoryBE);
    }

    public Predicate<FluidStack> addTank(MultiblockFluidTankBE multiblockFluidTankBE) {
        fluidTanks.put(multiblockFluidTankBE.getBlockPos(), multiblockFluidTankBE);

        return e -> true;
    }

    public Pair<Integer, Predicate<FluidStack>> addFluidInput(MultiblockFluidInputBE multiblockFluidInputBE) {
        fluidInputs.put(multiblockFluidInputBE.getBlockPos(), multiblockFluidInputBE);

        return Pair.of(0, e -> true);
    }

    public void addShaft(MultiblockKineticIOBE multiblockKineticIOBE) {
        shafts.put(multiblockKineticIOBE.getBlockPos(), multiblockKineticIOBE);
    }

    protected MultiblockKineticIOBE getFirstPoweredShaft() {
        if (shafts.isEmpty()) {
            return null;
        }

        for (MultiblockKineticIOBE be : shafts.values()) {
            if (be.getConfiguredStressImpact() == 0) {
                continue;
            }
            return be;
        }

        return null;
    }

    protected MultiblockFluidInputBE getFirstFluidInput() {
        if (fluidInputs.isEmpty()) {
            return null;
        }

        return fluidInputs.values().iterator().next();
    }

    protected MultiblockFluidTankBE getFirstFluidTank() {
        if (fluidTanks.isEmpty()) {
            return null;
        }

        return fluidTanks.values().iterator().next();
    }
}
