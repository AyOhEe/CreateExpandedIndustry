package io.github.ayohee.expandedindustry.register;


import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import javax.annotation.Nullable;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EIBiomes {
    public static final ResourceKey<Biome> INLAND_OIL_FIELD = register("inland_oil_field");
    public static final ResourceKey<Biome> OCEAN_OIL_FIELD = register("ocean_oil_field");

    private static ResourceKey<Biome> register(String key) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MODID, key));
    }

    public static Biome inland_oil_field(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {

        MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.desertSpawns(mobspawnsettings$builder);
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        BiomeDefaultFeatures.addFossilDecoration(biomegenerationsettings$builder);
        globalOverworldGeneration(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultOres(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultFlowers(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultGrass(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDesertVegetation(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDesertExtraVegetation(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDesertExtraDecoration(biomegenerationsettings$builder);
        return biome(false, 2.0F, 0.0F, mobspawnsettings$builder, biomegenerationsettings$builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DESERT));
    }

    public static Biome ocean_oil_field(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        return inland_oil_field(placedFeatures, worldCarvers);
    }


    private static Biome biome(
            boolean hasPercipitation,
            float temperature,
            float downfall,
            MobSpawnSettings.Builder mobSpawnSettings,
            BiomeGenerationSettings.Builder generationSettings,
            @Nullable Music backgroundMusic
    ) {
        return biome(hasPercipitation, temperature, downfall, 4159204, 329011, null, null, mobSpawnSettings, generationSettings, backgroundMusic);
    }

    private static Biome biome(
            boolean hasPrecipitation,
            float temperature,
            float downfall,
            int waterColor,
            int waterFogColor,
            @Nullable Integer grassColorOverride,
            @Nullable Integer foliageColorOverride,
            MobSpawnSettings.Builder mobSpawnSettings,
            BiomeGenerationSettings.Builder generationSettings,
            @Nullable Music backgroundMusic
    ) {
        BiomeSpecialEffects.Builder biomespecialeffects$builder = new BiomeSpecialEffects.Builder()
                .waterColor(waterColor)
                .waterFogColor(waterFogColor)
                .fogColor(12638463)
                .skyColor(calculateSkyColor(temperature))
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .backgroundMusic(backgroundMusic);
        if (grassColorOverride != null) {
            biomespecialeffects$builder.grassColorOverride(grassColorOverride);
        }

        if (foliageColorOverride != null) {
            biomespecialeffects$builder.foliageColorOverride(foliageColorOverride);
        }

        return new Biome.BiomeBuilder()
                .hasPrecipitation(hasPrecipitation)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(biomespecialeffects$builder.build())
                .mobSpawnSettings(mobSpawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }

    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder generationSettings) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
        BiomeDefaultFeatures.addDefaultSprings(generationSettings);
        BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
    }

    protected static int calculateSkyColor(float temperature) {
        float $$1 = temperature / 3.0F;
        $$1 = Mth.clamp($$1, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }
}
