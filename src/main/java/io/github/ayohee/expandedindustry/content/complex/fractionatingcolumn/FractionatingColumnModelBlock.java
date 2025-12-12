package io.github.ayohee.expandedindustry.content.complex.fractionatingcolumn;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import io.github.ayohee.expandedindustry.multiblock.MultiblockModelBE;
import io.github.ayohee.expandedindustry.multiblock.MultiblockModelBlock;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.util.ConstSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class FractionatingColumnModelBlock extends MultiblockModelBlock<MultiblockModelBE> {
    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
    public static final IntegerProperty SIZE = IntegerProperty.create("size", 1, 3);

    public static final ConstSupplier<BlockState> BOTTOM_1 = new ConstSupplier<>(
            () -> EIBlocks.FRACTIONATING_COLUMN_MODEL.getDefaultState()
                    .setValue(BOTTOM, true)
                    .setValue(SIZE, 1)
    );
    public static final ConstSupplier<BlockState> MIDDLE_1 = new ConstSupplier<>(
            () -> EIBlocks.FRACTIONATING_COLUMN_MODEL.getDefaultState()
                    .setValue(BOTTOM, false)
                    .setValue(SIZE, 1)
    );
    public static final ConstSupplier<BlockState> BOTTOM_2 = new ConstSupplier<>(
            () -> EIBlocks.FRACTIONATING_COLUMN_MODEL.getDefaultState()
                    .setValue(BOTTOM, true)
                    .setValue(SIZE, 2)
    );
    public static final ConstSupplier<BlockState> MIDDLE_2 = new ConstSupplier<>(
            () -> EIBlocks.FRACTIONATING_COLUMN_MODEL.getDefaultState()
                    .setValue(BOTTOM, false)
                    .setValue(SIZE, 2)
    );
    public static final ConstSupplier<BlockState> BOTTOM_3 = new ConstSupplier<>(
            () -> EIBlocks.FRACTIONATING_COLUMN_MODEL.getDefaultState()
                    .setValue(BOTTOM, true)
                    .setValue(SIZE, 3)
    );
    public static final ConstSupplier<BlockState> MIDDLE_3 = new ConstSupplier<>(
            () -> EIBlocks.FRACTIONATING_COLUMN_MODEL.getDefaultState()
                    .setValue(BOTTOM, false)
                    .setValue(SIZE, 3)
    );

    public FractionatingColumnModelBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(BOTTOM, false)
                .setValue(SIZE, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(BOTTOM, SIZE);
    }

    @Override
    public Class<MultiblockModelBE> getBlockEntityClass() {
        return MultiblockModelBE.class;
    }

    @Override
    public BlockEntityType<MultiblockModelBE> getBlockEntityType() {
        return EIBlockEntityTypes.FRACTIONATING_COLUMN_MODEL.get();
    }

    public static void generateBlockstate(DataGenContext<Block, FractionatingColumnModelBlock> ctx, RegistrateBlockstateProvider prov) {
        ResourceLocation modelFile = ResourceLocation.fromNamespaceAndPath(MODID, "block/fractionating_column/fractionating_column_");
        prov.getVariantBuilder(ctx.get())
                .forAllStates(state -> {
                    String slice = "middle";
                    if (state.getValue(FractionatingColumnModelBlock.BOTTOM)) {
                        slice = "bottom";
                    }
                    String size = state.getValue(FractionatingColumnModelBlock.SIZE).toString();

                    return ConfiguredModel.builder()
                            .modelFile(prov.models().getExistingFile(modelFile.withSuffix(slice + "_" + size + "_wide")))
                            .build();
                });
    }

    public static BlockState getMiddleBS(int size) {
        return switch (size) {
            case 1 -> MIDDLE_1.get();
            case 2 -> MIDDLE_2.get();
            default -> MIDDLE_3.get();
        };
    }

    public static BlockState getBottomBS(int size) {
        return switch (size) {
            case 1 -> BOTTOM_1.get();
            case 2 -> BOTTOM_2.get();
            default -> BOTTOM_3.get();
        };
    }
}
