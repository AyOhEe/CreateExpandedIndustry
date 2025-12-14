package io.github.ayohee.expandedindustry.register;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class EIBiomeData {
    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> worldCarvers = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(EIBiomes.INLAND_OIL_FIELD, EIBiomes.inland_oil_field(placedFeatures, worldCarvers));
        context.register(EIBiomes.OCEAN_OIL_FIELD, EIBiomes.ocean_oil_field(placedFeatures, worldCarvers));
    }
}
