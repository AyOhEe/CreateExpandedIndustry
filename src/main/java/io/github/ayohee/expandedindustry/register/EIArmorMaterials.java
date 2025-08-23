package io.github.ayohee.expandedindustry.register;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;

import java.util.EnumMap;
import java.util.List;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;
import static io.github.ayohee.expandedindustry.register.EIRegistries.ARMOR_MATERIALS;

public class EIArmorMaterials {
    public static final Holder<ArmorMaterial> COBALT_ARMOR_MATERIAL = ARMOR_MATERIALS
            .register("copper", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 3);
            }),
            20,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            () -> Ingredient.of(EIItems.PLASTIC_COMPOSITE),
            List.of(
                    new ArmorMaterial.Layer(
                            ResourceLocation.fromNamespaceAndPath(MODID, "cobalt")
                    )
//                    new ArmorMaterial.Layer(
//                            ResourceLocation.fromNamespaceAndPath(MODID, "cobalt"), "_overlay", false
//                    )
            ),
            2,
            0.05f
    ));

    public static void register() { }
}
