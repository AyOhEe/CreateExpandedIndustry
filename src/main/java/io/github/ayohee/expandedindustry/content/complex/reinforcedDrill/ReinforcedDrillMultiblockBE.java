package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import com.simibubi.create.foundation.utility.CreateLang;
import io.github.ayohee.expandedindustry.multiblock.MultiblockControllerBE;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

import static net.minecraft.ChatFormatting.GRAY;

public class ReinforcedDrillMultiblockBE extends MultiblockControllerBE {
    public ReinforcedDrillMultiblockBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        CreateLang.translate("gui.goggles.kinetic_stats")
                .forGoggles(tooltip);

        CreateLang.translate("tooltip.stressImpact")
            .style(GRAY)
            .forGoggles(tooltip);

        float stressTotal = 4 * 16;

        CreateLang.number(stressTotal)
                .translate("generic.unit.stress")
                .style(ChatFormatting.AQUA)
                .space()
                .add(CreateLang.translate("gui.goggles.at_current_speed")
                        .style(ChatFormatting.DARK_GRAY))
                .forGoggles(tooltip, 1);


        return true;
    }
}
