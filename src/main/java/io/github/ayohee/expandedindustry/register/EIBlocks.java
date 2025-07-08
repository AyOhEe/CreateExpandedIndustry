package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.AllDisplaySources;
import com.simibubi.create.AllMountedStorageTypes;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import io.github.ayohee.expandedindustry.CreateExpandedIndustry;

import io.github.ayohee.expandedindustry.content.pressurised_blocks.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;

import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType.mountedFluidStorage;
import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;
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

    public static final BlockEntry<PressurisedFluidTankBlock> PRESSURISED_FLUID_TANK = REGISTRATE.block("pressurised_fluid_tank", PressurisedFluidTankBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(p -> p.noOcclusion()
                    .isRedstoneConductor((p1, p2, p3) -> true))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(new PressurisedFluidTankGenerator()::generate)
            .onRegister(CreateRegistrate.blockModel(() -> PressurisedFluidTankModel::standard))
            .transform(displaySource(AllDisplaySources.BOILER))
            .transform(mountedFluidStorage(AllMountedStorageTypes.FLUID_TANK))
            .onRegister(movementBehaviour(new PressurisedFluidTankMovementBehavior()))
            .addLayer(() -> RenderType::cutoutMipped)
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EIBlocks.PRESSURISED_FLUID_TANK, 1)
                        .pattern("P")
                        .pattern("B")
                        .pattern("P")
                        .define('P', EIItems.COBALT_SHEET)
                        .define('B', Items.BARREL)
                        .unlockedBy("has_cobalt_block", RegistrateRecipeProvider.has(c.get()))
                        .save(p, "pressurised_fluid_tank_vertical");
            })
            .item(PressurisedFluidTankItem::new)
            .model((c, p) -> {
                p.withExistingParent(c.getName(), p.modLoc(c.getName() + "_block_single_window"));
            })
            .build()
            .register();

    public static final BlockEntry<CasingBlock> COBALT_CASING = REGISTRATE.block("cobalt_casing", CasingBlock::new)
            .properties(p -> p.mapColor(MapColor.PODZOL))
            .transform(BuilderTransformers.casing(() -> EISpriteShifts.COBALT_CASING))
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

    public static class EISpriteShifts {
        public static final CTSpriteShiftEntry COBALT_CASING = getCT(AllCTTypes.OMNIDIRECTIONAL, "cobalt_casing");

        public static final CTSpriteShiftEntry
                FLUID_TANK = getCT(AllCTTypes.RECTANGLE, "pressurised_fluid_tank"),
                FLUID_TANK_TOP = getCT(AllCTTypes.RECTANGLE, "pressurised_fluid_tank_top"),
                FLUID_TANK_INNER = getCT(AllCTTypes.RECTANGLE, "pressurised_fluid_tank_inner");


        public static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
            return getCT(type, blockTextureName, blockTextureName);
        }

        public static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
            return CTSpriteShifter.getCT(type, ResourceLocation.fromNamespaceAndPath(MODID, "block/" + blockTextureName),
                    ResourceLocation.fromNamespaceAndPath(MODID, "block/" + connectedTextureName + "_connected"));
        }
    }


    public static void register() { }
}
