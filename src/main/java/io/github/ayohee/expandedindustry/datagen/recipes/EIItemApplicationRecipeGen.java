package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.api.data.recipe.ItemApplicationRecipeGen;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.register.EIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EIItemApplicationRecipeGen extends ItemApplicationRecipeGen {
    public final GeneratedRecipe COBALT_CASING = create(
            "cobalt_casing_from_tuff_bricks",
            b -> b.require(Blocks.TUFF_BRICKS).require(EIItems.COBALT_INGOT).output(EIBlocks.COBALT_CASING)
    );

    public EIItemApplicationRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MODID);
    }
}
