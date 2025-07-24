package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class MultiblockKineticIOVisual extends KineticBlockEntityVisual<MultiblockKineticIOBE> {
    private RotatingInstance shaft;

    public MultiblockKineticIOVisual(VisualizationContext context, MultiblockKineticIOBE blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        float speed = blockEntity.getSpeed();

        shaft = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT_HALF))
                .createInstance();

        shaft.setup(blockEntity, speed)
                .setPosition(getVisualPosition())
                .rotateToFace(Direction.SOUTH, getDir())
                .setChanged();
    }

    private Direction getDir() {
        return blockState.getValue(BlockStateProperties.FACING);
    }

    @Override
    public void update(float partialTick) {
        shaft.setup(blockEntity, blockEntity.getSpeed()).setChanged();
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        consumer.accept(shaft);
    }

    @Override
    public void updateLight(float partialTick) {
        relight(shaft);
    }

    @Override
    protected void _delete() {
        shaft.delete();
    }
}
