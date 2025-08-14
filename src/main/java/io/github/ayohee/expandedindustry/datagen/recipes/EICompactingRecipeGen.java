package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.api.data.recipe.CompactingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import io.github.ayohee.expandedindustry.register.EIFluids;
import io.github.ayohee.expandedindustry.register.EIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EICompactingRecipeGen extends CompactingRecipeGen {
    public final GeneratedRecipe SOLID_FUEL = create(
            "solid_fuel",
            b -> b.require(Items.CHARCOAL)
                    .require(EIFluids.KEROSENE.get(), 250)
                    .output(EIItems.SOLID_FUEL)
    );

    public final GeneratedRecipe POLYETHYLENE_SHEET = create(
            "polyethylene_sheet",
            b -> b.require(EIItems.POLYETHYLENE_BEADS)
                    .require(EIItems.POLYETHYLENE_BEADS)
                    .require(EIItems.POLYETHYLENE_BEADS)
                    .requiresHeat(HeatCondition.HEATED)
                    .output(EIItems.POLYETHYLENE_SHEET)
    );

    public EICompactingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MODID);
    }
}
