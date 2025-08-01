package io.github.ayohee.expandedindustry.multiblock.placement;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class PlacementTest {
    protected HashMap<Character, Predicate<BlockState>> mapping = new HashMap<>();
    protected List<List<String>> layers = new LinkedList<>();
    protected Vec3i origin = Vec3i.ZERO;

    int width = -1;
    int length = -1;

    public PlacementTest() {
        mapping.put(' ', BlockState::canBeReplaced);
    }

    public PlacementTest addLayer(List<String> layer) {
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

    public PlacementTest define(char key, Predicate<BlockState> value) {
        if (Character.isWhitespace(key)) {
            throw new Error("Key was blank");
        }
        if (value == null) {
            throw new Error("BlockState was null");
        }

        mapping.put(key, value);

        return this;
    }

    public PlacementTest setOrigin(Vec3i origin) {
        if (origin == null) {
            throw new Error("Origin was null");
        }
        this.origin = origin;

        return this;
    }

    public boolean canPlace(LevelAccessor level, BlockPos corePos) {
        for (int layerY = 0; layerY < layers.size(); layerY++) {
            for (int layerX = 0; layerX < width; layerX++) {
                for (int layerZ = 0; layerZ < length; layerZ++) {
                    BlockPos pos = corePos.subtract(origin).offset(layerX, layerY, layerZ);
                    char key = layers.get(layerY).get(layerZ).charAt(layerX);
                    Predicate<BlockState> test = mapping.get(key);

                    if (!test.test(level.getBlockState(pos))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public PlacementTest copy() {
        PlacementTest test = new PlacementTest();

        test.mapping = new HashMap<>(mapping);

        test.layers = new ArrayList<>(layers);
        int i = 0;
        for (List<String> layer : layers)  {
            test.layers.set(i++, new ArrayList<>(layer));
        }

        test.origin = origin;
        test.length = length;
        test.width = width;

        return test;
    }

    public static Predicate<BlockState> blockMatches(Supplier<Block> b) {
        return (bs) -> bs.getBlock() == b.get();
    }

    public static Predicate<BlockState> blockStateMatches(Supplier<BlockState> b) {
        return (bs) -> bs == b.get();
    }
}