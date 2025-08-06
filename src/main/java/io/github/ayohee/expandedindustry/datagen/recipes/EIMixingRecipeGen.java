package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import io.github.ayohee.expandedindustry.CreateExpandedIndustry;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.register.EIFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class EIMixingRecipeGen extends MixingRecipeGen {
    public final GeneratedRecipe ASPHALT = create(
            "asphalt_mixing",
            b -> b.require(EIFluids.HOT_BITUMEN.get(), 250)
                    .require(Blocks.SAND.asItem())
                    .require(Blocks.SAND.asItem())
                    .require(Blocks.GRAVEL.asItem())
                    .require(Blocks.GRAVEL.asItem())
                    .output(EIBlocks.ASPHALT_BLOCK)
    );

    public EIMixingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateExpandedIndustry.MODID);
    }
}
