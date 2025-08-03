package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.api.equipment.goggles.IHaveHoveringInformation;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.List;

public interface IMultiblockTooltips extends IHaveHoveringInformation, IHaveGoggleInformation {
    // Returns how high up in the tooltip this entry should be. The order of entries with equal priority is undefined.
    default int multiblockGoggleTooltipPriority(boolean isPlayerSneaking) { return -1; }
    // Adds the tooltip to the multiblock
    default List<Component> multiblockGoggleTooltip(boolean isPlayerSneaking) { return List.of(); }

    // Returns how high up in the tooltip this entry should be. The order of entries with equal priority is undefined.
    default int multiblockTooltipPriority(boolean isPlayerSneaking) { return -1; }
    // Adds the tooltip to the multiblock
    default List<Component> multiblockTooltip(boolean isPlayerSneaking) { return List.of(); }

    // Returns how high up in the tooltip this entry should be. The order of entries with equal priority is undefined.
    default int multiblockContainedFluidTooltipPriority(boolean isPlayerSneaking) { return -1; }
    // Adds the tooltip to the multiblock
    default List<Component> multiblockContainedFluidTooltip(boolean isPlayerSneaking, IFluidHandler handler) { return List.of(); }
}
