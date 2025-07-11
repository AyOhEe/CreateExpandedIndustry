package io.github.ayohee.expandedindustry.datagen.features;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EIPlacedFeatures {
    public static final ResourceKey<PlacedFeature> HARDENED_ERYTHRITE_PATCH = ResourceKey.create(
            Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_erythrite_patch")
    );
    public static final ResourceKey<PlacedFeature> HARDENED_OCHRUM_PATCH = ResourceKey.create(
            Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_ochrum_patch")
    );
    public static final ResourceKey<PlacedFeature> HARDENED_CRIMSITE_PATCH = ResourceKey.create(
            Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_crimsite_patch")
    );
    public static final ResourceKey<PlacedFeature> HARDENED_ASURINE_PATCH = ResourceKey.create(
            Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_asurine_patch")
    );
    public static final ResourceKey<PlacedFeature> HARDENED_VERIDIUM_PATCH = ResourceKey.create(
            Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_veridium_patch")
    );

    public static void bootstrap(BootstrapContext<PlacedFeature> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> featureLookup = ctx.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> hardenedErythritePatch = featureLookup.getOrThrow(EIConfiguredFeatures.HARDENED_ERYTHRITE_PATCH);
        Holder<ConfiguredFeature<?, ?>> hardenedOchrumPatch = featureLookup.getOrThrow(EIConfiguredFeatures.HARDENED_OCHRUM_PATCH);
        Holder<ConfiguredFeature<?, ?>> hardenedCrimsitePatch = featureLookup.getOrThrow(EIConfiguredFeatures.HARDENED_CRIMSITE_PATCH);
        Holder<ConfiguredFeature<?, ?>> hardenedAsurinePatch = featureLookup.getOrThrow(EIConfiguredFeatures.HARDENED_ASURINE_PATCH);
        Holder<ConfiguredFeature<?, ?>> hardenedVeridiumPatch = featureLookup.getOrThrow(EIConfiguredFeatures.HARDENED_VERIDIUM_PATCH);

        int PATCH_RARITY = 128*128*5;
        PlacementUtils.register(ctx, HARDENED_ERYTHRITE_PATCH, hardenedErythritePatch, patchPlacementModifiers(PATCH_RARITY, -30, 30));
        PlacementUtils.register(ctx, HARDENED_OCHRUM_PATCH,    hardenedOchrumPatch,    patchPlacementModifiers(PATCH_RARITY, -30, 30));
        PlacementUtils.register(ctx, HARDENED_CRIMSITE_PATCH,  hardenedCrimsitePatch,  patchPlacementModifiers(PATCH_RARITY, -30, 30));
        PlacementUtils.register(ctx, HARDENED_ASURINE_PATCH,   hardenedAsurinePatch,   patchPlacementModifiers(PATCH_RARITY, -30, 30));
        PlacementUtils.register(ctx, HARDENED_VERIDIUM_PATCH,  hardenedVeridiumPatch,  patchPlacementModifiers(PATCH_RARITY, -30, 30));
    }

    //TODO enforce spacing. Maybe refuse to generate if there's another one too close?
    public static List<PlacementModifier> patchPlacementModifiers(int rarity, int minHeight, int maxHeight) {
        return List.of(
                RarityFilter.onAverageOnceEvery(rarity),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight))
        );
    }
}
