package io.github.ayohee.expandedindustry.multiblock.placement;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class HorizontalPlacementSet {
    Map<Direction, PlacementTest> tests = new HashMap<>();

    public HorizontalPlacementSet(PlacementTest test, List<Direction> acceptableDirections) {
        // TODO rotate
        tests.put(Direction.NORTH, test.copy());
        tests.put(Direction.EAST, test.copy());
        tests.put(Direction.SOUTH, test.copy());
        tests.put(Direction.WEST, test.copy());
    }

    public HorizontalPlacementSet(PlacementTest test) {
        this(test, List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST));
    }

    public HorizontalPlacementSet reDefine(char key, Map<Direction, Predicate<BlockState>> statePairs) {
        tests.replaceAll((k, v) -> v.define(key, statePairs.get(k)));

        return this;
    }


    public List<Direction> potentialPlacements(LevelAccessor level, BlockPos corePos) {
        LinkedList<Direction> validPlacements = new LinkedList<>();
        for (Map.Entry<Direction, PlacementTest> testPair : tests.entrySet()) {
            if (testPair.getValue().canPlace(level, corePos)) {
                validPlacements.add(testPair.getKey());
            }
        }

        return validPlacements;
    }


    // Returns null if the structure cannot be placed in this spot. Otherwise, returns the first valid placement direction
    public Direction findFirstPlacement(LevelAccessor level, BlockPos corePos) {
        List<Direction> placements = potentialPlacements(level, corePos);
        if (placements.isEmpty()) {
            return null;
        }
        return placements.getFirst();
    }
}
