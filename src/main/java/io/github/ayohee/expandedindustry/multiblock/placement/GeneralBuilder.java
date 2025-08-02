package io.github.ayohee.expandedindustry.multiblock.placement;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import static io.github.ayohee.expandedindustry.multiblock.placement.PlacementTest.rotateLayerClockwise;
import static io.github.ayohee.expandedindustry.multiblock.placement.PlacementTest.rotateOriginClockwise;

public class GeneralBuilder {
    protected HashMap<Character, Supplier<BlockState>> mapping = new HashMap<>();
    protected List<List<String>> layers = new LinkedList<>();
    protected Vec3i origin = Vec3i.ZERO;

    int width = -1;
    int length = -1;

    public GeneralBuilder() {
        mapping.put(' ', Blocks.AIR::defaultBlockState);
    }

    public GeneralBuilder addLayer(List<String> layer) {
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

    public GeneralBuilder define(char key, Supplier<BlockState> value) {
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

    public GeneralBuilder setOrigin(Vec3i origin) {
        if (origin == null) {
            throw new Error("Origin was null");
        }
        this.origin = origin;

        return this;
    }

    public boolean place(LevelAccessor level, BlockPos corePos) {
        for (int layerY = 0; layerY < layers.size(); layerY++) {
            for (int layerX = 0; layerX < width; layerX++) {
                for (int layerZ = 0; layerZ < length; layerZ++) {
                    BlockPos pos = corePos.subtract(origin).offset(layerX, layerY, layerZ);
                    char key = layers.get(layerY).get(layerZ).charAt(layerX);
                    Supplier<BlockState> supplier = mapping.get(key);

                    level.setBlock(pos, supplier.get(), Block.UPDATE_ALL);
                }
            }
        }

        return true;
    }

    public GeneralBuilder copy() {
        GeneralBuilder builder = new GeneralBuilder();

        builder.mapping = new HashMap<>(mapping);

        builder.layers = new ArrayList<>(layers);
        int i = 0;
        for (List<String> layer : layers)  {
            builder.layers.set(i++, new ArrayList<>(layer));
        }

        builder.origin = origin;
        builder.length = length;
        builder.width = width;

        return builder;
    }

    public GeneralBuilder clockwiseRotatedCopy() {
        GeneralBuilder builder = new GeneralBuilder();

        builder.mapping = new HashMap<>(mapping);

        builder.layers = new ArrayList<>(layers);
        int i = 0;
        for (List<String> layer : layers)  {
            builder.layers.set(i++, rotateLayerClockwise(layer));
        }

        builder.origin = rotateOriginClockwise(origin, length);
        builder.length = width;
        builder.width = length;

        return builder;
    }
}
