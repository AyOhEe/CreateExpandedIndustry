package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.CuttingRecipeGen;
import io.github.ayohee.expandedindustry.register.EIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EISawingRecipeGen extends CuttingRecipeGen {
    public final GeneratedRecipe BLANK_PUNCHCARD = create(
            "blank_punchcard",
            b -> b.require(AllItems.CARDBOARD)
                    .output(EIItems.BLANK_PUNCHCARD, 2)
    );

    public EISawingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MODID);
    }
}
