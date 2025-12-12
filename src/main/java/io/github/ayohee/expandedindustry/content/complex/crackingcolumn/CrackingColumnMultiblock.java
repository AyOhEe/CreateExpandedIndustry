package io.github.ayohee.expandedindustry.content.complex.crackingcolumn;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockController;
import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockControllerBE;
import io.github.ayohee.expandedindustry.multiblock.IMultiblockComponentBE;
import io.github.ayohee.expandedindustry.multiblock.MultiblockGhostBlock;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.util.ConstSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;


import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class CrackingColumnMultiblock extends AbstractMultiblockController<CrackingColumnMultiblockBE> implements IWrenchable {
    public static final IntegerProperty SIZE = IntegerProperty.create("size", 1, 3);

    public static final ConstSupplier<BlockState> SIZE_1 = new ConstSupplier(
            () -> EIBlocks.CRACKING_COLUMN_MULTIBLOCK.getDefaultState()
                    .setValue(SIZE, 1)
    );
    public static final ConstSupplier<BlockState> SIZE_2 = new ConstSupplier(
            () -> EIBlocks.CRACKING_COLUMN_MULTIBLOCK.getDefaultState()
                    .setValue(SIZE, 2)
    );
    public static final ConstSupplier<BlockState> SIZE_3 = new ConstSupplier(
            () -> EIBlocks.CRACKING_COLUMN_MULTIBLOCK.getDefaultState()
                    .setValue(SIZE, 3)
    );

    public CrackingColumnMultiblock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(SIZE, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(SIZE);
    }

    @Override
    public Class<CrackingColumnMultiblockBE> getBlockEntityClass() {
        return CrackingColumnMultiblockBE.class;
    }

    @Override
    public BlockEntityType<? extends CrackingColumnMultiblockBE> getBlockEntityType() {
        return EIBlockEntityTypes.CRACKING_COLUMN_MULTIBLOCK.get();
    }

    public static void placeMBS(LevelAccessor level, Vec3i lowestNorthWest, Vec3i dimensions) {
        // We're given the extents of the tank, but there's a layer of base blocks below, so account for that.
        // Place a layer of ghost blocks and then fill in as necessary
        int startingX = lowestNorthWest.getX();
        int endX = lowestNorthWest.getX() + dimensions.getX();
        int startingY = lowestNorthWest.getY() - 1;
        int endY = lowestNorthWest.getY() + dimensions.getY();
        int startingZ = lowestNorthWest.getZ();
        int endZ = lowestNorthWest.getZ() + dimensions.getZ();
        for (int x = startingX; x < endX; x++) {
            for (int y = startingY; y < endY; y++) {
                for (int z = startingZ; z < endZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    level.setBlock(pos, MultiblockGhostBlock.MULTIBLOCK_GHOST.get(), Block.UPDATE_ALL);
                }
            }
        }


        // Line structure with model blocks
        int tankWidth = dimensions.getX();
        Vec3i modelStart = lowestNorthWest.below();
        if (tankWidth != 1) {
            modelStart = modelStart.south().east();
        }

        BlockState bottom = CrackingColumnModelBlock.getBottomBS(tankWidth);
        BlockState middle = CrackingColumnModelBlock.getMiddleBS(tankWidth);
        BlockState top = getTopBS(tankWidth);
        level.setBlock(new BlockPos(modelStart), bottom, Block.UPDATE_ALL);

        for (int y = startingY + 1; y < endY - 1; y++) {
            level.setBlock(new BlockPos(modelStart.getX(), y, modelStart.getZ()), middle, Block.UPDATE_ALL);
        }

        // The top model is the controller
        BlockPos controllerPos = new BlockPos(modelStart).above(dimensions.getY());
        level.setBlock(controllerPos, top, Block.UPDATE_ALL);
        CrackingColumnMultiblockBE controller = (CrackingColumnMultiblockBE) level.getBlockEntity(controllerPos);

        //TODO fluid inputs

        // Inform the controller of its new children
        for (int x = startingX; x < endX; x++) {
            for (int y = startingY; y < endY; y++) {
                for (int z = startingZ; z < endZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockEntity component = level.getBlockEntity(pos);

                    if (component instanceof IMultiblockComponentBE imcBE) {
                        controller.addComponent(imcBE);
                    }

                }
            }
        }
    }

    public static void deconstructMBS(LevelAccessor level, BlockPos corePos, int size) {
        int x0 = corePos.getX();
        int yTop = corePos.getY();
        int z0 = corePos.getZ();

        int height = getColumnHeight(level, corePos);

        // For size = 2, the multiblock controller is in the highest and southeastern-most block of the structure,
        // so going one block northwest puts us at the lowest x and z.
        // Similarly, for size = 3, the multiblock controller is in the center highest block of the structure,
        // so going one block northwest puts us at the lowest x and z.
        // For size = 1, the structure it only one block wide, so we're already centred.
        int xStart = x0;
        int yStart = yTop - (height - 1);
        int zStart = z0;
        if (size != 1) {
            xStart -= 1;
            zStart -= 1;
        }

        int xEnd = xStart + size;
        int yEnd = yTop + 1;
        int zEnd = zStart + size;

        // Place column blocks
        for (int x = xStart; x < xEnd; x++) {
            for (int z = zStart; z < zEnd; z++) {
                level.setBlock(new BlockPos(x, yStart, z), EIBlocks.CRACKING_COLUMN_BASE.getDefaultState(), Block.UPDATE_ALL);
            }
        }

        // Place tanks
        for (int x = xStart; x < xEnd; x++) {
            for (int z = zStart; z < zEnd; z++) {
                for (int y = yStart + 1; y < yEnd; y++) {
                    level.setBlock(new BlockPos(x, y, z), EIBlocks.PRESSURISED_FLUID_TANK.getDefaultState(), Block.UPDATE_ALL);
                }
            }
        }
    }

    private static int getColumnHeight(LevelAccessor level, BlockPos corePos) {
        int height = 0;
        BlockPos pos = corePos;
        while (isColumnBlock(level, pos)) {
            height += 1;
            pos = pos.below();
        }

        return height;
    }

    //FIXME this is definitely buggy
    private static boolean isColumnBlock(LevelAccessor level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        return (be instanceof CrackingColumnMultiblockBE) || (be instanceof IMultiblockComponentBE);
    }

    public static BlockState getTopBS(int size) {
        return switch(size) {
            case 1 -> SIZE_1.get();
            case 2 -> SIZE_2.get();
            default -> SIZE_3.get();
        };
    }


    public static void generateBlockstate(DataGenContext<Block, CrackingColumnMultiblock> ctx, RegistrateBlockstateProvider prov) {
        ResourceLocation modelFile = ResourceLocation.fromNamespaceAndPath(MODID, "block/cracking_column/cracking_column_");
        prov.getVariantBuilder(ctx.get())
                .forAllStates(state -> {
                    String size = state.getValue(SIZE).toString();

                    return ConfiguredModel.builder()
                            .modelFile(prov.models().getExistingFile(modelFile.withSuffix("top_" + size + "_wide")))
                            .build();
                });
    }
}
