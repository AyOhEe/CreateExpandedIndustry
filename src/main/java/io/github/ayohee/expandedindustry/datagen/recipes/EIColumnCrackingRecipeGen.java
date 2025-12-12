package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.api.data.recipe.StandardProcessingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import io.github.ayohee.expandedindustry.CreateExpandedIndustry;
import io.github.ayohee.expandedindustry.content.recipe.ColumnCrackingRecipe;
import io.github.ayohee.expandedindustry.register.EIFluids;
import io.github.ayohee.expandedindustry.register.EIRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.Fluids;

import java.util.concurrent.CompletableFuture;

public class EIColumnCrackingRecipeGen extends StandardProcessingRecipeGen<ColumnCrackingRecipe> {
    //TODO adjust numbers once recipes are implemented

    public final GeneratedRecipe BITUMEN_TO_MIXED = create(
            "bitumen_to_mixed",
            b -> b.require(EIFluids.HOT_BITUMEN.get(), 250)
                    .output(EIFluids.MIXED_HYDROCARBONS.get(), 250)
                    .requiresHeat(HeatCondition.HEATED)
    );

    public final GeneratedRecipe KEROSENE_TO_RAW_LUBRICANT = create(
            "kerosene_to_raw_lubricant",
            b -> b.require(EIFluids.KEROSENE.get(), 250)
                    .require(EIFluids.HYDROGEN_GAS.get(), 250)
                    .output(EIFluids.RAW_LUBRICATING_OIL.get(), 250)
                    .requiresHeat(HeatCondition.HEATED)
    );

    public final GeneratedRecipe RAW_LUBRICANT_TO_NAPHTHA = create(
            "raw_lubricant_to_naphtha",
            b -> b.require(EIFluids.RAW_LUBRICATING_OIL.get(), 250)
                    .require(EIFluids.HYDROGEN_GAS.get(), 250)
                    .output(EIFluids.NAPHTHA.get(), 250)
                    .requiresHeat(HeatCondition.HEATED)
    );

    public final GeneratedRecipe NAPHTHA_TO_LPG = create(
            "naphtha_to_lpg",
            b -> b.require(EIFluids.NAPHTHA.get(), 250)
                    .require(EIFluids.HYDROGEN_GAS.get(), 250)
                    .output(EIFluids.LIQUID_PETROLEUM_GAS.get(), 250)
                    .requiresHeat(HeatCondition.SUPERHEATED)
    );

    public final GeneratedRecipe LPG_TO_ETHYLENE = create(
            "lpg_to_ethylene",
            b -> b.require(EIFluids.LIQUID_PETROLEUM_GAS.get(), 250)
                    .require(EIFluids.HYDROGEN_GAS.get(), 250)
                    .output(EIFluids.ETHYLENE.get(), 250)
                    .requiresHeat(HeatCondition.SUPERHEATED)
    );

    public final GeneratedRecipe STEAM_REFORMING = create(
            "steam_reforming",
            b -> b.require(EIFluids.LIQUID_PETROLEUM_GAS.get(), 250)
                    .require(Fluids.WATER, 250)
                    .output(EIFluids.HYDROGEN_GAS.get(), 250)
                    .requiresHeat(HeatCondition.HEATED)
    );

    public EIColumnCrackingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateExpandedIndustry.MODID);
    }

    @Override
    protected EIRecipeTypes getRecipeType() {
        return EIRecipeTypes.COLUMN_CRACKING;
    }
}
