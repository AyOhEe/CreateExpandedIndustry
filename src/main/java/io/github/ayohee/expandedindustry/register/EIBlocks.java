package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import io.github.ayohee.expandedindustry.CreateExpandedIndustry;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.REGISTRATE;

public class EIBlocks {
    static {
        REGISTRATE.setCreativeTab(EICreativeTabs.MAIN_TAB);
    }


    public static final BlockEntry<Block> ERYTHRITE_BLOCK = REGISTRATE.block("erythrite", Block::new)
            .initialProperties(SharedProperties::stone)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .item()
            .transform(getItemModel())
            .register();

    public static final BlockEntry<Block> HARDENED_ERYTHRITE_BLOCK = REGISTRATE.block("hardened_erythrite", Block::new)
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(EIBlocks::hardened_stones)
            .item()
            .transform(getItemModel())
            .register();

    public static final BlockEntry<Block> HARDENED_OCHRUM_BLOCK = REGISTRATE.block("hardened_ochrum", Block::new)
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(EIBlocks::hardened_stones)
            .item()
            .transform(getItemModel())
            .register();

    public static final BlockEntry<Block> HARDENED_VERIDIUM_BLOCK = REGISTRATE.block("hardened_veridium", Block::new)
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(EIBlocks::hardened_stones)
            .item()
            .transform(getItemModel())
            .register();

    public static final BlockEntry<Block> HARDENED_ASURINE_BLOCK = REGISTRATE.block("hardened_asurine", Block::new)
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(EIBlocks::hardened_stones)
            .item()
            .transform(getItemModel())
            .register();

    public static final BlockEntry<Block> HARDENED_CRIMSITE_BLOCK = REGISTRATE.block("hardened_crimsite", Block::new)
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(EIBlocks::hardened_stones)
            .item()
            .transform(getItemModel())
            .register();

    private static BlockBehaviour.Properties hardened_stones(BlockBehaviour.Properties properties) {
        return properties.explosionResistance(0).noLootTable();
    }


    public static final BlockEntry<Block> COBALT_BLOCK = REGISTRATE.block("cobalt_block", Block::new)
            .initialProperties(SharedProperties::netheriteMetal)
            .properties((c) -> {
                return c.destroyTime(20);
            })
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
            .item()
            .recipe((c, p) -> {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EIItems.COBALT_INGOT, 9)
                        .requires(c.get(), 1)
                        .unlockedBy("has_cobalt_block", RegistrateRecipeProvider.has(c.get()))
                        .save(p, "cobalt_ingots_from_block");
            })
            .transform(getItemModel())
            .register();


    public static <I extends BlockItem, P> NonNullFunction<ItemBuilder<I, P>, P> getItemModel() {
        return b -> b.model(EIBlocks::locateItemModel).build();
    }

    public static <I extends BlockItem> ItemModelBuilder locateItemModel(
            DataGenContext<Item, I> ctx,
            RegistrateItemModelProvider prov
            ) {
        String blockName = prov.name(ctx.getEntry()::getBlock);
        return prov.withExistingParent(
                blockName,
                ResourceLocation.fromNamespaceAndPath(CreateExpandedIndustry.MODID, "item/" + blockName)
        );
    }


    public static void register() { }
}
