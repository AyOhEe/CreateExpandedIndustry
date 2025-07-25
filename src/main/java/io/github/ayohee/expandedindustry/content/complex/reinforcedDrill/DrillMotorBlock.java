package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import io.github.ayohee.expandedindustry.content.blocks.WrenchableBlock;
import io.github.ayohee.expandedindustry.multiblock.MultiblockKineticIOBE;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.util.ConstSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

import static io.github.ayohee.expandedindustry.multiblock.MultiblockKineticIOBlock.*;


public class DrillMotorBlock extends WrenchableBlock {
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


    public DrillMotorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        // Must be performed by a player
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }

        // Ensure that all blocks are present, and in the correct blockstate
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!(assertBlock(level, pos.below().east().north(), EIBlocks.DRILL_BEAM.get())
           && assertBlock(level, pos.below().east().south(), EIBlocks.DRILL_BEAM.get())
           && assertBlock(level, pos.below().west().north(), EIBlocks.DRILL_BEAM.get())
           && assertBlock(level, pos.below().west().south(), EIBlocks.DRILL_BEAM.get())
           && assertReplaceable(level, pos.below().east())
           && assertReplaceable(level, pos.below().west())
           && assertReplaceable(level, pos.below().north())
           && assertReplaceable(level, pos.below().south())
           && assertBlock(level, pos.below(), EIBlocks.DRILL_BIT.get())
           && assertBlock(level, pos.east().north(), AllBlocks.RAILWAY_CASING.get())
           && assertBlock(level, pos.east().south(), AllBlocks.RAILWAY_CASING.get())
           && assertBlock(level, pos.west().north(), AllBlocks.RAILWAY_CASING.get())
           && assertBlock(level, pos.west().south(), AllBlocks.RAILWAY_CASING.get())
           && assertPipeAndShaftChecks(level, pos))) {
            player.displayClientMessage(Component.literal("Incorrect assembly!"), true);
            return InteractionResult.FAIL;
        }

        // Early return - this should only happen on the server, and we know the placement will succeed by now
        // The client should NOT place the multiblock, only *trigger* the placement.
        if (!(context.getLevel() instanceof ServerLevel)) {
            player.displayClientMessage(Component.literal("Successful assembly!"), true);
            return InteractionResult.SUCCESS;
        }

        Direction fluidPipeDir = locateFluidPipe(level, pos);
        BlockState childBS = EIBlocks.MULTIBLOCK_GHOST.getDefaultState();


        BlockPos cornerPos = pos.below().north().west();
        int xCorner = cornerPos.getX();
        int yCorner = cornerPos.getY();
        int zCorner = cornerPos.getZ();
        for (int x = xCorner; x <= xCorner + 2; ++x) {
            for (int y = yCorner; y <= yCorner + 1; ++y) {
                for (int z = zCorner; z <= zCorner + 2; ++z) {
                    level.setBlock(
                            new BlockPos(x, y, z),
                            childBS,
                            UPDATE_ALL
                    );
                }
            }
        }

        BlockState parentBS = REINFORCED_DRILL_PARENT.get().setValue(BlockStateProperties.HORIZONTAL_FACING, fluidPipeDir);
        level.setBlock(pos, parentBS, UPDATE_ALL);

        placeShaftPorts(pos, level, fluidPipeDir);

        return InteractionResult.SUCCESS;
    }

    private void placeShaftPorts(BlockPos pos, Level level, Direction fluidPipeDir) {
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
            }
        }
    }

    private Direction locateFluidPipe(Level level, BlockPos pos) {
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

    private boolean assertPipeAndShaftChecks(Level level, BlockPos pos) {
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

    private boolean assertReplaceable(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.canBeReplaced();
    }

    private boolean assertBlock(Level level, BlockPos pos, Block block) {
        return level.getBlockState(pos).getBlock() == block;
    }
}
