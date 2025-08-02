package io.github.ayohee.expandedindustry.multiblock.placement;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HorizontalGeneralBuilder<T extends GeneralBuilder> {
    Map<Direction, T> builders = new HashMap<>();

    public HorizontalGeneralBuilder(T builder, List<Direction> acceptableDirections) {
        T north = (T) builder.copy();
        T east = (T) north.clockwiseRotatedCopy();
        T south = (T) east.clockwiseRotatedCopy();
        T west = (T) south.clockwiseRotatedCopy();

        builders.put(Direction.NORTH, north);
        builders.put(Direction.EAST,  east);
        builders.put(Direction.SOUTH, south);
        builders.put(Direction.WEST,  west);
    }

    public HorizontalGeneralBuilder(T test) {
        this(test, List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST));
    }

    public HorizontalGeneralBuilder<T> reDefine(char key, Map<Direction, Supplier<BlockState>> statePairs) {
        builders.replaceAll((k, v) -> (T) v.define(key, statePairs.get(k)));

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
