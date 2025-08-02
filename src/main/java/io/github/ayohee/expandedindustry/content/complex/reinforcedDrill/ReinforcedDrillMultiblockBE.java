package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockControllerBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiConsumer;

public class ReinforcedDrillMultiblockBE extends AbstractMultiblockControllerBE {
    public ReinforcedDrillMultiblockBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public BiConsumer<LevelAccessor, BlockPos> deconstructFunction() {
        return ReinforcedDrillMultiblock::deconstructMBS;
    }

    @Override
    public void tick() {
        super.tick();

        //TODO MINING SHIT HERE
    }
}
