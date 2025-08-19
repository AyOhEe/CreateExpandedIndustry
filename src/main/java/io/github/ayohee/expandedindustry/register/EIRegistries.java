package io.github.ayohee.expandedindustry.register;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;
import static net.minecraft.core.registries.Registries.*;

public class EIRegistries {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.DataComponents.createDataComponents(DATA_COMPONENT_TYPE, MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(SOUND_EVENT, MODID);
    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(CREATIVE_MODE_TAB, MODID);

    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
        SOUNDS.register(modEventBus);
        STRUCTURES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
