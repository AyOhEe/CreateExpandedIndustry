package io.github.ayohee.expandedindustry.content.complex.crackingcolumn;

import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockControllerBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class CrackingColumnMultiblockBE extends AbstractMultiblockControllerBE {
    private final int size;

    public CrackingColumnMultiblockBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        size = blockState.getValue(CrackingColumnMultiblock.SIZE);
    }

    @Override
    public void deconstruct(LevelAccessor level, BlockPos pos) {
        CrackingColumnMultiblock.deconstructMBS(level, pos, size);
    }

    @Override
    public List<Component> multiblockTooltip(boolean isPlayerSneaking) {
        return List.of(Component.literal("    Hello, world!"));
    }

    @Override
    public int multiblockTooltipPriority(boolean isPlayerSneaking) {
        return 5;
    }
}
