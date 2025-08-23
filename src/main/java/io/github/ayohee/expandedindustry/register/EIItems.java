package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.item.CombustibleItem;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import io.github.ayohee.expandedindustry.content.items.PressurisedCanisterItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;
import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.REGISTRATE;
import static io.github.ayohee.expandedindustry.register.EIArmorMaterials.COBALT_ARMOR_MATERIAL;

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

    public static final ItemEntry<CombustibleItem> SOLID_FUEL = REGISTRATE.item("solid_fuel", CombustibleItem::new)
            .onRegister(i -> i.setBurnTime(3200))
            .defaultModel()
            .register();

    public static final ItemEntry<Item> BLANK_PUNCHCARD = REGISTRATE.item("blank_punchcard", Item::new)
            .defaultModel()
            .register();

    public static final ItemEntry<Item> POLYETHYLENE_BEADS = REGISTRATE.item("polyethylene_beads", Item::new)
            .defaultModel()
            .register();

    public static final ItemEntry<Item> POLYETHYLENE_SHEET = REGISTRATE.item("polyethylene_sheet", Item::new)
            .defaultModel()
            .register();

    public static final ItemEntry<Item> POLYETHYLENE_ROD = REGISTRATE.item("polyethylene_rod", Item::new)
            .defaultModel()
            .register();

    public static final ItemEntry<Item> BOLT_CAST = REGISTRATE.item("bolt_cast", Item::new)
            .defaultModel()
            .register();

    public static final ItemEntry<Item> HEATED_FILLED_BOLT_CAST = REGISTRATE.item("heated_filled_bolt_cast", Item::new)
            .defaultModel()
            .register();

    public static final ItemEntry<Item> BOLT = REGISTRATE.item("bolt", Item::new)
            .defaultModel()
            .register();

    public static final ItemEntry<Item> REINFORCED_PLATING = REGISTRATE.item("reinforced_plating", Item::new)
            .defaultModel()
            .register();

    public static final ItemEntry<Item> MICROPLASTICS = REGISTRATE.item("microplastics", Item::new)
            .recipe((c, p) -> {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EIBlocks.MICROPLASTIC_BLOCK, 1)
                        .requires(c.get(), 9)
                        .unlockedBy("has_microplastics", RegistrateRecipeProvider.has(c.get()))
                        .save(p, "microplastic_block_from_item");
            })
            .defaultModel()
            .register();

    public static final ItemEntry<Item> RUBBER = REGISTRATE.item("rubber", Item::new)
            .defaultModel()
            .register();

    public static final ItemEntry<Item> RUBBER_SHEET = REGISTRATE.item("rubber_sheet", Item::new)
            .defaultModel()
            .register();

    public static final ItemEntry<Item> KINETIC_TRANSISTOR = REGISTRATE.item("kinetic_transistor", Item::new)
            .defaultModel()
            .register();

    public static final ItemEntry<Item> PLASTIC_COMPOSITE = REGISTRATE.item("plastic_composite", Item::new)
            .defaultModel()
            .register();

    public static final ItemEntry<Item> MAGNETIC_PLATTER = REGISTRATE.item("magnetic_platter", Item::new)
            .defaultModel()
            .register();

    public static final ItemEntry<BannerPatternItem> LOSS_PATTERN = REGISTRATE.item(
                "loss_pattern",
                (p) -> new BannerPatternItem(TagKey.create(Registries.BANNER_PATTERN, ResourceLocation.fromNamespaceAndPath(MODID, "pattern_item/loss_pattern")), p)
            )
            .properties((p) -> p.stacksTo(1).rarity(Rarity.UNCOMMON))
            .recipe((ctx, prov) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get())
                    .requires(Items.PAPER)
                    .requires(EIItems.MICROPLASTICS)
                    .unlockedBy("has_microplastics", RegistrateRecipeProvider.has(ctx.get()))
                    .save(prov, "loss_pattern_from_microplastics")
            )
            .register();

    public static final ItemEntry<CombustibleItem> SPICY_BLAZE_CAKE = REGISTRATE.item("spicy_blaze_cake", CombustibleItem::new)
            .tag(AllItemTags.BLAZE_BURNER_FUEL_SPECIAL.tag, AllItemTags.UPRIGHT_ON_BELT.tag)
            .onRegister(i -> i.setBurnTime(25600))
            .register();

    public static final ItemEntry<PressurisedCanisterItem> PRESSURISED_CANISTER = REGISTRATE.item("pressurised_canister", PressurisedCanisterItem::new)
            .properties(p -> p.stacksTo(1))
            .defaultModel()
            .register();



    public static final ItemEntry<ArmorItem> COBALT_HELMET = REGISTRATE.item("cobalt_helmet", (p) -> new ArmorItem(
            COBALT_ARMOR_MATERIAL,
            ArmorItem.Type.HELMET,
            p
    )).register();

    public static final ItemEntry<ArmorItem> COBALT_CHESTPLATE = REGISTRATE.item("cobalt_chestplate", (p) -> new ArmorItem(
            COBALT_ARMOR_MATERIAL,
            ArmorItem.Type.CHESTPLATE,
            p
    )).register();

    public static final ItemEntry<ArmorItem> COBALT_LEGGINGS = REGISTRATE.item("cobalt_leggings", (p) -> new ArmorItem(
            COBALT_ARMOR_MATERIAL,
            ArmorItem.Type.LEGGINGS,
            p
    )).register();

    public static final ItemEntry<ArmorItem> COBALT_BOOTS = REGISTRATE.item("cobalt_boots", (p) -> new ArmorItem(
            COBALT_ARMOR_MATERIAL,
            ArmorItem.Type.BOOTS,
            p
    )).register();


    public static final ItemEntry<Item> MUSIC_DISC_ARIA_MATH = REGISTRATE.item("music_disc_aria_math", Item::new)
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(MODID, "aria_math")))
            )
            .tag(Tags.Items.MUSIC_DISCS)
            .recipe((ctx, prov) -> prov.stonecutting(DataIngredient.items(Items.AMETHYST_SHARD), RecipeCategory.MISC, ctx::get))
            .defaultModel()
            .register();

    public static final ItemEntry<Item> MUSIC_DISC_VALSE = REGISTRATE.item("music_disc_valse", Item::new)
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(MODID, "valse")))
            )
            .tag(Tags.Items.MUSIC_DISCS)
            .recipe((ctx, prov) -> prov.stonecutting(DataIngredient.items(EIBlocks.ERYTHRITE_BLOCK.get()), RecipeCategory.MISC, ctx::get))
            .defaultModel()
            .register();

    public static final ItemEntry<Item> MUSIC_DISC_CLAIRE_DE_LUNE = REGISTRATE.item("music_disc_claire_de_lune", Item::new)
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(MODID, "claire_de_lune")))
            )
            .tag(Tags.Items.MUSIC_DISCS)
            .recipe((ctx, prov) -> prov.stonecutting(DataIngredient.items(Items.PRISMARINE_CRYSTALS), RecipeCategory.MISC, ctx::get))
            .defaultModel()
            .register();



    static {
        REGISTRATE.setCreativeTab(null);
    }



    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_BOLT = REGISTRATE.item("incomplete_bolt", SequencedAssemblyItem::new)
            .defaultModel()
            .register();

    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_KINETIC_TRANSISTOR = REGISTRATE.item("incomplete_kinetic_transistor", SequencedAssemblyItem::new)
            .defaultModel()
            .register();

    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_PLASTIC_COMPOSITE = REGISTRATE.item("incomplete_plastic_composite", SequencedAssemblyItem::new)
            .defaultModel()
            .register();



    public static void register() { }
}
