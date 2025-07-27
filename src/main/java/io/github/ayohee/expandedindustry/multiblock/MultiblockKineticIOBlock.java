package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.util.ConstSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class MultiblockKineticIOBlock extends KineticMultiblockComponent<MultiblockKineticIOBE> {
    public static final ConstSupplier<BlockState> KIO_NORTH = new ConstSupplier<>(() -> EIBlocks.MULTIBLOCK_KINETIC_IO.getDefaultState()
            .setValue(BlockStateProperties.FACING, Direction.NORTH));
    public static final ConstSupplier<BlockState> KIO_EAST = new ConstSupplier<>(() -> EIBlocks.MULTIBLOCK_KINETIC_IO.getDefaultState()
            .setValue(BlockStateProperties.FACING, Direction.EAST));
    public static final ConstSupplier<BlockState> KIO_SOUTH = new ConstSupplier<>(() -> EIBlocks.MULTIBLOCK_KINETIC_IO.getDefaultState()
            .setValue(BlockStateProperties.FACING, Direction.SOUTH));
    public static final ConstSupplier<BlockState> KIO_WEST = new ConstSupplier<>(() -> EIBlocks.MULTIBLOCK_KINETIC_IO.getDefaultState()
            .setValue(BlockStateProperties.FACING, Direction.WEST));
    public static final ConstSupplier<BlockState> KIO_UP = new ConstSupplier<>(() -> EIBlocks.MULTIBLOCK_KINETIC_IO.getDefaultState()
            .setValue(BlockStateProperties.FACING, Direction.UP));
    public static final ConstSupplier<BlockState> KIO_DOWN = new ConstSupplier<>(() -> EIBlocks.MULTIBLOCK_KINETIC_IO.getDefaultState()
            .setValue(BlockStateProperties.FACING, Direction.DOWN));

    public MultiblockKineticIOBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(BlockStateProperties.FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.FACING);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(BlockStateProperties.FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        Direction facing = state.getValue(BlockStateProperties.FACING);
        return face == facing;
    }

    @Override
    public Class<MultiblockKineticIOBE> getBlockEntityClass() {
        return MultiblockKineticIOBE.class;
    }

    @Override
    public BlockEntityType<? extends MultiblockKineticIOBE> getBlockEntityType() {
        return EIBlockEntityTypes.MULTIBLOCK_KINETIC_IO.get();
    }
}
