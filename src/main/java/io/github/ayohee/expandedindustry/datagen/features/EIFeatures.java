package io.github.ayohee.expandedindustry.datagen.features;

import io.github.ayohee.expandedindustry.register.EIRegistries;
import io.github.ayohee.expandedindustry.worldgen.HardenedStonePatchFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;

public class EIFeatures {
    public static final DeferredHolder<Feature<?>, HardenedStonePatchFeature> HARDENED_STONE_PATCH =
            EIRegistries.FEATURES.register("hardened_stone_patch", HardenedStonePatchFeature::new);

    public static void register() {}
}
