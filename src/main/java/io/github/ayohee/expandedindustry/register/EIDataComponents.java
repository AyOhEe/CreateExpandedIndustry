package io.github.ayohee.expandedindustry.register;

import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;

import static io.github.ayohee.expandedindustry.register.EIRegistries.DATA_COMPONENTS;

public class EIDataComponents {
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> FLUID_STACK =  DATA_COMPONENTS.registerComponentType(
            "simple_fluid",
            builder -> builder.persistent(SimpleFluidContent.CODEC)
    );

    public static void register() { }
}
