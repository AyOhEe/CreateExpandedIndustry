package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.foundation.block.IBE;
import io.github.ayohee.expandedindustry.content.complex.reinforcedDrill.ReinforcedDrillMultiblockBE;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.util.TickingBlockEntityTicker;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractMultiblockController<T extends AbstractMultiblockControllerBE> extends Block implements IBE<T> {
    public AbstractMultiblockController(Properties properties) {
        super(properties);
    }

    @Override
    public <S extends BlockEntity> BlockEntityTicker<S> getTicker(Level level, BlockState blockState, BlockEntityType<S> blockEntityType) {
        return new TickingBlockEntityTicker<>();
    }


    // Helper methods for constructing multiblock structures
    public static void placeShaftPorts(List<Pair<BlockPos, BlockState>> states, LevelAccessor level, ReinforcedDrillMultiblockBE controller) {
        List<MultiblockKineticIOBE> blockEntities = new LinkedList<>();
        for (Pair<BlockPos, BlockState> statePair : states) {
            BlockPos pos = statePair.getFirst();
            BlockState state = statePair.getSecond();

            level.setBlock(pos, state, Block.UPDATE_ALL);
            MultiblockKineticIOBE be = level.getBlockEntity(pos, EIBlockEntityTypes.MULTIBLOCK_KINETIC_IO.get()).orElseThrow();

            controller.addComponent(be);
            blockEntities.add(be);
        }

        // We're working on i and i + 1, so ignore the last element such that it isn't suddenly out of range
        for (int i = 0; i < blockEntities.size() - 1; i++) {
            blockEntities.get(i).poolWith(blockEntities.get(i + 1));
            blockEntities.get(i + 1).poolWith(blockEntities.get(i));
        }
    }

    public static boolean assertReplaceable(LevelAccessor level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.canBeReplaced();
    }

    public static boolean assertBlock(LevelAccessor level, BlockPos pos, Block block) {
        return level.getBlockState(pos).getBlock() == block;
    }
}
