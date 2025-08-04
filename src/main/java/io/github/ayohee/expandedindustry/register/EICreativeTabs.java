package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.REGISTRATE;


public class EICreativeTabs {
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = EIRegistries.CREATIVE_MODE_TABS.register("main_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.createexpandedindustry"))
            .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey())
            .icon(EIBlocks.ERYTHRITE_BLOCK::asStack)
            .displayItems((parameters, output) -> {
                for (RegistryEntry<Block, Block> entry : REGISTRATE.getAll(Registries.BLOCK)) {
                    if (!CreateRegistrate.isInCreativeTab(entry, EICreativeTabs.MAIN_TAB))
                        continue;
                    if (entry.get() instanceof LiquidBlock)
                        continue;

                    output.accept(entry.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                }
                for (RegistryEntry<Item, Item> entry : REGISTRATE.getAll(Registries.ITEM)) {
                    if (!CreateRegistrate.isInCreativeTab(entry, EICreativeTabs.MAIN_TAB))
                        continue;
                    output.accept(entry.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                }
            })
            .build());

    public static void register() { }
}
