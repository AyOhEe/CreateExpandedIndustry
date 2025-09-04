package io.github.ayohee.expandedindustry.content.complex.crackingcolumn;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockController;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CrackingColumnMultiblock extends AbstractMultiblockController<CrackingColumnMultiblockBE> implements IWrenchable {
    public CrackingColumnMultiblock(Properties properties) {
        super(properties);
    }

    @Override
    public Class<CrackingColumnMultiblockBE> getBlockEntityClass() {
        return CrackingColumnMultiblockBE.class;
    }

    @Override
    public BlockEntityType<? extends CrackingColumnMultiblockBE> getBlockEntityType() {
        return EIBlockEntityTypes.CRACKING_COLUMN_MULTIBLOCK.get();
    }

    public static void generateBlockstate(DataGenContext<Block, CrackingColumnMultiblock> ctx, RegistrateBlockstateProvider prov) {
        //TODO
    }
}
