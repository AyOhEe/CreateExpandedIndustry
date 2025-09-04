package io.github.ayohee.expandedindustry.content.complex.crackingcolumn;

import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockControllerBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CrackingColumnMultiblockBE extends AbstractMultiblockControllerBE {
    public CrackingColumnMultiblockBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public void deconstruct(LevelAccessor level, BlockPos pos) {
        //TODO
    }
}
