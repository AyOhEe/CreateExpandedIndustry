package io.github.ayohee.expandedindustry.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

// Basically lifted straight from MultiblockGhost, but separate to allow for easier subclassing
public abstract class MultiblockModelBlock<T extends MultiblockModelBE> extends AbstractMultiblockComponent<T> {
    public MultiblockModelBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected float getShadeBrightness(BlockState p_308911_, BlockGetter p_308952_, BlockPos p_308918_) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }
}