package io.github.ayohee.expandedindustry.multiblock.placement;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

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
        //TODO actual logic
        return true;
    }

    public static Predicate<BlockState> blockMatches(Supplier<Block> b) {
        return (bs) -> bs.getBlock() == b.get();
    }

    public static Predicate<BlockState> blockStateMatches(Supplier<BlockState> b) {
        return (bs) -> bs == b.get();
    }
}