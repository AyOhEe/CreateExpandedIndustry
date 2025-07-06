package io.github.ayohee.expandedindustry;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.REGISTRATE;

public class EIRegistrateTags {
    public static void addGenerators() {
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, EIRegistrateTags::genBlockTags);
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, EIRegistrateTags::genItemTags);
        REGISTRATE.addDataGenerator(ProviderType.FLUID_TAGS, EIRegistrateTags::genFluidTags);
        REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, EIRegistrateTags::genEntityTags);
    }

    private static void genBlockTags(RegistrateTagsProvider<Block> blockIntrinsic) {
    }

    private static void genItemTags(RegistrateTagsProvider<Item> registrateItemTagsProvider) {
    }

    private static void genFluidTags(RegistrateTagsProvider<Fluid> fluidIntrinsic) {
    }

    private static void genEntityTags(RegistrateTagsProvider<EntityType<?>> entityTypeIntrinsic) {
    }
}
