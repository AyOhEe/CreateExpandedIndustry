package io.github.ayohee.expandedindustry.content.complex.fractionatingcolumn;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockController;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.util.ConstSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class FractionatingColumnMultiblock extends AbstractMultiblockController<FractionatingColumnMultiblockBE> implements IWrenchable {
    public static final IntegerProperty SIZE = IntegerProperty.create("size", 1, 3);

    public static final ConstSupplier<BlockState> SIZE_1 = new ConstSupplier(
            () -> EIBlocks.FRACTIONATING_COLUMN_MULTIBLOCK.getDefaultState()
                    .setValue(SIZE, 1)
    );
    public static final ConstSupplier<BlockState> SIZE_2 = new ConstSupplier(
            () -> EIBlocks.FRACTIONATING_COLUMN_MULTIBLOCK.getDefaultState()
                    .setValue(SIZE, 2)
    );
    public static final ConstSupplier<BlockState> SIZE_3 = new ConstSupplier(
            () -> EIBlocks.FRACTIONATING_COLUMN_MULTIBLOCK.getDefaultState()
                    .setValue(SIZE, 3)
    );

    public FractionatingColumnMultiblock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(SIZE, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(SIZE);
    }

    @Override
    public Class<FractionatingColumnMultiblockBE> getBlockEntityClass() {
        return FractionatingColumnMultiblockBE.class;
    }

    @Override
    public BlockEntityType<? extends FractionatingColumnMultiblockBE> getBlockEntityType() {
        return EIBlockEntityTypes.FRACTIONATING_COLUMN_MULTIBLOCK.get();
    }

    public static void placeMBS(LevelAccessor level, Vec3i lowestNorthWest, Vec3i dimensions) {

    }

    public static void deconstructMBS(LevelAccessor level, BlockPos corePos, int size) {

    }

    public static BlockState getTopBS(int size) {
        return switch(size) {
            case 1 -> SIZE_1.get();
            case 2 -> SIZE_2.get();
            default -> SIZE_3.get();
        };
    }

    public static void generateBlockstate(DataGenContext<Block, FractionatingColumnMultiblock> ctx, RegistrateBlockstateProvider prov) {
        ResourceLocation modelFile = ResourceLocation.fromNamespaceAndPath(MODID, "block/fractionating_column/fractionating_column_");
        prov.getVariantBuilder(ctx.get())
                .forAllStates(state -> {
                    String size = state.getValue(SIZE).toString();

                    return ConfiguredModel.builder()
                            .modelFile(prov.models().getExistingFile(modelFile.withSuffix("top_" + size + "_wide")))
                            .build();
                });
    }
}
