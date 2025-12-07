package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.api.contraption.ContraptionMovementSetting;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

import static io.github.ayohee.expandedindustry.register.EIBlocks.*;

public class EIContraptionMovementSettings {
    public EIContraptionMovementSettings() {
    }

    private static final Supplier<?>[] unmovables = {
            REINFORCED_DRILL_MULTIBLOCK,
            FRACTIONATING_COLUMN_MULTIBLOCK,
            FLARE_STACK_MULTIBLOCK,
            CRACKING_COLUMN_MULTIBLOCK,
            CRACKING_COLUMN_MODEL,
            MULTIBLOCK_GHOST,
            MULTIBLOCK_INVENTORY,
            MULTIBLOCK_KINETIC_IO,
            MULTIBLOCK_FLUID_IO,
            MULTIBLOCK_FLUID_INPUT
    };

    public static void registerDefaults() {
        for (Supplier<?> b : unmovables) {
            ContraptionMovementSetting.REGISTRY.register(((Supplier<Block>)b).get(), () -> ContraptionMovementSetting.UNMOVABLE);
        }
    }
}