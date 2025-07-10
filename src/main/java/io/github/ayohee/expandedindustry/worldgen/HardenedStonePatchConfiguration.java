package io.github.ayohee.expandedindustry.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class HardenedStonePatchConfiguration implements FeatureConfiguration {
    public static final Codec<HardenedStonePatchConfiguration> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                BlockState.CODEC
                    .fieldOf("hardened_variant")
                    .forGetter(config -> config.hardened_variant),
                BlockState.CODEC
                    .fieldOf("normal_variant")
                    .forGetter(config -> config.normal_variant),
                Codec.STRING
                    .fieldOf("type")
                    .forGetter(config -> config.type.serialised_value)
    ).apply(instance, HardenedStonePatchConfiguration::new));

    public final BlockState hardened_variant;
    public final BlockState normal_variant;
    public final HardenedStonePatchFeature.Types type;

    public HardenedStonePatchConfiguration(BlockState hardened, BlockState normal, String type) {
        this.hardened_variant = hardened;
        this.normal_variant = normal;
        this.type = HardenedStonePatchFeature.Types.valueOf(type);
    }
}
