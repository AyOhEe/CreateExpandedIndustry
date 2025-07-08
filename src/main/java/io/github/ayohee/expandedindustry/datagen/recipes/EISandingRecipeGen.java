package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.api.data.recipe.StandardProcessingRecipeGen;
import com.simibubi.create.content.equipment.sandPaper.SandPaperPolishingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import io.github.ayohee.expandedindustry.CreateExpandedIndustry;
import io.github.ayohee.expandedindustry.register.EIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class EISandingRecipeGen extends StandardProcessingRecipeGen<SandPaperPolishingRecipe> {
    public final GeneratedRecipe MAGNETISED_COBALT = create(
            "magnetised_cobalt_sanding",
            b -> b.require(EIItems.UNPOLISHED_MAGNETISED_COBALT).output(EIItems.MAGNETISED_COBALT)
    );

    public EISandingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateExpandedIndustry.MODID);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.SANDPAPER_POLISHING;
    }
}
