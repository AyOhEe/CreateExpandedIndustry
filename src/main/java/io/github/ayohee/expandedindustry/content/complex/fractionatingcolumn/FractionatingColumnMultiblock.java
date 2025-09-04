package io.github.ayohee.expandedindustry.content.complex.fractionatingcolumn;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockController;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FractionatingColumnMultiblock extends AbstractMultiblockController<FractionatingColumnMultiblockBE> implements IWrenchable {
    public FractionatingColumnMultiblock(Properties properties) {
        super(properties);
    }

    @Override
    public Class<FractionatingColumnMultiblockBE> getBlockEntityClass() {
        return FractionatingColumnMultiblockBE.class;
    }

    @Override
    public BlockEntityType<? extends FractionatingColumnMultiblockBE> getBlockEntityType() {
        return EIBlockEntityTypes.FRACTIONATING_COLUMN_MULTIBLOCK.get();
    }

    public static void generateBlockstate(DataGenContext<Block, FractionatingColumnMultiblock> ctx, RegistrateBlockstateProvider prov) {
        //TODO
    }
}
