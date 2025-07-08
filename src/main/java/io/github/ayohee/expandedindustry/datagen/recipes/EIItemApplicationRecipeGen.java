package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.api.data.recipe.ItemApplicationRecipeGen;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.register.EIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EIItemApplicationRecipeGen extends ItemApplicationRecipeGen {
    public final GeneratedRecipe COBALT_CASING = woodCasing("cobalt", EIItems.COBALT_INGOT::get, EIBlocks.COBALT_CASING::get);

    public EIItemApplicationRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MODID);
    }
}
