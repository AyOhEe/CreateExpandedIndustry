package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.AllDisplaySources;
import com.simibubi.create.AllMountedStorageTypes;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;

import io.github.ayohee.expandedindustry.content.blocks.HardenedStoneBlock;
import io.github.ayohee.expandedindustry.content.complex.pressurisedTank.*;
import io.github.ayohee.expandedindustry.content.complex.reinforcedDrill.*;
import io.github.ayohee.expandedindustry.multiblock.MultiblockGhostBlock;
import io.github.ayohee.expandedindustry.multiblock.MultiblockInventoryBlock;
import io.github.ayohee.expandedindustry.multiblock.MultiblockKineticIOBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType.mountedFluidStorage;
import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;
import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.REGISTRATE;

public class EIBlocks {
    /*-----THESE BLOCKS WILL NOT BE SEEN IN THE CREATIVE TAB-----*/
    static {
        REGISTRATE.setCreativeTab(null);
    }

    public static final BlockEntry<ReinforcedDrillMultiblock> REINFORCED_DRILL_MULTIBLOCK = REGISTRATE
            .block("reinforced_drill_multiblock", ReinforcedDrillMultiblock::new)
            .blockstate(ReinforcedDrillMultiblock::generateBlockstate)
            .initialProperties(() -> Blocks.GLASS)
            .properties(Helpers::multiblockComponent)
            .properties(c -> c
                    .mapColor(MapColor.COLOR_ORANGE)
                    .sound(SoundType.METAL))
            .register();


    public static final BlockEntry<MultiblockGhostBlock> MULTIBLOCK_GHOST = REGISTRATE
            .block("multiblock_ghost", MultiblockGhostBlock::new)
            .blockstate(Helpers.ghostBlock())
            .initialProperties(() -> Blocks.GLASS)
            .properties(Helpers::multiblockComponent)
            .properties(c -> c
                    .mapColor(MapColor.COLOR_ORANGE)
                    .sound(SoundType.METAL))
            .register();

    public static final BlockEntry<MultiblockInventoryBlock> MULTIBLOCK_INVENTORY = REGISTRATE
            .block("multiblock_inventory", MultiblockInventoryBlock::new)
            .blockstate(Helpers.ghostBlock())
            .initialProperties(() -> Blocks.GLASS)
            .properties(Helpers::multiblockComponent)
            .properties(c -> c
                .mapColor(MapColor.COLOR_ORANGE)
                .sound(SoundType.METAL))
            .register();

    public static final BlockEntry<MultiblockKineticIOBlock> MULTIBLOCK_KINETIC_IO = REGISTRATE
            .block("multiblock_kinetic_io", MultiblockKineticIOBlock::new)
            .blockstate(Helpers.ghostBlock())
            .initialProperties(() -> Blocks.GLASS)
            .properties(Helpers::multiblockComponent)
            .properties(c -> c
                    .mapColor(MapColor.COLOR_ORANGE)
                    .sound(SoundType.METAL))
            .register();


    /*-----THESE BLOCKS WILL BE SEEN IN THE CREATIVE TAB-----*/
    static {
        REGISTRATE.setCreativeTab(EICreativeTabs.MAIN_TAB);
    }


    public static final BlockEntry<Block> ERYTHRITE_BLOCK = REGISTRATE.block("erythrite", Block::new)
            .initialProperties(SharedProperties::stone)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .properties(c -> c.mapColor(DyeColor.MAGENTA))
            .blockstate(Helpers.subdirCubeAllTexture())
            .simpleItem()
            .register();

    public static final BlockEntry<HardenedStoneBlock> HARDENED_ERYTHRITE_BLOCK = REGISTRATE
            .block("hardened_erythrite", (p) -> new HardenedStoneBlock(p, EIBlockEntityTypes.HARDENED_ERYTHRITE))
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(EIBlocks::hardened_stones)
            .properties(c -> c.mapColor(MapColor.COLOR_MAGENTA))
            .blockstate(Helpers.subdirCubeAllTexture())
            .simpleItem()
            .register();

    public static final BlockEntry<HardenedStoneBlock> HARDENED_OCHRUM_BLOCK = REGISTRATE
            .block("hardened_ochrum", (p) -> new HardenedStoneBlock(p, EIBlockEntityTypes.HARDENED_OCHRUM))
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(EIBlocks::hardened_stones)
            .properties(c -> c.mapColor(MapColor.GOLD))
            .blockstate(Helpers.subdirCubeAllTexture())
            .simpleItem()
            .register();

    public static final BlockEntry<HardenedStoneBlock> HARDENED_VERIDIUM_BLOCK = REGISTRATE
            .block("hardened_veridium", (p) -> new HardenedStoneBlock(p, EIBlockEntityTypes.HARDENED_VERIDIUM))
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(EIBlocks::hardened_stones)
            .properties(c -> c.mapColor(MapColor.COLOR_GREEN))
            .blockstate(Helpers.subdirCubeAllTexture())
            .simpleItem()
            .register();

    public static final BlockEntry<HardenedStoneBlock> HARDENED_ASURINE_BLOCK = REGISTRATE
            .block("hardened_asurine", (p) -> new HardenedStoneBlock(p, EIBlockEntityTypes.HARDENED_ASURINE))
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(EIBlocks::hardened_stones)
            .properties(c -> c.mapColor(MapColor.COLOR_BLUE))
            .blockstate(Helpers.subdirCubeAllTexture())
            .simpleItem()
            .register();

    public static final BlockEntry<HardenedStoneBlock> HARDENED_CRIMSITE_BLOCK = REGISTRATE
            .block("hardened_crimsite", (p) -> new HardenedStoneBlock(p, EIBlockEntityTypes.HARDENED_CRIMSITE))
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(EIBlocks::hardened_stones)
            .properties(c -> c.mapColor(MapColor.COLOR_RED))
            .blockstate(Helpers.subdirCubeAllTexture())
            .simpleItem()
            .register();


    private static BlockBehaviour.Properties hardened_stones(BlockBehaviour.Properties properties) {
        return properties.explosionResistance(0).noLootTable();
    }

    public static final BlockEntry<Block> MICROPLASTIC_BLOCK = REGISTRATE
            .block("microplastic_block", Block::new)
            .initialProperties(() -> Blocks.SAND)
            .properties(c -> c.mapColor(MapColor.COLOR_LIGHT_GRAY))
            .blockstate(Helpers.subdirCubeAllTexture("microplastic"))
            .item()
            .tag(EITags.MICROPLASTIC_BLOCK)
            .build()
            .register();

    public static final Map<DyeColor, BlockEntry<Block>> DYED_MICROPLASTIC_BLOCKS = Helpers.createColouredVariants((DyeColor v) -> {
        return REGISTRATE
                .block(v.toString() + "_microplastic_block", Block::new)
                .initialProperties(() -> Blocks.SAND)
                .properties(c -> c.mapColor(v.getMapColor()))
                .blockstate(Helpers.subdirCubeAllTexture("microplastic"))
                .item()
                .tag(v.getDyedTag())
                .tag(EITags.MICROPLASTIC_BLOCK)
                .build()
                .recipe((ctx, prov) -> {
                    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EIBlocks.DYED_MICROPLASTIC_BLOCKS.get(v).asStack(8))
                            .pattern("PPP")
                            .pattern("PDP")
                            .pattern("PPP")
                            .define('P', EITags.MICROPLASTIC_BLOCK)
                            .define('D', v.getTag())
                            .unlockedBy("has_microplastic", RegistrateRecipeProvider.has(EIBlocks.MICROPLASTIC_BLOCK))
                            .save(prov, v.toString() + "_dyed_microplastic_block");
                })
                .register();
    });


    public static final BlockEntry<Block> COBALT_BLOCK = REGISTRATE.block("cobalt_block", Block::new)
            .initialProperties(SharedProperties::netheriteMetal)
            .properties(c -> c.mapColor(MapColor.COLOR_BLUE).destroyTime(20))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
            .blockstate(Helpers.subdirCubeAllTexture())
            .simpleItem()
            .recipe((c, p) -> {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EIItems.COBALT_INGOT, 9)
                        .requires(c.get(), 1)
                        .unlockedBy("has_cobalt_block", RegistrateRecipeProvider.has(c.get()))
                        .save(p, "cobalt_ingots_from_block");
            })
            .register();

    public static final BlockEntry<PressurisedFluidTankBlock> PRESSURISED_FLUID_TANK = REGISTRATE.block("pressurised_fluid_tank", PressurisedFluidTankBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(p -> p.noOcclusion()
                    .isRedstoneConductor((p1, p2, p3) -> true)
                    .mapColor(MapColor.COLOR_BLUE))
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
            .transform(BuilderTransformers.casing(() -> EISpriteShifts.COBALT_CASING))
            .properties(p -> p.mapColor(MapColor.COLOR_LIGHT_BLUE).sound(SoundType.TUFF_BRICKS))
            .blockstate(Helpers.subdirCubeAllTexture())
            .simpleItem()
            .register();



    public static final BlockEntry<DrillBeamBlock> DRILL_BEAM = REGISTRATE
            .block("drill_beam", DrillBeamBlock::new)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY)
                    .noOcclusion()
                    .sound(SoundType.NETHERITE_BLOCK))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
            .simpleItem()
            .register();

    public static final BlockEntry<DrillMotorBlock> DRILL_MOTOR = REGISTRATE
            .block("drill_motor", DrillMotorBlock::new)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY)
                    .sound(SoundType.METAL))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
            .simpleItem()
            .register();

    public static final BlockEntry<DrillBitBlock> DRILL_BIT = REGISTRATE
            .block("drill_bit", DrillBitBlock::new)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY)
                    .noOcclusion()
                    .sound(SoundType.NETHERITE_BLOCK))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
            .simpleItem()
            .register();


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

    public static class Helpers {
        public static BlockBehaviour.Properties multiblockComponent(BlockBehaviour.Properties properties) {
            // Can't be mined by hand, but will break from an explosion. Drops nothing.
            return properties.strength(-1, 0).noLootTable();
        }

        public static <T extends Block> Map<DyeColor, BlockEntry<T>> createColouredVariants(Function<DyeColor, BlockEntry<T>> factory) {
            HashMap<DyeColor, BlockEntry<T>> map = new HashMap<>();

            for (DyeColor v : DyeColor.values()) {
                map.put(v, factory.apply(v));
            }

            return map;
        }

        public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> subdirCubeAllTexture(String... paths) {
            StringBuilder sb = new StringBuilder();
            sb.append("block/");
            for (String dir : paths) {
                sb.append(dir).append('/');
            }
            final String path = sb.toString();

            return (ctx, prov) -> {
                ResourceLocation textureLoc = ResourceLocation.fromNamespaceAndPath(MODID, path + ctx.getName());
                ResourceLocation cubeAll = ResourceLocation.parse("block/cube_all");
                prov.simpleBlock(ctx.get(), prov.models().singleTexture(ctx.getName(), cubeAll, "all", textureLoc));
            };
        }

        public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> ghostBlock() {
            return (ctx, prov) -> {
                ResourceLocation air = ResourceLocation.parse("block/air");
                prov.simpleBlock(ctx.get(), prov.models().withExistingParent(ctx.getName(), air));
            };
        }
    }


    public static void register() { }
}
