package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import io.github.ayohee.expandedindustry.content.blocks.WrenchableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        if (!state.getValue(MULTIBLOCK_CHILD)) {
            return rotateSelfAndChildren(state, context);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult rotateSelfAndChildren(BlockState state, UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction faceDirection = context.getClickedFace();

        level.setBlock(pos, getRotatedBlockState(state, faceDirection), UPDATE_ALL);
        for (BlockPos cPos : getChildPositions(pos)) {
            level.setBlock(cPos, getRotatedBlockState(level.getBlockState(cPos), faceDirection), UPDATE_ALL);
        }

        IWrenchable.playRotateSound(level, pos);

        return InteractionResult.SUCCESS;
    }

    private BlockPos[] getChildPositions(BlockPos pos) {
        BlockPos[] positions = new BlockPos[17];

        int i = 0;
        BlockPos cPos;
        for (int x = -1; x < 2; ++x) {
            for (int y = -1; y < 1; ++y) {
                for (int z = -1; z < 2; ++z) {
                    cPos = pos.east(x).above(y).south(z);
                    if (x == 0 && y == 0 && z == 0) {
                        continue;
                    }

                    positions[i] = new BlockPos(cPos);

                    i += 1;
                }
            }
        }

        return positions;
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
