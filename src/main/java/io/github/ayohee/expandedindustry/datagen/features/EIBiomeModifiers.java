package io.github.ayohee.expandedindustry.datagen.features;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EIBiomeModifiers {
    public static final ResourceKey<BiomeModifier> HARDENED_ERYTHRITE_PATCH = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_erythrite_patch")
    );
    public static final ResourceKey<BiomeModifier> HARDENED_OCHRUM_PATCH = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_ochrum_patch")
    );
    public static final ResourceKey<BiomeModifier> HARDENED_CRIMSITE_PATCH = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_crimsite_patch")
    );
    public static final ResourceKey<BiomeModifier> HARDENED_ASURINE_PATCH = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_asurine_patch")
    );
    public static final ResourceKey<BiomeModifier> HARDENED_VERIDIUM_PATCH = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_veridium_patch")
    );

    public static void bootstrap(BootstrapContext<BiomeModifier> ctx) {
        HolderGetter<Biome> biomeLookup = ctx.lookup(Registries.BIOME);
        HolderSet<Biome> isOverworld = biomeLookup.getOrThrow(BiomeTags.IS_OVERWORLD);

        HolderGetter<PlacedFeature> featureLookup = ctx.lookup(Registries.PLACED_FEATURE);
        Holder<PlacedFeature> hardenedErythritePatch = featureLookup.getOrThrow(EIPlacedFeatures.HARDENED_ERYTHRITE_PATCH);
        Holder<PlacedFeature> hardenedOchrumPatch = featureLookup.getOrThrow(EIPlacedFeatures.HARDENED_OCHRUM_PATCH);
        Holder<PlacedFeature> hardenedCrimsitePatch = featureLookup.getOrThrow(EIPlacedFeatures.HARDENED_CRIMSITE_PATCH);
        Holder<PlacedFeature> hardenedAsurinePatch = featureLookup.getOrThrow(EIPlacedFeatures.HARDENED_ASURINE_PATCH);
        Holder<PlacedFeature> hardenedVeridiumPatch = featureLookup.getOrThrow(EIPlacedFeatures.HARDENED_VERIDIUM_PATCH);

        // Later phase than UNDERGROUND_ORES to reduce overlap. Consistently finding this feature is important to gameplay
        ctx.register(HARDENED_ERYTHRITE_PATCH, new BiomeModifiers.AddFeaturesBiomeModifier(isOverworld,
                HolderSet.direct(hardenedErythritePatch), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        ctx.register(HARDENED_OCHRUM_PATCH, new BiomeModifiers.AddFeaturesBiomeModifier(isOverworld,
                HolderSet.direct(hardenedOchrumPatch), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        ctx.register(HARDENED_CRIMSITE_PATCH, new BiomeModifiers.AddFeaturesBiomeModifier(isOverworld,
                HolderSet.direct(hardenedCrimsitePatch), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        ctx.register(HARDENED_ASURINE_PATCH, new BiomeModifiers.AddFeaturesBiomeModifier(isOverworld,
                HolderSet.direct(hardenedAsurinePatch), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        ctx.register(HARDENED_VERIDIUM_PATCH, new BiomeModifiers.AddFeaturesBiomeModifier(isOverworld,
                HolderSet.direct(hardenedVeridiumPatch), GenerationStep.Decoration.UNDERGROUND_DECORATION));
    }
}
