package io.github.ayohee.expandedindustry.content.complex.pressurisedTank;

import com.simibubi.create.content.fluids.tank.FluidTankBlock.Shape;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;

//TODO this is an AWFUL way of doing this. So much code duplication....
public class PressurisedFluidTankGenerator extends SpecialBlockStateGen {

    private String prefix;

    public PressurisedFluidTankGenerator() {
        this("");
    }

    public PressurisedFluidTankGenerator(String prefix) {
        this.prefix = prefix;
    }

    @Override
    protected int getXRotation(BlockState state) {
        return 0;
    }

    @Override
    protected int getYRotation(BlockState state) {
        return 0;
    }

    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
                                                BlockState state) {
        Boolean top = state.getValue(PressurisedFluidTankBlock.TOP);
        Boolean bottom = state.getValue(PressurisedFluidTankBlock.BOTTOM);
        Shape shape = state.getValue(PressurisedFluidTankBlock.SHAPE);

        String shapeName = "middle";
        if (top && bottom)
            shapeName = "single";
        else if (top)
            shapeName = "top";
        else if (bottom)
            shapeName = "bottom";

        String modelName = shapeName + (shape == Shape.PLAIN ? "" : "_" + shape.getSerializedName());

        if (!prefix.isEmpty())
            return prov.models()
                    .withExistingParent(prefix + modelName, prov.modLoc("block/pressurised_fluid_tank/block_" + modelName))
                    .texture("0", prov.modLoc("block/" + prefix + "casing"))
                    .texture("1", prov.modLoc("block/" + prefix + "pressurised_fluid_tank"))
                    .texture("3", prov.modLoc("block/" + prefix + "pressurised_fluid_tank_window"))
                    .texture("4", prov.modLoc("block/" + prefix + "casing"))
                    .texture("5", prov.modLoc("block/" + prefix + "pressurised_fluid_tank_window_single"))
                    .texture("particle", prov.modLoc("block/" + prefix + "pressurised_fluid_tank"));

        return AssetLookup.partialBaseModel(ctx, prov, modelName);
    }

}
