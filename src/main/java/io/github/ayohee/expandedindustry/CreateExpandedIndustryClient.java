package io.github.ayohee.expandedindustry;

import io.github.ayohee.expandedindustry.content.complex.pressurisedTank.PressurisedFluidTankRenderer;
import io.github.ayohee.expandedindustry.multiblock.MultiblockKineticIORenderer;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;


@Mod(value = CreateExpandedIndustry.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = CreateExpandedIndustry.MODID, value = Dist.CLIENT)
public class CreateExpandedIndustryClient {
    public CreateExpandedIndustryClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        //FIXME this shouldn't be done this way. No clue why it wasn't working through registrate.
        BlockEntityRenderers.register(EIBlockEntityTypes.PRESSURISED_FLUID_TANK.get(), PressurisedFluidTankRenderer::new);
        BlockEntityRenderers.register(EIBlockEntityTypes.MULTIBLOCK_KINETIC_IO.get(), MultiblockKineticIORenderer::new);
    }
}
