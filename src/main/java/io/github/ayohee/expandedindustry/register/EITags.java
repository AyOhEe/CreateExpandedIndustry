package io.github.ayohee.expandedindustry.register;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.simibubi.create.AllTags.commonItemTag;
import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EITags {
    public static TagKey<Item> COBALT_INGOT = commonItemTag("ingots/cobalt");
    public static TagKey<Item> COBALT_NUGGET = commonItemTag("nuggets/cobalt");
    public static TagKey<Item> COBALT_SHEET = commonItemTag("plates/cobalt");
    public static TagKey<Item> MICROPLASTIC_BLOCK = modItemTag("microplastic_block");
    public static TagKey<Item> ASPHALT_BLOCK = modItemTag("asphalt_block");

    public static TagKey<Item> modItemTag(String tag) {
        return TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath(MODID, tag));
    }
}
