package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.AllItems;
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

    public final GeneratedRecipe BOLT_CAST = create(
            "bolt_cast",
            b -> b.require(AllItems.STURDY_SHEET)
                    .output(EIItems.BOLT_CAST)
    );

    public final GeneratedRecipe BOLT = create(
            "bolt_pressing",
            b -> b.require(EIItems.HEATED_FILLED_BOLT_CAST)
                    .output(EIItems.BOLT_CAST, 2)
                    .output(EIItems.BOLT)
    );

    public final GeneratedRecipe RUBBER_SHEET = create(
            "rubber_sheet",
            b -> b.require(EIItems.RUBBER)
                    .output(EIItems.RUBBER_SHEET)
    );

    public EIPressingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateExpandedIndustry.MODID);
    }
}
