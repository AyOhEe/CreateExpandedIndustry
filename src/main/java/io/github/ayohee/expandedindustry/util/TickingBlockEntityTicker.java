package io.github.ayohee.expandedindustry.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class TickingBlockEntityTicker<T extends BlockEntity> implements BlockEntityTicker<T> {

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (!blockEntity.hasLevel())
            blockEntity.setLevel(level);
        ((ITickingBlockEntity) blockEntity).tick();
    }

}
