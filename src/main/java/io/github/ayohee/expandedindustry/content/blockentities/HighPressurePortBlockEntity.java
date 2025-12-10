package io.github.ayohee.expandedindustry.content.blockentities;

import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.util.ITickingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class HighPressurePortBlockEntity extends BlockEntity implements ITickingBlockEntity {
    private static final int CAPACITY = 500;

    private FluidTank inv;

    public HighPressurePortBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        inv = new FluidTank(CAPACITY);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                EIBlockEntityTypes.HIGH_PRESSURE_PORT.get(),
                (be, context) -> {
                    if (context == null || context == be.getBlockState().getValue(BlockStateProperties.FACING).getOpposite()) {
                        return be.inv;
                    }
                    return null;
                }
        );
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", inv.writeToNBT(registries, new CompoundTag()));

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        inv.readFromNBT(registries, tag.getCompound("inventory"));
        int overflow = inv.getFluidAmount() - inv.getCapacity();
        if (overflow > 0)
            inv.drain(overflow, IFluidHandler.FluidAction.EXECUTE);

        super.loadAdditional(tag, registries);
    }
}
