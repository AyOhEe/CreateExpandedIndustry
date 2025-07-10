package io.github.ayohee.expandedindustry.datagen.features;

import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.worldgen.HardenedStonePatchConfiguration;
import io.github.ayohee.expandedindustry.worldgen.HardenedStonePatchFeature.Types;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EIConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> HARDENED_ERYTHRITE_PATCH = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_erythrite_patch")
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> HARDENED_OCHRUM_PATCH = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_ochrum_patch")
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> HARDENED_CRIMSITE_PATCH = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_crimsite_patch")
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> HARDENED_ASURINE_PATCH = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_asurine_patch")
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> HARDENED_VERIDIUM_PATCH = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "hardened_veridium_patch")
    );

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> ctx) {
        FeatureUtils.register(ctx, HARDENED_ERYTHRITE_PATCH, EIFeatures.HARDENED_STONE_PATCH.get(),
                new HardenedStonePatchConfiguration(
                        EIBlocks.HARDENED_ERYTHRITE_BLOCK.get().defaultBlockState(),
                        EIBlocks.ERYTHRITE_BLOCK.get().defaultBlockState(),
                        Types.ERYTHRITE.serialised_value)
        );
        FeatureUtils.register(ctx, HARDENED_OCHRUM_PATCH,    EIFeatures.HARDENED_STONE_PATCH.get(),
                new HardenedStonePatchConfiguration(
                        EIBlocks.HARDENED_OCHRUM_BLOCK.get().defaultBlockState(),
                        AllPaletteStoneTypes.OCHRUM.getBaseBlock().get().defaultBlockState(),
                        Types.OCHRUM.serialised_value)
        );
        FeatureUtils.register(ctx, HARDENED_CRIMSITE_PATCH,  EIFeatures.HARDENED_STONE_PATCH.get(),
                new HardenedStonePatchConfiguration(
                        EIBlocks.HARDENED_CRIMSITE_BLOCK.get().defaultBlockState(),
                        AllPaletteStoneTypes.CRIMSITE.getBaseBlock().get().defaultBlockState(),
                        Types.CRIMSITE.serialised_value)
        );
        FeatureUtils.register(ctx, HARDENED_ASURINE_PATCH,   EIFeatures.HARDENED_STONE_PATCH.get(),
                new HardenedStonePatchConfiguration(
                        EIBlocks.HARDENED_ASURINE_BLOCK.get().defaultBlockState(),
                        AllPaletteStoneTypes.ASURINE.getBaseBlock().get().defaultBlockState(),
                        Types.ASURINE.serialised_value)
        );
        FeatureUtils.register(ctx, HARDENED_VERIDIUM_PATCH,  EIFeatures.HARDENED_STONE_PATCH.get(),
                new HardenedStonePatchConfiguration(
                        EIBlocks.HARDENED_VERIDIUM_BLOCK.get().defaultBlockState(),
                        AllPaletteStoneTypes.VERIDIUM.getBaseBlock().get().defaultBlockState(),
                        Types.VERIDIUM.serialised_value)
        );
    }
}
