package io.github.ayohee.expandedindustry.register;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.ayohee.expandedindustry.content.pressurised_blocks.PressurisedFluidTankBlockEntity;
import io.github.ayohee.expandedindustry.content.pressurised_blocks.PressurisedFluidTankRenderer;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.REGISTRATE;

public class EIBlockEntityTypes {
    public static final BlockEntityEntry<PressurisedFluidTankBlockEntity> PRESSURISED_FLUID_TANK = REGISTRATE
            .blockEntity("pressurised_fluid_tank", PressurisedFluidTankBlockEntity::new)
            .validBlocks(EIBlocks.PRESSURISED_FLUID_TANK)
            //.renderer(() -> PressurisedFluidTankRenderer::new) FIXME this should work
            .register();

    public static void register() { }
}
