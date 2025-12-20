package io.github.ayohee.expandedindustry.register;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.neoforge.registries.DeferredHolder;

public class EIParticleTypes {
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CONFETTI = EIRegistries.PARTICLE_TYPE.register(
            "confetti",
            () -> new SimpleParticleType(false)
    );

    public static void register() {}
}
