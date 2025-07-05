package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import io.github.ayohee.expandedindustry.CreateExpandedIndustry;
import io.github.ayohee.expandedindustry.ErythriteBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.REGISTRATE;

public class EIBlocks {
    static {
        REGISTRATE.setCreativeTab(EICreativeTabs.MAIN_TAB);
    }

    public static final BlockEntry<ErythriteBlock> ERYTHRITE_BLOCK = REGISTRATE.block("erythrite", ErythriteBlock::new)
            .initialProperties(SharedProperties::stone)
            .item()
            .transform(getItemModel())
            .register();

    public static <I extends BlockItem, P> NonNullFunction<ItemBuilder<I, P>, P> getItemModel() {
        return b -> b.model(EIBlocks::locateItemModel).build();
    }

    public static <I extends BlockItem> ItemModelBuilder locateItemModel(
            DataGenContext<Item, I> ctx,
            RegistrateItemModelProvider prov
            ) {
        String blockName = prov.name(ctx.getEntry()::getBlock);
        return prov.withExistingParent(
                blockName,
                ResourceLocation.fromNamespaceAndPath(CreateExpandedIndustry.MODID, "item/" + blockName)
        );
    }


    public static void register() { }
}
