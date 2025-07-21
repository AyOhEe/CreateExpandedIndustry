package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import io.github.ayohee.expandedindustry.content.blocks.WrenchableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ReinforcedDrillMultiblock extends WrenchableBlock {
    public static final BooleanProperty MULTIBLOCK_CHILD = BooleanProperty.create("multiblock_child");

    public ReinforcedDrillMultiblock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(MULTIBLOCK_CHILD, true)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(MULTIBLOCK_CHILD, BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        if (state.getValue(MULTIBLOCK_CHILD)) {
            return RenderShape.INVISIBLE;
        }
        else {
            return RenderShape.MODEL;
        }
    }

    @Override
    protected float getShadeBrightness(BlockState p_308911_, BlockGetter p_308952_, BlockPos p_308918_) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }
}
