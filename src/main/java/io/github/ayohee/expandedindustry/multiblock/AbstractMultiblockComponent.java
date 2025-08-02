package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import io.github.ayohee.expandedindustry.util.TickingBlockEntityTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractMultiblockComponent <T extends BlockEntity & IMultiblockComponentBE> extends Block implements IBE<T>, IWrenchable {
    public AbstractMultiblockComponent(Properties properties) {
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
        ((IMultiblockComponentBE) context.getLevel().getBlockEntity(context.getClickedPos())).onDestroy();
        return InteractionResult.SUCCESS;
    }
}
