package io.github.ayohee.expandedindustry.content.complex.flarestack;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockController;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FlareStackMultiblock extends AbstractMultiblockController<FlareStackMultiblockBE> implements IWrenchable {
    public FlareStackMultiblock(Properties properties) {
        super(properties);
    }

    @Override
    public Class<FlareStackMultiblockBE> getBlockEntityClass() {
        return FlareStackMultiblockBE.class;
    }

    @Override
    public BlockEntityType<? extends FlareStackMultiblockBE> getBlockEntityType() {
        return EIBlockEntityTypes.FLARE_STACK_MULTIBLOCK.get();
    }

    public static void generateBlockstate(DataGenContext<Block, FlareStackMultiblock> ctx, RegistrateBlockstateProvider prov) {
        //TODO
    }
}
