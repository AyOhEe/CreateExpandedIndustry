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

public class MultiblockBuilder {
    protected HashMap<Character, Supplier<BlockState>> mapping = new HashMap<>();
    protected List<List<String>> layers = new LinkedList<>();
    protected Vec3i origin = Vec3i.ZERO;

    int width = -1;
    int length = -1;

    float stressImpact = 0;
    float minRPM = 0;

    public MultiblockBuilder() { }

    public MultiblockBuilder addLayer(List<String> layer) {
        if (layer == null || layer.isEmpty()) {
            throw new Error("Empty layer");
        }

        if (length == -1) {
            length = layer.size();
        }
        if (layer.getFirst() != null && width == -1) {
            width = layer.getFirst().length();
        }


        if (layer.size() != length) {
            throw new Error("Layer had length " + layer.size() + ". Expected" + length);
        }
        for (String row : layer) {
            if (row == null) {
                throw new Error("Layer contained a null value");
            }
            if (row.length() != width) {
                throw new Error("Row had length " + row.length() + ". Expected " + width);
            }
        }

        layers.add(layer);

        return this;
    }

    public MultiblockBuilder define(char key, Supplier<BlockState> value) {
        if (Character.isWhitespace(key)) {
            throw new Error("Key was blank");
        }
        if (value == null) {
            throw new Error("BlockState function was null");
        }

        // We can't enforce Block types here, as the registries aren't complete. A validation step could be done
        // elsewhere but that seems tedious and relatively unnecessary - it'll all complain during placement anyways.

        mapping.put(key, value);

        return this;
    }

    public MultiblockBuilder setOrigin(Vec3i origin) {
        if (origin == null) {
            throw new Error("Origin was null");
        }
        this.origin = origin;

        return this;
    }

    public MultiblockBuilder kineticStats(float stressImpact, float minRPM) {
        if (stressImpact < 0 || minRPM < 0) {
            throw new Error("Both stressImpact and minRPM must be greater than or equal to 0");
        }

        this.stressImpact = stressImpact;
        this.minRPM = minRPM;

        return this;
    }

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

    public MultiblockBuilder copy() {
        MultiblockBuilder test = new MultiblockBuilder();

        test.mapping = new HashMap<>(mapping);

        test.layers = new ArrayList<>(layers);
        int i = 0;
        for (List<String> layer : layers)  {
            test.layers.set(i++, new ArrayList<>(layer));
        }

        test.origin = origin;
        test.length = length;
        test.width = width;
        test.stressImpact = stressImpact;
        test.minRPM = minRPM;

        return test;
    }

    public MultiblockBuilder clockwiseRotatedCopy() {
        MultiblockBuilder test = new MultiblockBuilder();

        test.mapping = new HashMap<>(mapping);

        test.layers = new ArrayList<>(layers);
        int i = 0;
        for (List<String> layer : layers)  {
            test.layers.set(i++, rotateLayerClockwise(layer));
        }

        test.origin = rotateOriginClockwise(origin, length);
        test.length = width;
        test.width = length;
        test.stressImpact = stressImpact;
        test.minRPM = minRPM;

        return test;
    }

    private static Vec3i rotateOriginClockwise(Vec3i origin, int oldLength) {
        // A clockwise rotation is really just a transposition and a mirroring. Need to make sure
        // that the new X coordinate is in range, as Z=0 could an X exactly equal to (and thus outside) the length
        return new Vec3i(oldLength - origin.getZ() - 1, origin.getY(), origin.getX());
    }

    private static List<String> rotateLayerClockwise(List<String> layer) {
        LinkedList<String> rotatedLayer = new LinkedList<>();

        int length = layer.size();
        int width = layer.getFirst().length();
        for (int i = 0; i < width; i++) {
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < length; j++) {
                sb.append(layer.get(length - j - 1).charAt(i));
            }

            rotatedLayer.add(sb.toString());
        }

        return rotatedLayer;
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
