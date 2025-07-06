package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.AllTags.AllItemTags;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.REGISTRATE;

public class EIItems {
    static {
        REGISTRATE.setCreativeTab(EICreativeTabs.MAIN_TAB);
    }

    public static final ItemEntry<Item> CRUSHED_RAW_COBALT = REGISTRATE.item("crushed_raw_cobalt", Item::new)
            .tag(AllItemTags.CRUSHED_RAW_MATERIALS.tag)
            .register();

    public static final ItemEntry<Item> COBALT_INGOT = REGISTRATE.item("cobalt_ingot", Item::new)
            .tag(EITags.COBALT_INGOT)
            .recipe((c, p) -> {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EIItems.COBALT_NUGGET, 9)
                        .requires(c.get(), 1)
                        .unlockedBy("has_cobalt_nugget", RegistrateRecipeProvider.has(EIItems.COBALT_NUGGET))
                        .save(p);
            })
            .register();

    public static final ItemEntry<Item> COBALT_NUGGET = REGISTRATE.item("cobalt_nugget", Item::new)
            .tag(EITags.COBALT_NUGGET)
            .recipe((c, p) -> {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EIItems.COBALT_INGOT, 1)
                        .requires(c.get(), 9)
                        .unlockedBy("has_cobalt_ingot", RegistrateRecipeProvider.has(EIItems.COBALT_INGOT))
                        .save(p);
            })
            .register();

    public static final ItemEntry<Item> COBALT_SHEET = REGISTRATE.item("cobalt_sheet", Item::new)
            .tag(AllItemTags.PLATES.tag, EITags.COBALT_SHEET)
            .register();


    public static void register() { }
}
