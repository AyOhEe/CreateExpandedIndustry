package io.github.ayohee.expandedindustry.datagen;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import io.github.ayohee.expandedindustry.datagen.recipes.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EIRecipeProvider extends RecipeProvider {
    static final List<ProcessingRecipeGen<?, ?, ?>> GENERATORS = new ArrayList<>();

    public EIRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    public static void registerAllProcessing(DataGenerator gen, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        GENERATORS.add(new EIPressingRecipeGen(output, registries));
        GENERATORS.add(new EICrushingRecipeGen(output, registries));
        GENERATORS.add(new EISandingRecipeGen(output, registries));
        GENERATORS.add(new EIItemApplicationRecipeGen(output, registries));
        GENERATORS.add(new EIMixingRecipeGen(output, registries));
        GENERATORS.add(new EICompactingRecipeGen(output, registries));
        GENERATORS.add(new EICuttingRecipeGen(output, registries));
        GENERATORS.add(new EIFillingRecipeGen(output, registries));

        gen.addProvider(true, new DataProvider() {
            @Override
            public @NotNull String getName() {
                return "Create: Expanded Industry's Processing Recipes";
            }
            @Override
            public @NotNull CompletableFuture<?> run(@NotNull CachedOutput dc) {
                return CompletableFuture.allOf(GENERATORS.stream().map(gen -> gen.run(dc)).toArray(CompletableFuture[]::new));
            }
        });
    }
}
