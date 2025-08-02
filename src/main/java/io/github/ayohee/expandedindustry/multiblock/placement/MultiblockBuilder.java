package io.github.ayohee.expandedindustry.multiblock.placement;

import io.github.ayohee.expandedindustry.multiblock.*;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.function.Supplier;

import static io.github.ayohee.expandedindustry.multiblock.placement.PlacementTest.rotateLayerClockwise;
import static io.github.ayohee.expandedindustry.multiblock.placement.PlacementTest.rotateOriginClockwise;

public class MultiblockBuilder extends GeneralBuilder {
    float stressImpact = 0;
    float minRPM = 0;

    public MultiblockBuilder() {
        super();
        mapping = new HashMap<>();
    }

    public MultiblockBuilder kineticStats(float stressImpact, float minRPM) {
        if (stressImpact < 0 || minRPM < 0) {
            throw new Error("Both stressImpact and minRPM must be greater than or equal to 0");
        }

        this.stressImpact = stressImpact;
        this.minRPM = minRPM;

        return this;
    }

    @Override
    public boolean place(LevelAccessor level, BlockPos corePos) {
        LinkedList<Pair<BlockPos, BlockState>> placementQueue = new LinkedList<>();
        LinkedList<Pair<BlockPos, BlockState>> kioQueue = new LinkedList<>();

        AbstractMultiblockControllerBE controller = null;
        for (int layerY = 0; layerY < layers.size(); layerY++) {
            for (int layerX = 0; layerX < width; layerX++) {
                for (int layerZ = 0; layerZ < length; layerZ++) {
                    BlockPos pos = corePos.subtract(origin).offset(layerX, layerY, layerZ);
                    char key = layers.get(layerY).get(layerZ).charAt(layerX);
                    Supplier<BlockState> supplier = mapping.get(key);

                    if (pos.equals(corePos)) {
                        if (controller != null) {
                            throw new Error("Attempted to create more than one controller");
                        }
                        level.setBlock(pos, supplier.get(), Block.UPDATE_ALL);
                        controller = (AbstractMultiblockControllerBE) level.getBlockEntity(pos);
                    } else if (supplier.get().getBlock() instanceof AbstractKineticMultiblockComponent<?>) {
                        kioQueue.addFirst(Pair.of(pos, supplier.get()));
                    } else {
                        placementQueue.add(Pair.of(pos, supplier.get()));
                    }
                }
            }
        }

        if (controller == null) {
            return false;
        }


        for (Pair<BlockPos, BlockState> p : placementQueue) {
            level.setBlock(p.getFirst(), p.getSecond(), Block.UPDATE_ALL);
            IMultiblockComponentBE be = ((IMultiblockComponentBE) level.getBlockEntity(p.getFirst()));
            controller.addComponent(be);
        }
        placeShaftPorts(kioQueue, level, controller, stressImpact, minRPM);

        return true;
    }

    @Override
    public MultiblockBuilder addLayer(List<String> layer) {
        return (MultiblockBuilder) super.addLayer(layer);
    }

    @Override
    public MultiblockBuilder define(char key, Supplier<BlockState> value) {
        return (MultiblockBuilder) super.define(key, value);
    }

    @Override
    public MultiblockBuilder setOrigin(Vec3i origin) {
        return (MultiblockBuilder) super.setOrigin(origin);
    }

    @Override
    public MultiblockBuilder copy() {
        MultiblockBuilder builder = new MultiblockBuilder();

        builder.mapping = new HashMap<>(mapping);

        builder.layers = new ArrayList<>(layers);
        int i = 0;
        for (List<String> layer : layers)  {
            builder.layers.set(i++, new ArrayList<>(layer));
        }

        builder.origin = origin;
        builder.length = length;
        builder.width = width;
        builder.stressImpact = stressImpact;
        builder.minRPM = minRPM;

        return builder;
    }

    @Override
    public MultiblockBuilder clockwiseRotatedCopy() {
        MultiblockBuilder builder = new MultiblockBuilder();

        builder.mapping = new HashMap<>(mapping);

        builder.layers = new ArrayList<>(layers);
        int i = 0;
        for (List<String> layer : layers)  {
            builder.layers.set(i++, rotateLayerClockwise(layer));
        }

        builder.origin = rotateOriginClockwise(origin, length);
        builder.length = width;
        builder.width = length;
        builder.stressImpact = stressImpact;
        builder.minRPM = minRPM;

        return builder;
    }

    public static void placeShaftPorts(List<Pair<BlockPos, BlockState>> states,
                                       LevelAccessor level,
                                       AbstractMultiblockControllerBE controller,
                                       float consumedStress,
                                       float minRPM) {
        List<MultiblockKineticIOBE> blockEntities = new LinkedList<>();
        for (Pair<BlockPos, BlockState> statePair : states) {
            BlockPos pos = statePair.getFirst();
            BlockState state = statePair.getSecond();

            level.setBlock(pos, state, Block.UPDATE_ALL);
            MultiblockKineticIOBE be = level.getBlockEntity(pos, EIBlockEntityTypes.MULTIBLOCK_KINETIC_IO.get()).orElseThrow();

            controller.addComponent(be);
            blockEntities.add(be);
        }

        blockEntities.getFirst().setConfiguredStressImpact(consumedStress);
        blockEntities.getFirst().setMinimumRotationSpeed(minRPM);

        // We're working on i and i + 1, so ignore the last element such that it isn't suddenly out of range
        for (int i = 0; i < blockEntities.size() - 1; i++) {
            blockEntities.get(i).poolWith(blockEntities.get(i + 1));
            blockEntities.get(i + 1).poolWith(blockEntities.get(i));
        }
    }
}
