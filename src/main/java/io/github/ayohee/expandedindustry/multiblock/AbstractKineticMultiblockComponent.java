package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractKineticMultiblockComponent<T extends KineticBlockEntity & IMultiblockComponentBE> extends KineticBlock implements IBE<T> {
    public AbstractKineticMultiblockComponent(Properties properties) {
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
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
        ((IMultiblockComponentBE) context.getLevel().getBlockEntity(context.getClickedPos())).onDestroy();
        return InteractionResult.SUCCESS;
    }
}
