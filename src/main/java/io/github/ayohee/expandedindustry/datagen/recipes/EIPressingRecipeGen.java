package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.api.data.recipe.PressingRecipeGen;
import io.github.ayohee.expandedindustry.CreateExpandedIndustry;
import io.github.ayohee.expandedindustry.register.EIItems;
import io.github.ayohee.expandedindustry.register.EITags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class EIPressingRecipeGen extends PressingRecipeGen {
    public final GeneratedRecipe COBALT = create(
            "cobalt_pressing",
            b -> b.require(EITags.COBALT_INGOT).output(EIItems.COBALT_SHEET)
    );

    public EIPressingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateExpandedIndustry.MODID);
    }
}
