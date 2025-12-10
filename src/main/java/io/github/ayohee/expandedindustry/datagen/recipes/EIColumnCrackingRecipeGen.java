package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.api.data.recipe.StandardProcessingRecipeGen;
import io.github.ayohee.expandedindustry.CreateExpandedIndustry;
import io.github.ayohee.expandedindustry.content.recipe.ColumnCrackingRecipe;
import io.github.ayohee.expandedindustry.register.EIFluids;
import io.github.ayohee.expandedindustry.register.EIItems;
import io.github.ayohee.expandedindustry.register.EIRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class EIColumnCrackingRecipeGen extends StandardProcessingRecipeGen<ColumnCrackingRecipe> {
    public final GeneratedRecipe LPG_TO_ETHYLENE = create(
            "lpg_to_ethylene",
            b -> b.require(EIFluids.LIQUID_PETROLEUM_GAS.get(), 250)
                    .output(EIFluids.ETHYLENE.get(), 250)
    );

    public EIColumnCrackingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateExpandedIndustry.MODID);
    }

    @Override
    protected EIRecipeTypes getRecipeType() {
        return EIRecipeTypes.COLUMN_CRACKING;
    }
}
