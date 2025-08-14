package io.github.ayohee.expandedindustry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import io.github.ayohee.expandedindustry.content.complex.pressurisedTank.PressurisedFluidTankBlockEntity;
import io.github.ayohee.expandedindustry.multiblock.MultiblockFluidInputBE;
import io.github.ayohee.expandedindustry.multiblock.MultiblockFluidTankBE;
import io.github.ayohee.expandedindustry.multiblock.MultiblockInventoryBE;
import io.github.ayohee.expandedindustry.register.*;
import net.createmod.catnip.lang.FontHelper;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CreateExpandedIndustry.MODID)
public class CreateExpandedIndustry {
    public static final String MODID = "createexpandedindustry";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );
    public static final Logger LOGGER = LogUtils.getLogger();


    public CreateExpandedIndustry(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(EventPriority.HIGHEST, EIDatagen::gatherDataHighPriority);
        modEventBus.addListener(EventPriority.LOWEST, EIDatagen::gatherData);


        REGISTRATE.registerEventListeners(modEventBus);


        NeoForge.EVENT_BUS.register(this);

        EIBlockEntityTypes.register();
        EIBlocks.register();
        EIFluids.register();
        EIStructures.register();
        EIItems.register();
        EIConfig.register(modContainer);
        EICreativeTabs.register();
        EIRegistries.register(modEventBus);
        EISoundEvents.register();


    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }


    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        PressurisedFluidTankBlockEntity.registerCapabilities(event);
        MultiblockInventoryBE.registerCapabilities(event);
        MultiblockFluidTankBE.registerCapabilities(event);
        MultiblockFluidInputBE.registerCapabilities(event);
    }
}
