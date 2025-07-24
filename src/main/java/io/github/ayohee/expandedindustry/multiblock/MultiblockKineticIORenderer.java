package io.github.ayohee.expandedindustry.multiblock;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class MultiblockKineticIORenderer extends KineticBlockEntityRenderer<MultiblockKineticIOBE> {

    public MultiblockKineticIORenderer(BlockEntityRendererProvider.Context dispatcher) {
        super(dispatcher);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(MultiblockKineticIOBE te, BlockState state) {
        return CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, state);
    }

    @Override
    protected void renderSafe(MultiblockKineticIOBE be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        BlockState state = getRenderedBlockState(be);
        RenderType type = getRenderType(be, state);
        renderRotatingBuffer(be, getRotatedModel(be, state), ms, buffer.getBuffer(type), light);
    }
}
