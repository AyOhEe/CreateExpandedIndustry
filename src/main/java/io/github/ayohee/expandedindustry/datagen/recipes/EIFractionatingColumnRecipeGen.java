package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.api.data.recipe.StandardProcessingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import io.github.ayohee.expandedindustry.CreateExpandedIndustry;
import io.github.ayohee.expandedindustry.content.recipe.FractionatingColumnRecipe;
import io.github.ayohee.expandedindustry.register.EIFluids;
import io.github.ayohee.expandedindustry.register.EIRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class EIFractionatingColumnRecipeGen extends StandardProcessingRecipeGen<FractionatingColumnRecipe> {
    public final GeneratedRecipe OIL_FRACTIONATING = create(
            "oil_fractionating",
            b -> b.require(EIFluids.CRUDE_OIL.get(), 250)
                    .output(EIFluids.HOT_BITUMEN.get(), 70)
                    .output(EIFluids.MIXED_HYDROCARBONS.get(), 80)
                    .output(EIFluids.LIQUID_PETROLEUM_GAS.get(), 100)
                    .requiresHeat(HeatCondition.HEATED)
    );

    public final GeneratedRecipe MIXED_HYDROCARBON_FRACTIONATING = create(
            "mixed_hydrocarbon_fractionating",
            b -> b.require(EIFluids.MIXED_HYDROCARBONS.get(), 250)
                    .output(EIFluids.KEROSENE.get(), 100)
                    .output(EIFluids.RAW_LUBRICATING_OIL.get(), 70)
                    .output(EIFluids.NAPHTHA.get(), 80)
                    .requiresHeat(HeatCondition.HEATED)
    );

    public EIFractionatingColumnRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateExpandedIndustry.MODID);
    }

    @Override
    protected EIRecipeTypes getRecipeType() {
        return EIRecipeTypes.FRACTIONATING_COLUMN;
    }
}
