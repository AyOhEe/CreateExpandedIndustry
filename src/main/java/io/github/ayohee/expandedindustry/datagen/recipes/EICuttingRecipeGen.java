package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.CuttingRecipeGen;
import io.github.ayohee.expandedindustry.register.EIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EICuttingRecipeGen extends CuttingRecipeGen {
    public final GeneratedRecipe BLANK_PUNCHCARD = create(
            "blank_punchcard",
            b -> b.require(AllItems.CARDBOARD)
                    .output(EIItems.BLANK_PUNCHCARD, 2)
    );

    public final GeneratedRecipe POLYETHYLENE_ROD = create(
            "polyethylene_rod",
            b -> b.require(EIItems.POLYETHYLENE_SHEET)
                    .output(EIItems.POLYETHYLENE_ROD, 4)
    );

    public EICuttingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MODID);
    }
}
