package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class MultiblockControllerBETicker<T extends BlockEntity> implements BlockEntityTicker<T> {

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (!blockEntity.hasLevel())
            blockEntity.setLevel(level);
        ((MultiblockControllerBE) blockEntity).tick();
    }

}
