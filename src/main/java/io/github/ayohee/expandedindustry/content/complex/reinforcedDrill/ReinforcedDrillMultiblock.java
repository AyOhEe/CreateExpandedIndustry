package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockController;
import io.github.ayohee.expandedindustry.multiblock.IMultiblockComponentBE;
import io.github.ayohee.expandedindustry.multiblock.MultiblockKineticIOBE;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.util.ConstSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

import static io.github.ayohee.expandedindustry.multiblock.MultiblockKineticIOBlock.*;
import static io.github.ayohee.expandedindustry.multiblock.MultiblockKineticIOBlock.KIO_SOUTH;

public class ReinforcedDrillMultiblock extends AbstractMultiblockController<ReinforcedDrillMultiblockBE> implements IWrenchable {
    public static final ConstSupplier<BlockState> NS_BRASS_ENCASED_SHAFT = new ConstSupplier<>(() -> AllBlocks.BRASS_ENCASED_SHAFT.getDefaultState()
            .setValue(RotatedPillarKineticBlock.AXIS, Direction.Axis.Z));
    public static final ConstSupplier<BlockState> EW_BRASS_ENCASED_SHAFT = new ConstSupplier<>(() -> AllBlocks.BRASS_ENCASED_SHAFT.getDefaultState()
            .setValue(RotatedPillarKineticBlock.AXIS, Direction.Axis.X));
    public static final ConstSupplier<BlockState> NS_COPPER_ENCASED_PIPE = new ConstSupplier<>(() -> AllBlocks.ENCASED_FLUID_PIPE.getDefaultState()
            .setValue(BlockStateProperties.NORTH, true)
            .setValue(BlockStateProperties.SOUTH, true)
            .setValue(BlockStateProperties.UP, false)
            .setValue(BlockStateProperties.DOWN, false)
            .setValue(BlockStateProperties.WEST, false)
            .setValue(BlockStateProperties.EAST, false));
    public static final ConstSupplier<BlockState> EW_COPPER_ENCASED_PIPE = new ConstSupplier<>(() -> AllBlocks.ENCASED_FLUID_PIPE.getDefaultState()
            .setValue(BlockStateProperties.NORTH, false)
            .setValue(BlockStateProperties.SOUTH, false)
            .setValue(BlockStateProperties.UP, false)
            .setValue(BlockStateProperties.DOWN, false)
            .setValue(BlockStateProperties.WEST, true)
            .setValue(BlockStateProperties.EAST, true));

    public static final ConstSupplier<BlockState> REINFORCED_DRILL_PARENT = new ConstSupplier<>(EIBlocks.REINFORCED_DRILL_MULTIBLOCK::getDefaultState);



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

    @Override
    public Class<ReinforcedDrillMultiblockBE> getBlockEntityClass() {
        return ReinforcedDrillMultiblockBE.class;
    }

    @Override
    public BlockEntityType<? extends ReinforcedDrillMultiblockBE> getBlockEntityType() {
        return EIBlockEntityTypes.REINFORCED_DRILL_MULTIBLOCK.get();
    }


    public static boolean canPlace(LevelAccessor level, BlockPos corePos) {
        return (assertBlock(level, corePos.below().east().north(), EIBlocks.DRILL_BEAM.get())
                && assertBlock(level, corePos.below().east().south(), EIBlocks.DRILL_BEAM.get())
                && assertBlock(level, corePos.below().west().north(), EIBlocks.DRILL_BEAM.get())
                && assertBlock(level, corePos.below().west().south(), EIBlocks.DRILL_BEAM.get())
                && assertReplaceable(level, corePos.below().east())
                && assertReplaceable(level, corePos.below().west())
                && assertReplaceable(level, corePos.below().north())
                && assertReplaceable(level, corePos.below().south())
                && assertBlock(level, corePos.below(), EIBlocks.DRILL_BIT.get())
                && assertBlock(level, corePos.east().north(), AllBlocks.RAILWAY_CASING.get())
                && assertBlock(level, corePos.east().south(), AllBlocks.RAILWAY_CASING.get())
                && assertBlock(level, corePos.west().north(), AllBlocks.RAILWAY_CASING.get())
                && assertBlock(level, corePos.west().south(), AllBlocks.RAILWAY_CASING.get())
                && assertPipeAndShaftChecks(level, corePos));
    }

    public static void placeMBS(LevelAccessor level, BlockPos corePos) {
        Direction fluidPipeDir = locateFluidPipe(level, corePos);
        BlockState childBS = EIBlocks.MULTIBLOCK_GHOST.getDefaultState();


        BlockState parentBS = REINFORCED_DRILL_PARENT.get().setValue(BlockStateProperties.HORIZONTAL_FACING, fluidPipeDir);
        level.setBlock(corePos, parentBS, UPDATE_ALL);
        ReinforcedDrillMultiblockBE controller = level.getBlockEntity(corePos, EIBlockEntityTypes.REINFORCED_DRILL_MULTIBLOCK.get()).orElseThrow();

        BlockPos cornerPos = corePos.below().north().west();
        int xCorner = cornerPos.getX();
        int yCorner = cornerPos.getY();
        int zCorner = cornerPos.getZ();
        for (int x = xCorner; x <= xCorner + 2; ++x) {
            for (int y = yCorner; y <= yCorner + 1; ++y) {
                for (int z = zCorner; z <= zCorner + 2; ++z) {
                    BlockPos currentPos = new BlockPos(x, y, z);
                    if (currentPos.equals(corePos))
                        continue;

                    level.setBlock(
                            currentPos,
                            childBS,
                            UPDATE_ALL
                    );
                    BlockEntity currentBE = level.getBlockEntity(currentPos);
                    if (!(currentBE instanceof IMultiblockComponentBE))
                        continue;

                    controller.addComponent((IMultiblockComponentBE) currentBE);
                }
            }
        }

        placeShaftPorts(corePos, level, controller, fluidPipeDir);
    }

    private static void placeShaftPorts(BlockPos pos, LevelAccessor level, ReinforcedDrillMultiblockBE controller, Direction fluidPipeDir) {
        switch (fluidPipeDir) {
            case NORTH, SOUTH -> {
                level.setBlock(pos.west(), KIO_WEST.get(), UPDATE_ALL);
                level.setBlock(pos.east(), KIO_EAST.get(), UPDATE_ALL);

                Optional<MultiblockKineticIOBE> west = level.getBlockEntity(pos.west(), EIBlockEntityTypes.MULTIBLOCK_KINETIC_IO.get());
                Optional<MultiblockKineticIOBE> east = level.getBlockEntity(pos.east(), EIBlockEntityTypes.MULTIBLOCK_KINETIC_IO.get());
                if (west.isEmpty() || east.isEmpty()) {
                    throw new Error();
                }

                west.get().poolWith(east.get());
                east.get().poolWith(west.get());
                west.get().setConfiguredStressImpact(256);
                west.get().setMinimumRotationSpeed(64);

                controller.addComponent(west.get());
                controller.addComponent(east.get());
            }
            case WEST, EAST  -> {
                level.setBlock(pos.north(), KIO_NORTH.get(), UPDATE_ALL);
                level.setBlock(pos.south(), KIO_SOUTH.get(), UPDATE_ALL);

                Optional<MultiblockKineticIOBE> north = level.getBlockEntity(pos.north(), EIBlockEntityTypes.MULTIBLOCK_KINETIC_IO.get());
                Optional<MultiblockKineticIOBE> south = level.getBlockEntity(pos.south(), EIBlockEntityTypes.MULTIBLOCK_KINETIC_IO.get());
                if (north.isEmpty() || south.isEmpty()) {
                    throw new Error();
                }

                north.get().poolWith(south.get());
                south.get().poolWith(north.get());
                north.get().setConfiguredStressImpact(256);
                north.get().setMinimumRotationSpeed(64);

                controller.addComponent(north.get());
                controller.addComponent(south.get());
            }
        }
    }

    private static Direction locateFluidPipe(LevelAccessor level, BlockPos pos) {
        BlockState west = level.getBlockState(pos.west());
        BlockState east = level.getBlockState(pos.east());
        BlockState north = level.getBlockState(pos.north());
        BlockState south = level.getBlockState(pos.south());

        if (west == EW_COPPER_ENCASED_PIPE.get()) {
            return Direction.WEST;
        }
        else if (east == EW_COPPER_ENCASED_PIPE.get()) {
            return Direction.EAST;
        }
        else if (north == NS_COPPER_ENCASED_PIPE.get()) {
            return Direction.NORTH;
        }
        else if (south == NS_COPPER_ENCASED_PIPE.get()) {
            return Direction.SOUTH;
        }
        return null;
    }

    private static boolean assertPipeAndShaftChecks(LevelAccessor level, BlockPos pos) {
        BlockState west = level.getBlockState(pos.west());
        BlockState east = level.getBlockState(pos.east());
        BlockState north = level.getBlockState(pos.north());
        BlockState south = level.getBlockState(pos.south());

        boolean NSAreShafts = (north == NS_BRASS_ENCASED_SHAFT.get()) && (south == NS_BRASS_ENCASED_SHAFT.get());
        boolean NIsPipe = (north == NS_COPPER_ENCASED_PIPE.get()) && (south == AllBlocks.BRASS_CASING.getDefaultState());
        boolean SIsPipe = (north == AllBlocks.BRASS_CASING.getDefaultState()) && (south == NS_COPPER_ENCASED_PIPE.get());
        boolean EWAreShafts = (east == EW_BRASS_ENCASED_SHAFT.get()) && (west == EW_BRASS_ENCASED_SHAFT.get());
        boolean EIsPipe = (east == EW_COPPER_ENCASED_PIPE.get()) && (west == AllBlocks.BRASS_CASING.getDefaultState());
        boolean WIsPipe = (east == AllBlocks.BRASS_CASING.getDefaultState()) && (west == EW_COPPER_ENCASED_PIPE.get());

        return (NSAreShafts && (EIsPipe || WIsPipe)) || ((NIsPipe || SIsPipe) && EWAreShafts);
    }

    private static boolean assertReplaceable(LevelAccessor level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.canBeReplaced();
    }

    private static boolean assertBlock(LevelAccessor level, BlockPos pos, Block block) {
        return level.getBlockState(pos).getBlock() == block;
    }

    //FIXME temporary and bad and awful and stinky
    public static void deconstructMBS(LevelAccessor level, BlockPos corePos) {
        for (int x = corePos.getX() - 1; x <= corePos.getX() + 1; x++) {
            for (int y = corePos.getY() - 1; y <= corePos.getY(); y++) {
                for (int z = corePos.getZ() - 1; z <= corePos.getZ() + 1; z++) {
                    level.setBlock(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                }
            }
        }
    }
}
