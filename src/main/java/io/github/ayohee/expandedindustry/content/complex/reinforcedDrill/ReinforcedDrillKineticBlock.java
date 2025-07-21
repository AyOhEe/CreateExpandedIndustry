package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ReinforcedDrillKineticBlock extends KineticBlock implements IBE<ReinforcedDrillKineticBlockEntity>, IWrenchable {
    public ReinforcedDrillKineticBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        return face == facing;
    }

    @Override
    public Class<ReinforcedDrillKineticBlockEntity> getBlockEntityClass() {
        return ReinforcedDrillKineticBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ReinforcedDrillKineticBlockEntity> getBlockEntityType() {
        return EIBlockEntityTypes.REINFORCED_DRILL_KINETIC_BLOCK.get();
    }
}
