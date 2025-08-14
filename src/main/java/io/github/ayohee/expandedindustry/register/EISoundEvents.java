package io.github.ayohee.expandedindustry.register;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;
import static io.github.ayohee.expandedindustry.register.EIRegistries.SOUNDS;

public class EISoundEvents {
    public static final DeferredHolder<SoundEvent, SoundEvent> VANILLA_ARIA_MATH = SOUNDS.register(
            "aria_math", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MODID, "aria_math"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> VALSE = SOUNDS.register(
            "valse", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MODID, "valse"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> CLAIRE_DE_LUNE = SOUNDS.register(
            "claire_de_lune", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MODID, "claire_de_lune"))
    );

    public static void register() {}
}
