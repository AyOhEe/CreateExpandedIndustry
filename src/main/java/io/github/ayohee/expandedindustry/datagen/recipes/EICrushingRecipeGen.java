package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.api.data.recipe.CrushingRecipeGen;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.register.EIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EICrushingRecipeGen extends CrushingRecipeGen {
    public final GeneratedRecipe ERYTHRITE = create(
            "erythrite_crushing",
            b -> b.require(EIBlocks.ERYTHRITE_BLOCK)
                    .output(0.1f, EIItems.CRUSHED_RAW_COBALT)
                    .output(0.1f, EIItems.COBALT_NUGGET)
    );

    public final GeneratedRecipe MICROPLASTICS = create(
            "microplastics",
            b -> b.require(EIItems.POLYETHYLENE_BEADS)
                    .output(EIItems.MICROPLASTICS, 2)
                    .output(0.5f, EIItems.MICROPLASTICS)
    );

    public EICrushingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MODID);
    }
}
