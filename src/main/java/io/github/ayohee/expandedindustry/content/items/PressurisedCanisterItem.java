package io.github.ayohee.expandedindustry.content.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import io.github.ayohee.expandedindustry.register.EIDataComponents;
import io.github.ayohee.expandedindustry.register.EIItems;
import net.createmod.catnip.platform.NeoForgeCatnipServices;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;

import java.util.List;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class PressurisedCanisterItem extends Item {
    public static final int CAPACITY = 8000;

    public PressurisedCanisterItem(Properties properties) {
        super(properties);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (itemstack, ctx) -> new FluidHandlerItemStack(EIDataComponents.FLUID_STACK, itemstack, CAPACITY),
                EIItems.PRESSURISED_CANISTER
        );
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        IFluidHandlerItem capability = stack.getCapability(Capabilities.FluidHandler.ITEM);
        return capability != null;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        IFluidHandlerItem capability = stack.getCapability(Capabilities.FluidHandler.ITEM);

        if (capability == null) {
            return 0;
        } else {
            int amount = capability.getFluidInTank(0).getAmount();
            int capacity = capability.getTankCapacity(0);
            return Math.round(13.0f * amount / capacity);
        }
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xFFFFFF;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        IFluidHandlerItem capability = stack.getCapability(Capabilities.FluidHandler.ITEM);
        if (capability == null) {
            tooltipComponents.add(Component.literal("0mb / 0mb of BUGGED"));
            return;
        }

        int capacity = capability.getTankCapacity(0);
        FluidStack fluid = capability.getFluidInTank(0);
        int amount = fluid.getAmount();

        Component fluidName = fluid.isEmpty() ? Component.literal("Air") : fluid.getHoverName();
        tooltipComponents.add(Component.literal(amount + "mb / " + capacity + "mb of ").append(fluidName));
    }

    public static class ItemExtensions implements IClientItemExtensions {
        private final PressurisedCanisterRenderer renderer = new PressurisedCanisterRenderer();

        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return renderer;
        }
    }

    public static class PressurisedCanisterRenderer extends BlockEntityWithoutLevelRenderer {
        protected static final PartialModel ITEM = PartialModel.of(ResourceLocation.fromNamespaceAndPath(MODID, "item/empty_canister"));


        public PressurisedCanisterRenderer() {
            super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        }

        @Override
        public void renderByItem(ItemStack stack,
                                 ItemDisplayContext transformType,
                                 PoseStack ms,
                                 MultiBufferSource buffer,
                                 int light, 
                                 int overlay) {
            ItemTransform tf = ITEM.get().getTransforms().getTransform(transformType);
            ms.translate(0.5f, 0.5f, 0.5f);
            tf.apply(true, ms);

            // Render base container
            PartialItemModelRenderer renderer = PartialItemModelRenderer.of(stack, transformType, ms, buffer, overlay);
            renderer.render(ITEM.get(), light);

            // Render fluid inside
            IFluidHandlerItem capability = stack.getCapability(Capabilities.FluidHandler.ITEM);
            if ((capability != null) && (capability.getFluidInTank(0) != FluidStack.EMPTY)) {
                FluidStack content = capability.getFluidInTank(0);
                final float filledHeight = 0.1f;
                final float emptyHeight = -0.35f;
                final float currentHeight = emptyHeight + ((filledHeight - emptyHeight) * ((float) content.getAmount() / CAPACITY));

                NeoForgeCatnipServices.FLUID_RENDERER.renderFluidBox(
                        content,
                        -0.2f, emptyHeight, 0,
                        0.2f, currentHeight, 0,
                        buffer, ms, light, false, true
                );
            }
        }
    }
}
