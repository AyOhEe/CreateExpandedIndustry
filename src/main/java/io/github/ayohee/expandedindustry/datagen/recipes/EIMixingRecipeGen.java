package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import io.github.ayohee.expandedindustry.CreateExpandedIndustry;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.register.EIFluids;
import io.github.ayohee.expandedindustry.register.EIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
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

    public final GeneratedRecipe POLYETHYLENE_BEADS = create(
            "polyethylene_beads",
            b -> b.require(EIFluids.ETHYLENE.get(), 400)
                    .require(AllItems.CRUSHED_IRON)
                    .requiresHeat(HeatCondition.HEATED)
                    .output(EIItems.POLYETHYLENE_BEADS, 4)
    );

    public final GeneratedRecipe UNPOLISHED_MAGNETISED_COBALT = create(
            "unpolished_magnetised_cobalt",
            b -> b.require(EIItems.COBALT_INGOT)
                    .require(Items.REDSTONE)
                    .require(Items.GLOWSTONE)
                    .output(EIItems.UNPOLISHED_MAGNETISED_COBALT)
    );

    public EIMixingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateExpandedIndustry.MODID);
    }
}
