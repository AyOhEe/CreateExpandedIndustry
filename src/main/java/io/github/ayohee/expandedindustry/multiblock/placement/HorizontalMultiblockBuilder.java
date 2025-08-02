package io.github.ayohee.expandedindustry.multiblock.placement;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HorizontalMultiblockBuilder {
    Map<Direction, MultiblockBuilder> builders = new HashMap<>();

    public HorizontalMultiblockBuilder(MultiblockBuilder builder, List<Direction> acceptableDirections) {
        MultiblockBuilder north = builder.copy();
        MultiblockBuilder east = north.clockwiseRotatedCopy();
        MultiblockBuilder south = east.clockwiseRotatedCopy();
        MultiblockBuilder west = south.clockwiseRotatedCopy();

        builders.put(Direction.NORTH, north);
        builders.put(Direction.EAST,  east);
        builders.put(Direction.SOUTH, south);
        builders.put(Direction.WEST,  west);
    }

    public HorizontalMultiblockBuilder(MultiblockBuilder test) {
        this(test, List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST));
    }

    public HorizontalMultiblockBuilder reDefine(char key, Map<Direction, Supplier<BlockState>> statePairs) {
        builders.replaceAll((k, v) -> v.define(key, statePairs.get(k)));

        return this;
    }


    public boolean hasPlacementFor(Direction dir) {
        return builders.containsKey(dir);
    }

    public boolean place(LevelAccessor level, BlockPos corePos, Direction dir) {
        if (!hasPlacementFor(dir)) {
            return false;
        }

        return builders.get(dir).place(level, corePos);
    }
}
