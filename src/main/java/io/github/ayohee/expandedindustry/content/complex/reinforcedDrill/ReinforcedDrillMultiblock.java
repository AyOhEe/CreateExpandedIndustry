package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockController;
import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockControllerBE;
import io.github.ayohee.expandedindustry.multiblock.IMultiblockComponentBE;
import io.github.ayohee.expandedindustry.multiblock.placement.*;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.util.ConstSupplier;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
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

import java.util.List;
import java.util.Map;

import static io.github.ayohee.expandedindustry.multiblock.MultiblockGhostBlock.MULTIBLOCK_GHOST;
import static io.github.ayohee.expandedindustry.multiblock.MultiblockKineticIOBlock.*;
import static io.github.ayohee.expandedindustry.multiblock.placement.PlacementTest.blockMatches;
import static io.github.ayohee.expandedindustry.multiblock.placement.PlacementTest.blockStateMatches;

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

    public static final ConstSupplier<BlockState> DRILL_CONTROLLER_NORTH = new ConstSupplier<>(() -> EIBlocks.REINFORCED_DRILL_MULTIBLOCK
        .getDefaultState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    public static final ConstSupplier<BlockState> DRILL_CONTROLLER_EAST = new ConstSupplier<>(() -> EIBlocks.REINFORCED_DRILL_MULTIBLOCK
        .getDefaultState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST));
    public static final ConstSupplier<BlockState> DRILL_CONTROLLER_SOUTH = new ConstSupplier<>(() -> EIBlocks.REINFORCED_DRILL_MULTIBLOCK
        .getDefaultState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH));
    public static final ConstSupplier<BlockState> DRILL_CONTROLLER_WEST = new ConstSupplier<>(() -> EIBlocks.REINFORCED_DRILL_MULTIBLOCK
        .getDefaultState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST));



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


    public static final HorizontalPlacementSet PLACEMENT_SET =
        new HorizontalPlacementSet(
            new PlacementTest()
                .addLayer(List.of(
                    "B B",
                    " D ",
                    "B B"))
                .addLayer(List.of(
                    "TPT",
                    "SMS",
                    "TCT"))
                .define('B', blockMatches(EIBlocks.DRILL_BEAM::get))
                .define('D', blockMatches(EIBlocks.DRILL_BIT::get))
                .define('M', blockMatches(EIBlocks.DRILL_MOTOR::get))
                .define('T', blockMatches(AllBlocks.RAILWAY_CASING::get))
                .define('C', blockMatches(AllBlocks.BRASS_CASING::get))
                .define('P', blockStateMatches(NS_COPPER_ENCASED_PIPE))
                .define('S', blockStateMatches(EW_BRASS_ENCASED_SHAFT))
                .setOrigin(new Vec3i(1, 1, 1))
            ).reDefine('P',
                Map.of(
                    Direction.NORTH, blockStateMatches(NS_COPPER_ENCASED_PIPE),
                    Direction.EAST, blockStateMatches(EW_COPPER_ENCASED_PIPE),
                    Direction.SOUTH, blockStateMatches(NS_COPPER_ENCASED_PIPE),
                    Direction.WEST, blockStateMatches(EW_COPPER_ENCASED_PIPE)
                )
            ).reDefine('S',
                Map.of(
                    Direction.NORTH, blockStateMatches(EW_BRASS_ENCASED_SHAFT),
                    Direction.EAST, blockStateMatches(NS_BRASS_ENCASED_SHAFT),
                    Direction.SOUTH, blockStateMatches(EW_BRASS_ENCASED_SHAFT),
                    Direction.WEST, blockStateMatches(NS_BRASS_ENCASED_SHAFT)
                )
        );

    public static final HorizontalMultiblockBuilder MULTIBLOCK_BUILDER =
        new HorizontalMultiblockBuilder(
            new MultiblockBuilder()
                .addLayer(List.of(
                    "GGG",
                    "GGG",
                    "GGG"))
                .addLayer(List.of(
                    "GGG",
                    "LCR",
                    "GGG"))
                .define('G', MULTIBLOCK_GHOST)
                .define('L', KIO_EAST)
                .define('R', KIO_WEST)
                .define('C', DRILL_CONTROLLER_NORTH)
                .setOrigin(new Vec3i(1, 1, 1))
                .kineticStats(256, 64)
            ).reDefine('L',
                Map.of(
                    Direction.NORTH, KIO_WEST,
                    Direction.EAST, KIO_NORTH,
                    Direction.SOUTH, KIO_EAST,
                    Direction.WEST, KIO_SOUTH
                )
            ).reDefine('R',
                Map.of(
                    Direction.NORTH, KIO_EAST,
                    Direction.EAST, KIO_SOUTH,
                    Direction.SOUTH, KIO_WEST,
                    Direction.WEST, KIO_NORTH
                )
            ).reDefine('C',
                Map.of(
                    Direction.NORTH, DRILL_CONTROLLER_NORTH,
                    Direction.EAST, DRILL_CONTROLLER_EAST,
                    Direction.SOUTH, DRILL_CONTROLLER_SOUTH,
                    Direction.WEST, DRILL_CONTROLLER_WEST
                )
            );

    public static HorizontalGeneralBuilder<GeneralBuilder> MULTIBLOCK_DECONSTRUCTOR =
        new HorizontalGeneralBuilder<>(
            new GeneralBuilder()
                .addLayer(List.of(
                    "B B",
                    " D ",
                    "B B"))
                .addLayer(List.of(
                    "TPT",
                    "SMS",
                    "TCT"))
                .define('B', EIBlocks.DRILL_BEAM::getDefaultState)
                .define('D', EIBlocks.DRILL_BIT::getDefaultState)
                .define('M', EIBlocks.DRILL_MOTOR::getDefaultState)
                .define('T', AllBlocks.RAILWAY_CASING::getDefaultState)
                .define('C', AllBlocks.BRASS_CASING::getDefaultState)
                .define('P', NS_COPPER_ENCASED_PIPE)
                .define('S', EW_BRASS_ENCASED_SHAFT)
                .setOrigin(new Vec3i(1, 1, 1))
            ).reDefine('P',
                Map.of(
                    Direction.NORTH, NS_COPPER_ENCASED_PIPE,
                    Direction.EAST, EW_COPPER_ENCASED_PIPE,
                    Direction.SOUTH, NS_COPPER_ENCASED_PIPE,
                    Direction.WEST, EW_COPPER_ENCASED_PIPE
                )
            ).reDefine('S',
                Map.of(
                    Direction.NORTH, EW_BRASS_ENCASED_SHAFT,
                    Direction.EAST, NS_BRASS_ENCASED_SHAFT,
                    Direction.SOUTH, EW_BRASS_ENCASED_SHAFT,
                    Direction.WEST, NS_BRASS_ENCASED_SHAFT
                )
            );

    public static void placeMBS(LevelAccessor level, BlockPos corePos) {
        MULTIBLOCK_BUILDER.place(level, corePos, PLACEMENT_SET.findFirstPlacement(level, corePos));
    }

    public static void deconstructMBS(LevelAccessor level, BlockPos corePos) {
        AbstractMultiblockControllerBE be = (AbstractMultiblockControllerBE) level.getBlockEntity(corePos);
        MULTIBLOCK_DECONSTRUCTOR.place(level, corePos, be.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING));
    }
}
