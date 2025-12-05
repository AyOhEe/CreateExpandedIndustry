package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.ayohee.expandedindustry.content.blockentities.HardenedStoneBlockEntity;
import io.github.ayohee.expandedindustry.content.blockentities.HardenedStoneBlockEntity.RichnessProfile;
import io.github.ayohee.expandedindustry.content.blockentities.LoopingJukeboxBlockEntity;
import io.github.ayohee.expandedindustry.content.complex.crackingcolumn.CrackingColumnMultiblockBE;
import io.github.ayohee.expandedindustry.content.complex.flarestack.FlareStackMultiblockBE;
import io.github.ayohee.expandedindustry.content.complex.fractionatingcolumn.FractionatingColumnMultiblockBE;
import io.github.ayohee.expandedindustry.content.complex.pressurisedTank.PressurisedFluidTankBlockEntity;
import io.github.ayohee.expandedindustry.content.complex.reinforcedDrill.ReinforcedDrillMultiblockBE;
import io.github.ayohee.expandedindustry.multiblock.*;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.REGISTRATE;

public class EIBlockEntityTypes {
    public static final BlockEntityEntry<PressurisedFluidTankBlockEntity> PRESSURISED_FLUID_TANK = REGISTRATE
            .blockEntity("pressurised_fluid_tank", PressurisedFluidTankBlockEntity::new)
            .validBlocks(EIBlocks.PRESSURISED_FLUID_TANK)
            //.renderer(() -> PressurisedFluidTankRenderer::new) FIXME this should work
            .register();



    private static final Supplier<RichnessProfile> HARDENED_ERYTHRITE_PROFILE = () -> new RichnessProfile(
            EIBlocks.ERYTHRITE_BLOCK.asStack(),
            10,
            50,
            10000,
            0.1f
    );

    private static final Supplier<RichnessProfile> HARDENED_OCHRUM_PROFILE = () -> new RichnessProfile(
            new ItemStack(AllPaletteStoneTypes.OCHRUM.getBaseBlock().get().asItem()),
            10,
            50,
            10000,
            0.1f
    );

    private static final Supplier<RichnessProfile> HARDENED_CRIMSITE_PROFILE = () -> new RichnessProfile(
            new ItemStack(AllPaletteStoneTypes.CRIMSITE.getBaseBlock().get().asItem()),
            10,
            50,
            10000,
            0.1f
    );

    private static final Supplier<RichnessProfile> HARDENED_ASURINE_PROFILE = () -> new RichnessProfile(
            new ItemStack(AllPaletteStoneTypes.ASURINE.getBaseBlock().get().asItem()),
            10,
            50,
            10000,
            0.1f
    );

    private static final Supplier<RichnessProfile> HARDENED_VERIDIUM_PROFILE = () -> new RichnessProfile(
            new ItemStack(AllPaletteStoneTypes.VERIDIUM.getBaseBlock().get().asItem()),
            10,
            50,
            10000,
            0.1f
    );


    public static final BlockEntityEntry<HardenedStoneBlockEntity> HARDENED_ERYTHRITE = REGISTRATE
            .blockEntity("hardened_erythrite", HardenedStoneBlockEntity.createNew(HARDENED_ERYTHRITE_PROFILE))
            .validBlocks(EIBlocks.HARDENED_ERYTHRITE_BLOCK)
            .register();

    public static final BlockEntityEntry<HardenedStoneBlockEntity> HARDENED_OCHRUM = REGISTRATE
            .blockEntity("hardened_ochrum", HardenedStoneBlockEntity.createNew(HARDENED_OCHRUM_PROFILE))
            .validBlocks(EIBlocks.HARDENED_OCHRUM_BLOCK)
            .register();

    public static final BlockEntityEntry<HardenedStoneBlockEntity> HARDENED_CRIMSITE = REGISTRATE
            .blockEntity("hardened_crimsite", HardenedStoneBlockEntity.createNew(HARDENED_CRIMSITE_PROFILE))
            .validBlocks(EIBlocks.HARDENED_CRIMSITE_BLOCK)
            .register();

    public static final BlockEntityEntry<HardenedStoneBlockEntity> HARDENED_ASURINE = REGISTRATE
            .blockEntity("hardened_asurine", HardenedStoneBlockEntity.createNew(HARDENED_ASURINE_PROFILE))
            .validBlocks(EIBlocks.HARDENED_ASURINE_BLOCK)
            .register();

    public static final BlockEntityEntry<HardenedStoneBlockEntity> HARDENED_VERIDIUM = REGISTRATE
            .blockEntity("hardened_veridium", HardenedStoneBlockEntity.createNew(HARDENED_VERIDIUM_PROFILE))
            .validBlocks(EIBlocks.HARDENED_VERIDIUM_BLOCK)
            .register();


    public static final BlockEntityEntry<LoopingJukeboxBlockEntity> LOOPING_JUKEBOX = REGISTRATE
            .blockEntity("looping_jukebox", LoopingJukeboxBlockEntity::new)
            .validBlocks(EIBlocks.LOOPING_JUKEBOX)
            .register();



    public static final BlockEntityEntry<MultiblockGhostBE> MULTIBLOCK_GHOST = REGISTRATE
            .blockEntity("multiblock_ghost", MultiblockGhostBE::new)
            .validBlocks(EIBlocks.MULTIBLOCK_GHOST)
            .register();

    public static final BlockEntityEntry<MultiblockKineticIOBE> MULTIBLOCK_KINETIC_IO = REGISTRATE
            .blockEntity("multiblock_kinetic_io", MultiblockKineticIOBE::new)
            .visual(() -> MultiblockKineticIOVisual::new, false)
            .validBlocks(EIBlocks.MULTIBLOCK_KINETIC_IO)
            //.renderer(() -> RDKBERenderer::new) FIXME this should work
            .register();

    public static final BlockEntityEntry<MultiblockInventoryBE> MULTIBLOCK_INVENTORY = REGISTRATE
            .blockEntity("multiblock_inventory", MultiblockInventoryBE::new)
            .validBlocks(EIBlocks.MULTIBLOCK_INVENTORY)
            .register();

    public static final BlockEntityEntry<MultiblockFluidTankBE> MULTIBLOCK_FLUID_IO = REGISTRATE
            .blockEntity("multiblock_fluid_io", MultiblockFluidTankBE::new)
            .validBlocks(EIBlocks.MULTIBLOCK_FLUID_IO)
            .register();

    public static final BlockEntityEntry<MultiblockFluidInputBE> MULTIBLOCK_FLUID_INPUT = REGISTRATE
            .blockEntity("multiblock_fluid_input", MultiblockFluidInputBE::new)
            .validBlocks(EIBlocks.MULTIBLOCK_FLUID_INPUT)
            .register();

    public static final BlockEntityEntry<ReinforcedDrillMultiblockBE> REINFORCED_DRILL_MULTIBLOCK = REGISTRATE
            .blockEntity("reinforced_drill_multiblock", ReinforcedDrillMultiblockBE::new)
            .validBlocks(EIBlocks.REINFORCED_DRILL_MULTIBLOCK)
            .register();

    public static final BlockEntityEntry<FractionatingColumnMultiblockBE> FRACTIONATING_COLUMN_MULTIBLOCK = REGISTRATE
            .blockEntity("fractionating_column_multiblock", FractionatingColumnMultiblockBE::new)
            .validBlocks(EIBlocks.FRACTIONATING_COLUMN_MULTIBLOCK)
            .register();

    public static final BlockEntityEntry<FlareStackMultiblockBE> FLARE_STACK_MULTIBLOCK = REGISTRATE
            .blockEntity("flare_stack_multiblock", FlareStackMultiblockBE::new)
            .validBlocks(EIBlocks.FLARE_STACK_MULTIBLOCK)
            .register();

    public static final BlockEntityEntry<CrackingColumnMultiblockBE> CRACKING_COLUMN_MULTIBLOCK = REGISTRATE
            .blockEntity("cracking_column_multiblock", CrackingColumnMultiblockBE::new)
            .validBlocks(EIBlocks.CRACKING_COLUMN_MULTIBLOCK)
            .register();

    public static final BlockEntityEntry<MultiblockModelBE> CRACKING_COLUMN_MODEL = REGISTRATE
            .blockEntity("cracking_column_model", MultiblockModelBE::new)
            .validBlocks(EIBlocks.CRACKING_COLUMN_MODEL)
            .register();

    public static void register() { }
}
