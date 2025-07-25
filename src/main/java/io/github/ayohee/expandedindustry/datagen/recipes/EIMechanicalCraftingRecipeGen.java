package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.MechanicalCraftingRecipeGen;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EIMechanicalCraftingRecipeGen extends MechanicalCraftingRecipeGen {
    public final GeneratedRecipe DRILL_BEAM = create(EIBlocks.DRILL_BEAM::asItem)
            .returns(4).recipe(b -> b
            .key('P', Ingredient.of(AllItems.IRON_SHEET))
            .key('B', Ingredient.of(Blocks.IRON_BLOCK))
            .key('G', Ingredient.of(AllBlocks.METAL_GIRDER))
            .key('C', Ingredient.of(AllBlocks.RAILWAY_CASING))
            .patternLine("PBBBP")
            .patternLine(" GCG ")
            .patternLine(" GCG ")
            .patternLine(" GCG ")
            .patternLine("PBBBP"));

    public final GeneratedRecipe DRILL_MOTOR = create(EIBlocks.DRILL_MOTOR::asItem).recipe(b -> b
            .key('C', Ingredient.of(AllBlocks.RAILWAY_CASING))
            .key('G', Ingredient.of(AllBlocks.GEARBOX))
            .key('P', Ingredient.of(AllBlocks.MECHANICAL_PRESS))
            .key('M', Ingredient.of(AllBlocks.MECHANICAL_BEARING))
            .key('B', Ingredient.of(AllItems.IRON_SHEET))
            .patternLine("CGC")
            .patternLine("CPC")
            .patternLine("CMC")
            .patternLine("BBB"));

    public final GeneratedRecipe DRILL_BIT = create(EIBlocks.DRILL_BIT::asItem).recipe(b -> b
            .key('D', Ingredient.of(Items.DIAMOND))
            .key('I', Ingredient.of(Items.IRON_INGOT))
            .key('B', Ingredient.of(Items.IRON_BLOCK))
            .patternLine("DIBID")
            .patternLine(" DID ")
            .patternLine("  D  "));

    public EIMechanicalCraftingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MODID);
    }
}
