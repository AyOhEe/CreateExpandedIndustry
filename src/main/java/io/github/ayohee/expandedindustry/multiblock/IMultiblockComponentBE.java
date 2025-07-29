package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.api.equipment.goggles.IHaveHoveringInformation;
import io.github.ayohee.expandedindustry.util.ITickingBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import java.util.List;

public interface IMultiblockComponentBE extends IHaveHoveringInformation, IHaveGoggleInformation, ITickingBlockEntity {
    @Nonnull BlockEntity getInstance();

    void setController(MultiblockControllerBE mbc);
    void findController();
    MultiblockControllerBE getController();

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

    @Override
    default boolean addToTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        findController();
        if (getController() == null) {
            return false;
        }
        return getController().addToTooltip(tooltip, isPlayerSneaking);
    }

    @Override
    default boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        findController();
        if (getController() == null) {
            return false;
        }
        return getController().addToGoggleTooltip(tooltip, isPlayerSneaking);
    }

    @Override
    default boolean containedFluidTooltip(List<Component> tooltip, boolean isPlayerSneaking,
                                          IFluidHandler handler) {
        findController();
        if (getController() == null) {
            return false;
        }
        return getController().containedFluidTooltip(tooltip, isPlayerSneaking, handler);
    }

    default void onDestroy() {
        findController();
        if (getController() == null) {
            return;
        }
        if (!getInstance().hasLevel() || getInstance().getLevel().isClientSide) {
            return;
        }

        getController().onDestroy();
    }
}
