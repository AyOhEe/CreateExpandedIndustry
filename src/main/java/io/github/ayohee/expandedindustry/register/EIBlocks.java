package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.AllDisplaySources;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllMountedStorageTypes;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.decoration.palettes.ConnectedPillarBlock;
import com.simibubi.create.content.decoration.palettes.PaletteBlockPattern;
import com.simibubi.create.foundation.block.connected.*;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import io.github.ayohee.expandedindustry.content.blocks.HardenedStoneBlock;
import io.github.ayohee.expandedindustry.content.blocks.HighPressurePortBlock;
import io.github.ayohee.expandedindustry.content.blocks.LoopingJukeboxBlock;
import io.github.ayohee.expandedindustry.content.complex.crackingcolumn.CrackingColumnBaseBlock;
import io.github.ayohee.expandedindustry.content.complex.crackingcolumn.CrackingColumnModelBlock;
import io.github.ayohee.expandedindustry.content.complex.crackingcolumn.CrackingColumnMultiblock;
import io.github.ayohee.expandedindustry.content.complex.flarestack.FlareStackMultiblock;
import io.github.ayohee.expandedindustry.content.complex.flarestack.FlareStackVentBlock;
import io.github.ayohee.expandedindustry.content.complex.fractionatingcolumn.FractionatingColumnModelBlock;
import io.github.ayohee.expandedindustry.content.complex.fractionatingcolumn.FractionatingColumnMultiblock;
import io.github.ayohee.expandedindustry.content.complex.fractionatingcolumn.FractionatingColumnBaseBlock;
import io.github.ayohee.expandedindustry.content.complex.pressurisedTank.*;
import io.github.ayohee.expandedindustry.content.complex.reinforcedDrill.*;
import io.github.ayohee.expandedindustry.multiblock.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType.mountedFluidStorage;
import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;
import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.REGISTRATE;
import static io.github.ayohee.expandedindustry.register.EIBlocks.EISpriteShifts.paletteCT;



//TODO go through all of this shit and apply .requiresCorrectToolForDrops() where appropriate
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

    public static final BlockEntry<FractionatingColumnMultiblock> FRACTIONATING_COLUMN_MULTIBLOCK = REGISTRATE
            .block("fractionating_column_multiblock", FractionatingColumnMultiblock::new)
            .blockstate(FractionatingColumnMultiblock::generateBlockstate)
            .initialProperties(() -> Blocks.GLASS)
            .properties(Helpers::multiblockComponent)
            .properties(c -> c
                    .mapColor(MapColor.COLOR_BLUE)
                    .sound(SoundType.METAL))
            .register();

    public static final BlockEntry<FractionatingColumnModelBlock> FRACTIONATING_COLUMN_MODEL = REGISTRATE
            .block("fractionating_column_model", FractionatingColumnModelBlock::new)
            .blockstate(FractionatingColumnModelBlock::generateBlockstate)
            .initialProperties(() -> Blocks.GLASS)
            .properties(Helpers::multiblockComponent)
            .properties(c -> c
                    .mapColor(MapColor.COLOR_BLUE)
                    .sound(SoundType.METAL))
            .register();

    public static final BlockEntry<FlareStackMultiblock> FLARE_STACK_MULTIBLOCK = REGISTRATE
            .block("flare_stack_multiblock", FlareStackMultiblock::new)
            .blockstate(Helpers.ghostBlock())
            .initialProperties(() -> Blocks.GLASS)
            .properties(Helpers::multiblockComponent)
            .properties(c -> c
                    .mapColor(MapColor.COLOR_BLUE)
                    .sound(SoundType.METAL))
            .register();

    public static final BlockEntry<CrackingColumnMultiblock> CRACKING_COLUMN_MULTIBLOCK = REGISTRATE
            .block("cracking_column_multiblock", CrackingColumnMultiblock::new)
            .blockstate(CrackingColumnMultiblock::generateBlockstate)
            .initialProperties(() -> Blocks.GLASS)
            .properties(Helpers::multiblockComponent)
            .properties(c -> c
                    .mapColor(MapColor.COLOR_BLUE)
                    .sound(SoundType.METAL))
            .register();

    public static final BlockEntry<CrackingColumnModelBlock> CRACKING_COLUMN_MODEL = REGISTRATE
            .block("cracking_column_model", CrackingColumnModelBlock::new)
            .blockstate(CrackingColumnModelBlock::generateBlockstate)
            .initialProperties(() -> Blocks.GLASS)
            .properties(Helpers::multiblockComponent)
            .properties(c -> c
                    .mapColor(MapColor.COLOR_BLUE)
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

    public static final BlockEntry<MultiblockFluidTankBlock> MULTIBLOCK_FLUID_IO = REGISTRATE
            .block("multiblock_fluid_io", MultiblockFluidTankBlock::new)
            .blockstate(Helpers.ghostBlock())
            .initialProperties(() -> Blocks.GLASS)
            .properties(Helpers::multiblockComponent)
            .properties(c -> c
                    .mapColor(MapColor.COLOR_ORANGE)
                    .sound(SoundType.METAL))
            .register();

    public static final BlockEntry<MultiblockFluidInputBlock> MULTIBLOCK_FLUID_INPUT = REGISTRATE
            .block("multiblock_fluid_input", MultiblockFluidInputBlock::new)
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



    public static final BlockEntry<Block> COBALT_BLOCK = REGISTRATE.block("cobalt_block", Block::new)
            .initialProperties(SharedProperties::netheriteMetal)
            .properties(c -> c.mapColor(MapColor.COLOR_BLUE).destroyTime(20))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
            .blockstate(Helpers.subdirCubeAll())
            .simpleItem()
            .recipe((c, p) -> {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EIItems.COBALT_INGOT, 9)
                        .requires(c.get(), 1)
                        .unlockedBy("has_cobalt_block", RegistrateRecipeProvider.has(c.get()))
                        .save(p, "cobalt_ingots_from_block");
            })
            .register();

    public static final BlockEntry<CrackingColumnBaseBlock> CRACKING_COLUMN_BASE = REGISTRATE.block("cracking_column_base", CrackingColumnBaseBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(c -> c.mapColor(MapColor.COLOR_BLUE).destroyTime(20))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
            .blockstate(Helpers.subdirCubeBottomTop())
            .simpleItem()
            .recipe((c, p) -> {}) //TODO
            .register();

    public static final BlockEntry<FlareStackVentBlock> FLARE_STACK_VENT = REGISTRATE.block("flare_stack_vent", FlareStackVentBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(c -> c.mapColor(MapColor.COLOR_BLUE).destroyTime(20))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
            .blockstate(Helpers.subdirCubeTop())
            .simpleItem()
            .recipe((c, p) -> {}) //TODO
            .register();

    public static final BlockEntry<FractionatingColumnBaseBlock> FRACTIONATING_COLUMN_BASE = REGISTRATE.block("fractionating_column_base", FractionatingColumnBaseBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(c -> c.mapColor(MapColor.COLOR_BLUE).destroyTime(20))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
            .blockstate(Helpers.subdirCubeBottomTop())
            .simpleItem()
            .recipe((c, p) -> {}) //TODO
            .register();

    public static final BlockEntry<PressurisedFluidTankBlock> PRESSURISED_FLUID_TANK = REGISTRATE.block("pressurised_fluid_tank", PressurisedFluidTankBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(p -> p.noOcclusion()
                    .isRedstoneConductor((p1, p2, p3) -> true)
                    .mapColor(MapColor.COLOR_BLUE))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(new PressurisedFluidTankGenerator()::generate)
            .onRegister(CreateRegistrate.blockModel(() -> PressurisedFluidTankModel::standard))
            .transform(displaySource(AllDisplaySources.BOILER)) //TODO remove?
            .transform(mountedFluidStorage(AllMountedStorageTypes.FLUID_TANK)) //TODO clone?
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
            .blockstate(Helpers.subdirCubeAll())
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

    public static final BlockEntry<HighPressurePortBlock> HIGH_PRESSURE_PORT = REGISTRATE
            .block("high_pressure_port", HighPressurePortBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .blockstate(HighPressurePortBlock::generateBlockstate)
            .properties(p -> p.mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .noOcclusion())
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
            .simpleItem()
            .register();

    public static final BlockEntry<LoopingJukeboxBlock> LOOPING_JUKEBOX = REGISTRATE
            .block("looping_jukebox", LoopingJukeboxBlock::new)
            .initialProperties(() -> Blocks.JUKEBOX)
            .blockstate((ctx, prov) -> prov.getVariantBuilder(ctx.get()).forAllStates((state) -> {
                    return ConfiguredModel.builder().modelFile(
                            prov.models().withExistingParent("looping_jukebox", ResourceLocation.withDefaultNamespace("cube_top"))
                            .texture("top", ResourceLocation.fromNamespaceAndPath(MODID, "block/looping_jukebox/looping_jukebox_top"))
                            .texture("side", ResourceLocation.fromNamespaceAndPath(MODID, "block/looping_jukebox/looping_jukebox_side"))
                    ).build();
            }))
            .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EIBlocks.LOOPING_JUKEBOX)
                    .pattern("p")
                    .pattern("j")
                    .define('p', AllItems.PRECISION_MECHANISM)
                    .define('j', Blocks.JUKEBOX.asItem())
                    .unlockedBy("has_precision_mechanism", RegistrateRecipeProvider.has(AllItems.PRECISION_MECHANISM))
                    .save(prov, "looping_jukebox_precision_mechanism")
            )
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

        public static CTSpriteShiftEntry paletteCT(String variant, String texture, PaletteBlockPattern.CTs ct) {
            ResourceLocation resLoc = ResourceLocation.fromNamespaceAndPath(MODID, "block/palettes/stone_types/" + texture + "/" + variant + "_" + texture);
            return CTSpriteShifter.getCT(ct.type, resLoc,
                    ResourceLocation.fromNamespaceAndPath(resLoc.getNamespace(), resLoc.getPath() + "_connected"));
        }
    }



    /*----- THESE BLOCKS WILL BE SEEN IN THE DECORATIVES TAB -----*/
    static {
        REGISTRATE.setCreativeTab(EICreativeTabs.DECORATIVES_TAB);
    }



    public static final BlockEntry<Block> ERYTHRITE_BLOCK = REGISTRATE.block("erythrite", Block::new)
            .initialProperties(SharedProperties::stone)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .properties(c -> c.mapColor(DyeColor.MAGENTA))
            .blockstate(Helpers.subdirCubeAll())
            .simpleItem()
            .register();

    public static final BlockEntry<HardenedStoneBlock> HARDENED_ERYTHRITE_BLOCK = REGISTRATE
            .block("hardened_erythrite", (p) -> new HardenedStoneBlock(p, EIBlockEntityTypes.HARDENED_ERYTHRITE))
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(Helpers::hardened_stones)
            .properties(c -> c.mapColor(MapColor.COLOR_MAGENTA))
            .blockstate(Helpers.subdirCubeAll())
            .simpleItem()
            .register();

    public static final BlockEntry<HardenedStoneBlock> HARDENED_OCHRUM_BLOCK = REGISTRATE
            .block("hardened_ochrum", (p) -> new HardenedStoneBlock(p, EIBlockEntityTypes.HARDENED_OCHRUM))
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(Helpers::hardened_stones)
            .properties(c -> c.mapColor(MapColor.GOLD))
            .blockstate(Helpers.subdirCubeAll())
            .simpleItem()
            .register();

    public static final BlockEntry<HardenedStoneBlock> HARDENED_VERIDIUM_BLOCK = REGISTRATE
            .block("hardened_veridium", (p) -> new HardenedStoneBlock(p, EIBlockEntityTypes.HARDENED_VERIDIUM))
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(Helpers::hardened_stones)
            .properties(c -> c.mapColor(MapColor.COLOR_GREEN))
            .blockstate(Helpers.subdirCubeAll())
            .simpleItem()
            .register();

    public static final BlockEntry<HardenedStoneBlock> HARDENED_ASURINE_BLOCK = REGISTRATE
            .block("hardened_asurine", (p) -> new HardenedStoneBlock(p, EIBlockEntityTypes.HARDENED_ASURINE))
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(Helpers::hardened_stones)
            .properties(c -> c.mapColor(MapColor.COLOR_BLUE))
            .blockstate(Helpers.subdirCubeAll())
            .simpleItem()
            .register();

    public static final BlockEntry<HardenedStoneBlock> HARDENED_CRIMSITE_BLOCK = REGISTRATE
            .block("hardened_crimsite", (p) -> new HardenedStoneBlock(p, EIBlockEntityTypes.HARDENED_CRIMSITE))
            .initialProperties(() -> Blocks.BEDROCK)
            .properties(Helpers::hardened_stones)
            .properties(c -> c.mapColor(MapColor.COLOR_RED))
            .blockstate(Helpers.subdirCubeAll())
            .simpleItem()
            .register();



    public static final BlockEntry<ConnectedPillarBlock> COBALT_PILLAR = REGISTRATE
            .block("cobalt_pillar", ConnectedPillarBlock::new)
            .initialProperties(() -> Blocks.STONE_BRICKS)
            .tag(BlockTags.NEEDS_STONE_TOOL)
            .blockstate((ctx, prov) -> {
                ResourceLocation side = ResourceLocation.fromNamespaceAndPath(MODID, "block/palettes/stone_types/pillar/cobalt_pillar");
                ResourceLocation end = ResourceLocation.fromNamespaceAndPath(MODID, "block/palettes/stone_types/cap/cobalt_cap");
                prov.getVariantBuilder(ctx.getEntry())
                        .forAllStatesExcept(state -> {
                                    Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);
                                    if (axis == Direction.Axis.Y)
                                        return ConfiguredModel.builder()
                                                .modelFile(prov.models()
                                                        .cubeColumn("cobalt_pillar", side, end))
                                                .uvLock(false)
                                                .build();
                                    return ConfiguredModel.builder()
                                            .modelFile(prov.models()
                                                    .cubeColumnHorizontal("cobalt_pillar_horizontal", side, end))
                                            .uvLock(false)
                                            .rotationX(90)
                                            .rotationY(axis == Direction.Axis.X ? 90 : 0)
                                            .build();
                                }, BlockStateProperties.WATERLOGGED, ConnectedPillarBlock.NORTH, ConnectedPillarBlock.SOUTH,
                                ConnectedPillarBlock.EAST, ConnectedPillarBlock.WEST);
            })
            .onRegister(CreateRegistrate.connectedTextures(
                    () -> new RotatedPillarCTBehaviour(
                            paletteCT("cobalt", "pillar", PaletteBlockPattern.CTs.PILLAR),
                            paletteCT("cobalt", "cap", PaletteBlockPattern.CTs.CAP)
                    )))
            .simpleItem()
            .recipe((ctx, prov) -> {
                prov.stonecutting(DataIngredient.items(EIItems.COBALT_INGOT.get()), RecipeCategory.DECORATIONS, ctx::get, 2);
            })
            .register();

    public static final BlockEntry<Block> TUFF_TILES = REGISTRATE.block("tuff_tiles", Block::new)
            .initialProperties(() -> Blocks.TUFF_BRICKS)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(Helpers.subdirCubeAll())
            .recipe((ctx, prov) -> {
                prov.stonecutting(DataIngredient.items(Blocks.TUFF), RecipeCategory.DECORATIONS, ctx::get, 1);
            })
            .simpleItem()
            .register();

    public static final BlockEntry<SlabBlock> TUFF_TILE_SLAB = REGISTRATE.block("tuff_tile_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.TUFF_SLAB)
            .blockstate(Helpers.subdirSlab("tuff_tiles"))
            .recipe((ctx, prov) -> {
                prov.stonecutting(DataIngredient.items((NonNullSupplier<? extends Block>) EIBlocks.TUFF_TILES), RecipeCategory.DECORATIONS, ctx::get, 2);
            })
            .simpleItem()
            .register();

    public static final BlockEntry<StairBlock> TUFF_TILE_STAIRS = REGISTRATE.block("tuff_tile_stairs", (p) -> new StairBlock(EIBlocks.TUFF_TILES.getDefaultState(), p))
            .initialProperties(() -> Blocks.TUFF_BRICK_STAIRS)
            .blockstate(Helpers.subdirStairs("tuff_tiles"))
            .recipe((ctx, prov) -> {
                prov.stonecutting(DataIngredient.items((NonNullSupplier<? extends Block>) EIBlocks.TUFF_TILES), RecipeCategory.DECORATIONS, ctx::get, 1);
            })
            .simpleItem()
            .register();

    public static final BlockEntry<Block> MICROPLASTIC_BLOCK = REGISTRATE
            .block("microplastic_block", Block::new)
            .initialProperties(() -> Blocks.SAND)
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .properties(c -> c.mapColor(MapColor.COLOR_LIGHT_GRAY))
            .blockstate(Helpers.subdirCubeAllRotated("microplastic"))
            .item()
            .tag(EITags.MICROPLASTIC_BLOCK)
            .build()
            .register();

    public static final Map<DyeColor, BlockEntry<Block>> DYED_MICROPLASTIC_BLOCKS = Helpers.createColouredVariants((DyeColor v) -> {
        return REGISTRATE
                .block(v.toString() + "_microplastic_block", Block::new)
                .initialProperties(() -> Blocks.SAND)
                .tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .properties(c -> c.mapColor(v.getMapColor()))
                .blockstate(Helpers.subdirCubeAllRotated("microplastic"))
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

    public static final BlockEntry<Block> ASPHALT_BLOCK = REGISTRATE
            .block("asphalt_block", Block::new)
            .initialProperties(() -> Blocks.STONE_BRICKS)
            .tag(BlockTags.NEEDS_STONE_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
            .properties(c -> c.mapColor(MapColor.COLOR_GRAY).speedFactor(1.2f))
            .blockstate(Helpers.subdirCubeAll("asphalt"))
            .item()
            .tag(EITags.ASPHALT_BLOCK)
            .build()
            .register();

    public static final Map<DyeColor, BlockEntry<Block>> DYED_ASPHALT_BLOCKS = Helpers.createColouredVariants((DyeColor v) -> {
        return REGISTRATE
                .block(v.toString() + "_asphalt_block", Block::new)
                .initialProperties(() -> Blocks.STONE_BRICKS)
                .tag(BlockTags.NEEDS_STONE_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .properties(c -> c.mapColor(v.getMapColor()).speedFactor(1.2f))
                .blockstate(Helpers.subdirCubeAll("asphalt"))
                .item()
                .tag(v.getDyedTag())
                .tag(EITags.ASPHALT_BLOCK)
                .build()
                .recipe((ctx, prov) -> {
                    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EIBlocks.DYED_ASPHALT_BLOCKS.get(v).asStack(8))
                            .pattern("PPP")
                            .pattern("PDP")
                            .pattern("PPP")
                            .define('P', EITags.ASPHALT_BLOCK)
                            .define('D', v.getTag())
                            .unlockedBy("has_asphalt", RegistrateRecipeProvider.has(EIBlocks.ASPHALT_BLOCK))
                            .save(prov, v.toString() + "_dyed_asphalt_block");
                })
                .register();
    });



    public static class Helpers {
        public static BlockBehaviour.Properties hardened_stones(BlockBehaviour.Properties properties) {
            return properties.explosionResistance(0).noLootTable();
        }

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

        @NotNull
        private static String buildSubdirPath(String[] paths) {
            StringBuilder sb = new StringBuilder();
            sb.append("block/");
            for (String dir : paths) {
                sb.append(dir).append('/');
            }
            final String path = sb.toString();
            return path;
        }

        public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> subdirCubeAll(String... paths) {
            final String path = buildSubdirPath(paths);

            return (ctx, prov) -> {
                ResourceLocation textureLoc = ResourceLocation.fromNamespaceAndPath(MODID, path + ctx.getName());
                ResourceLocation cubeAll = ResourceLocation.withDefaultNamespace("block/cube_all");

                prov.simpleBlock(ctx.get(), prov.models().singleTexture(ctx.getName(), cubeAll, "all", textureLoc));
            };
        }

        public static <T extends SlabBlock> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> subdirSlab(String baseName) {
            String path = "block/" + baseName;

            return (ctx, prov) -> {
                ResourceLocation textureLoc = ResourceLocation.fromNamespaceAndPath(MODID, path);
                ResourceLocation fullBlock = ResourceLocation.fromNamespaceAndPath(MODID, baseName);

                prov.slabBlock(ctx.get(), fullBlock,textureLoc);
            };
        }

        public static <T extends StairBlock> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> subdirStairs(String baseName) {
            String path = "block/" + baseName;

            return (ctx, prov) -> {
                ResourceLocation textureLoc = ResourceLocation.fromNamespaceAndPath(MODID, path);

                prov.stairsBlock(ctx.get(), textureLoc);
            };
        }


        public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> subdirCubeAllRotated(String... paths) {
            final String path = buildSubdirPath(paths);

            return (ctx, prov) -> {
                ResourceLocation textureLoc = ResourceLocation.fromNamespaceAndPath(MODID, path + ctx.getName());
                ResourceLocation cubeAll = ResourceLocation.withDefaultNamespace("block/cube_all");

                BlockModelBuilder model = prov.models().singleTexture(ctx.getName(), cubeAll, "all", textureLoc);
                prov.getVariantBuilder(ctx.get()).addModels(prov.getExistingVariantBuilder(ctx.get()).get().partialState(), ConfiguredModel.allRotations(model, false, 1));
            };
        }


        public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> subdirCubeBottomTop(String... paths) {
            final String path = buildSubdirPath(paths);

            return (ctx, prov) -> {
                ResourceLocation sideTextureLoc = ResourceLocation.fromNamespaceAndPath(MODID, path + ctx.getName() + "_side");
                ResourceLocation topTextureLoc = ResourceLocation.fromNamespaceAndPath(MODID, path + ctx.getName() + "_top");
                ResourceLocation bottomTextureLoc = ResourceLocation.fromNamespaceAndPath(MODID, path + ctx.getName() + "_bottom");
                ResourceLocation cubeAll = ResourceLocation.withDefaultNamespace("block/cube_bottom_top");

                prov.simpleBlock(ctx.get(), prov.models().withExistingParent(ctx.getName(), cubeAll)
                        .texture("top", topTextureLoc)
                        .texture("bottom", bottomTextureLoc)
                        .texture("side", sideTextureLoc)
                );
            };
        }

        public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> subdirCubeTop(String... paths) {
            final String path = buildSubdirPath(paths);

            return (ctx, prov) -> {
                ResourceLocation sideTextureLoc = ResourceLocation.fromNamespaceAndPath(MODID, path + ctx.getName() + "_side");
                ResourceLocation topTextureLoc = ResourceLocation.fromNamespaceAndPath(MODID, path + ctx.getName() + "_top");
                ResourceLocation cubeAll = ResourceLocation.withDefaultNamespace("block/cube_top");

                prov.simpleBlock(ctx.get(), prov.models().withExistingParent(ctx.getName(), cubeAll)
                        .texture("top", topTextureLoc)
                        .texture("side", sideTextureLoc)
                );
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
