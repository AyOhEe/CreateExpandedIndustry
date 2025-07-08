package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.AllTags.AllItemTags;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.REGISTRATE;

public class EIItems {
    static {
        REGISTRATE.setCreativeTab(EICreativeTabs.MAIN_TAB);
    }

    public static final ItemEntry<Item> CRUSHED_RAW_COBALT = REGISTRATE.item("crushed_raw_cobalt", Item::new)
            .tag(AllItemTags.CRUSHED_RAW_MATERIALS.tag)
            .recipe((c, p) -> {
                Ingredient crushed_cobalt = Ingredient.of(c.get());
                SimpleCookingRecipeBuilder.blasting(crushed_cobalt, RecipeCategory.MISC, EIItems.COBALT_INGOT, 0.1f, 100)
                        .unlockedBy("has_crushed_cobalt", RegistrateRecipeProvider.has(EIItems.CRUSHED_RAW_COBALT))
                        .save(p, "cobalt_ingot_from_crushed");
            })
            .register();

    public static final ItemEntry<Item> COBALT_INGOT = REGISTRATE.item("cobalt_ingot", Item::new)
            .tag(EITags.COBALT_INGOT)
            .recipe((c, p) -> {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EIItems.COBALT_NUGGET, 9)
                        .requires(c.get(), 1)
                        .unlockedBy("has_cobalt_ingot", RegistrateRecipeProvider.has(c.get()))
                        .save(p, "cobalt_nuggets_from_ingot");
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EIBlocks.COBALT_BLOCK, 1)
                        .requires(c.get(), 9)
                        .unlockedBy("has_cobalt_ingot", RegistrateRecipeProvider.has(c.get()))
                        .save(p, "cobalt_block_from_ingots");
            })
            .register();

    public static final ItemEntry<Item> COBALT_NUGGET = REGISTRATE.item("cobalt_nugget", Item::new)
            .tag(EITags.COBALT_NUGGET)
            .recipe((c, p) -> {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EIItems.COBALT_INGOT, 1)
                        .requires(c.get(), 9)
                        .unlockedBy("has_cobalt_nugget", RegistrateRecipeProvider.has(c.get()))
                        .save(p, "cobalt_ingot_from_nuggets");
            })
            .register();

    public static final ItemEntry<Item> COBALT_SHEET = REGISTRATE.item("cobalt_sheet", Item::new)
            .tag(AllItemTags.PLATES.tag, EITags.COBALT_SHEET)
            .register();

    public static final ItemEntry<Item> UNPOLISHED_MAGNETISED_COBALT = REGISTRATE.item("unpolished_magnetised_cobalt", Item::new)
            .recipe((c, p) -> {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                        .requires(EIItems.COBALT_INGOT, 1)
                        .requires(Items.GLOWSTONE_DUST, 2)
                        .requires(Items.REDSTONE, 2)
                        .unlockedBy("has_cobalt_ingot", RegistrateRecipeProvider.has(EIItems.COBALT_INGOT))
                        .save(p, "unpolished_magnetised_cobalt_shapeless");
            })
            .register();

    public static final ItemEntry<Item> MAGNETISED_COBALT = REGISTRATE.item("magnetised_cobalt", Item::new)
            .register();


    public static void register() { }
}
