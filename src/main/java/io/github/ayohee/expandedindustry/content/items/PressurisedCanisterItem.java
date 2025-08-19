package io.github.ayohee.expandedindustry.content.items;

import io.github.ayohee.expandedindustry.register.EIDataComponents;
import io.github.ayohee.expandedindustry.register.EIItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;

import java.util.List;

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
        IFluidHandlerItem capability = stack.getCapability(Capabilities.FluidHandler.ITEM);

        if (capability == null) {
            return 0xFFFFFF;
        }

        Fluid fluid = capability.getFluidInTank(0).getFluid();
        if (fluid == Fluids.EMPTY) {
            return 0xFFFFFF;
        }

        return IClientFluidTypeExtensions.of(fluid).getTintColor();
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
}
