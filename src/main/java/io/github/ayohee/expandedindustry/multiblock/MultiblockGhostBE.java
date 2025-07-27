package io.github.ayohee.expandedindustry.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MultiblockGhostBE extends BlockEntity implements IMultiblockComponentBE {
    MultiblockControllerBE controller = null;

    public MultiblockGhostBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public BlockEntity getInstance() {
        return this;
    }

    @Override
    public void setController(MultiblockControllerBE mbc) {
        controller = mbc;
    }

    @Override
    public MultiblockControllerBE getController() {
        return controller;
    }
}
