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
}
