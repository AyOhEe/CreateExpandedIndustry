package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.ayohee.expandedindustry.content.items.PressurisedCanisterItem;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
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

                    output.accept(entry.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
                for (RegistryEntry<Item, Item> entry : REGISTRATE.getAll(Registries.ITEM)) {
                    if (!CreateRegistrate.isInCreativeTab(entry, EICreativeTabs.MAIN_TAB))
                        continue;
                    output.accept(entry.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }

                ItemStack[] filledCanisters = {
                        generateFilledCanister(EIFluids.LIQUID_PETROLEUM_GAS),
                        generateFilledCanister(EIFluids.ETHYLENE),
                        generateFilledCanister(EIFluids.HYDROGEN_GAS)
                };
                for (ItemStack canister : filledCanisters) {
                    output.accept(canister, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            })
            .build());

    private static ItemStack generateFilledCanister(FluidEntry<VirtualFluid> fluidEntry) {
        ItemStack item = EIItems.PRESSURISED_CANISTER.asStack();
        FluidStack fluid = new FluidStack(fluidEntry.get().getSource(), PressurisedCanisterItem.CAPACITY);

        item.applyComponents(DataComponentPatch.builder()
                .set(EIDataComponents.FLUID_STACK.get(), SimpleFluidContent.copyOf(fluid))
                .build()
        );

        return item;
    }

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DECORATIVES_TAB = EIRegistries.CREATIVE_MODE_TABS.register("decoratives_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.createexpandedindustry_decoratives"))
            .withTabsBefore(EICreativeTabs.MAIN_TAB.getKey())
            .icon(() -> EIBlocks.DYED_MICROPLASTIC_BLOCKS.get(DyeColor.LIGHT_BLUE).asStack())
            .displayItems((parameters, output) -> {
                for (RegistryEntry<Block, Block> entry : REGISTRATE.getAll(Registries.BLOCK)) {
                    if (!CreateRegistrate.isInCreativeTab(entry, EICreativeTabs.DECORATIVES_TAB))
                        continue;
                    if (entry.get() instanceof LiquidBlock)
                        continue;

                    output.accept(entry.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
                for (RegistryEntry<Item, Item> entry : REGISTRATE.getAll(Registries.ITEM)) {
                    if (!CreateRegistrate.isInCreativeTab(entry, EICreativeTabs.DECORATIVES_TAB))
                        continue;
                    output.accept(entry.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            })
            .build());

    public static void register() { }
}
