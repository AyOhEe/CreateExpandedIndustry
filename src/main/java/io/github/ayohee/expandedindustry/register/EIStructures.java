package io.github.ayohee.expandedindustry.register;


import com.mojang.serialization.MapCodec;
import io.github.ayohee.expandedindustry.content.structures.HardenedStonePatchStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;

public class EIStructures {
    public static final DeferredHolder<StructureType<?>, StructureType<HardenedStonePatchStructures>> ORE_PATCHES
            = EIRegistries.STRUCTURES.register("hardened_stone_patches", () -> explicitStructureTypeTyping(HardenedStonePatchStructures.CODEC));

    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(MapCodec<T> structureCodec) {
        return () -> structureCodec;
    }

    public static void register() {}
}