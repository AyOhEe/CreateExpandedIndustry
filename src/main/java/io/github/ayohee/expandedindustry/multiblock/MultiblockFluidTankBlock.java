package io.github.ayohee.expandedindustry.multiblock;

import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.util.ConstSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class MultiblockFluidTankBlock extends AbstractMultiblockComponent<MultiblockFluidTankBE> {
    public static final ConstSupplier<BlockState> FIO_NORTH = new ConstSupplier<>(() -> EIBlocks.MULTIBLOCK_FLUID_IO.getDefaultState()
            .setValue(BlockStateProperties.FACING, Direction.NORTH));
    public static final ConstSupplier<BlockState> FIO_EAST = new ConstSupplier<>(() -> EIBlocks.MULTIBLOCK_FLUID_IO.getDefaultState()
            .setValue(BlockStateProperties.FACING, Direction.EAST));
    public static final ConstSupplier<BlockState> FIO_SOUTH = new ConstSupplier<>(() -> EIBlocks.MULTIBLOCK_FLUID_IO.getDefaultState()
            .setValue(BlockStateProperties.FACING, Direction.SOUTH));
    public static final ConstSupplier<BlockState> FIO_WEST = new ConstSupplier<>(() -> EIBlocks.MULTIBLOCK_FLUID_IO.getDefaultState()
            .setValue(BlockStateProperties.FACING, Direction.WEST));
    public static final ConstSupplier<BlockState> FIO_UP = new ConstSupplier<>(() -> EIBlocks.MULTIBLOCK_FLUID_IO.getDefaultState()
            .setValue(BlockStateProperties.FACING, Direction.UP));
    public static final ConstSupplier<BlockState> FIO_DOWN = new ConstSupplier<>(() -> EIBlocks.MULTIBLOCK_FLUID_IO.getDefaultState()
            .setValue(BlockStateProperties.FACING, Direction.DOWN));


    public MultiblockFluidTankBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(BlockStateProperties.FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.FACING);
    }


    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected float getShadeBrightness(BlockState p_308911_, BlockGetter p_308952_, BlockPos p_308918_) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }

    @Override
    public Class<MultiblockFluidTankBE> getBlockEntityClass() {
        return MultiblockFluidTankBE.class;
    }

    @Override
    public BlockEntityType<? extends MultiblockFluidTankBE> getBlockEntityType() {
        return EIBlockEntityTypes.MULTIBLOCK_FLUID_IO.get();
    }
}
