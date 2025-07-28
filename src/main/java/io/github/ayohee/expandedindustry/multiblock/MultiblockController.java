package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.foundation.block.IBE;
import io.github.ayohee.expandedindustry.util.TickingBlockEntityTicker;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MultiblockController <T extends MultiblockControllerBE> extends Block implements IBE<T> {
    public MultiblockController(Properties properties) {
        super(properties);
    }

    @Override
    public <S extends BlockEntity> BlockEntityTicker<S> getTicker(Level level, BlockState blockState, BlockEntityType<S> blockEntityType) {
        return new TickingBlockEntityTicker<>();
    }
}
