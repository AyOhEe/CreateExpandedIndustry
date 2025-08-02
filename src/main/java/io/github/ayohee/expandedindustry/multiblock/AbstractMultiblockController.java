package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import io.github.ayohee.expandedindustry.util.TickingBlockEntityTicker;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractMultiblockController<T extends AbstractMultiblockControllerBE> extends Block implements IBE<T>, IWrenchable {
    public AbstractMultiblockController(Properties properties) {
        super(properties);
    }

    @Override
    public <S extends BlockEntity> BlockEntityTicker<S> getTicker(Level level, BlockState blockState, BlockEntityType<S> blockEntityType) {
        return new TickingBlockEntityTicker<>();
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
        ((AbstractMultiblockControllerBE) context.getLevel().getBlockEntity(context.getClickedPos())).onDestroy();
        return InteractionResult.SUCCESS;
    }
}
