package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.api.equipment.goggles.IHaveHoveringInformation;
import io.github.ayohee.expandedindustry.util.ITickingBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import java.util.List;

public interface IMultiblockComponentBE extends IMultiblockTooltips, ITickingBlockEntity {
    @Nonnull BlockEntity getInstance();

    void setController(AbstractMultiblockControllerBE mbc);
    void findController();
    AbstractMultiblockControllerBE getController();

    default void onDestroy() {
        findController();
        if (getController() == null) {
            return;
        }

        getController().onDestroy();
    }

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
}
