package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.FillingRecipeGen;
import io.github.ayohee.expandedindustry.register.EIFluids;
import io.github.ayohee.expandedindustry.register.EIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EIFillingRecipeGen extends FillingRecipeGen {
    public final GeneratedRecipe SPICY_BLAZE_CAKE = create(
            "spicy_blaze_cake",
            b -> b.require(EIFluids.KEROSENE.get(), 500)
                    .require(AllItems.BLAZE_CAKE_BASE)
                    .output(EIItems.SPICY_BLAZE_CAKE)
    );

    public EIFillingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MODID);
    }
}
