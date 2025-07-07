package io.github.ayohee.expandedindustry.register;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;


public class EIConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue PRESSURISED_TANK_CAPACITY_MULTIPLIER = BUILDER
            .comment("The capacity of pressurised fluid tanks relative to the capacity of normal fluid tanks, expressed as a multiplier")
            .defineInRange("pressurisedTankCapacityMultiplier", 1.5, 1.0, 32.0);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static void register(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, SPEC);
    }
}
