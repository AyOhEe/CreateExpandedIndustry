package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import io.github.ayohee.expandedindustry.multiblock.MultiblockController;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ReinforcedDrillMultiblock extends MultiblockController implements IWrenchable {
    public ReinforcedDrillMultiblock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        return InteractionResult.PASS;
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
}
