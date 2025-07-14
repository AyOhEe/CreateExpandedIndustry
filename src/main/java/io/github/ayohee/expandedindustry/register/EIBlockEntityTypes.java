package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.ayohee.expandedindustry.content.blockentities.HardenedStoneBlockEntity;
import io.github.ayohee.expandedindustry.content.blockentities.HardenedStoneBlockEntity.RichnessProfile;
import io.github.ayohee.expandedindustry.content.pressurised_blocks.PressurisedFluidTankBlockEntity;
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


    public static void register() { }
}
