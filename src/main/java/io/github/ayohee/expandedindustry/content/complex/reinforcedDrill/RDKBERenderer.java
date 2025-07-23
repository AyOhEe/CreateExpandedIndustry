package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class RDKBERenderer extends KineticBlockEntityRenderer<ReinforcedDrillKineticBlockEntity> {

    public RDKBERenderer(BlockEntityRendererProvider.Context dispatcher) {
        super(dispatcher);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(ReinforcedDrillKineticBlockEntity te, BlockState state) {
        return CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, state);
    }

    @Override
    protected void renderSafe(ReinforcedDrillKineticBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        BlockState state = getRenderedBlockState(be);
        RenderType type = getRenderType(be, state);
        renderRotatingBuffer(be, getRotatedModel(be, state), ms, buffer.getBuffer(type), light);
    }
}
