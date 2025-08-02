package io.github.ayohee.expandedindustry.multiblock.placement;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HorizontalMultiblockBuilder extends HorizontalGeneralBuilder<MultiblockBuilder> {
    public HorizontalMultiblockBuilder(MultiblockBuilder builder, List<Direction> acceptableDirections) {
        super(builder, acceptableDirections);
    }

    public HorizontalMultiblockBuilder(MultiblockBuilder test) {
        this(test, List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST));
    }

    @Override
    public HorizontalMultiblockBuilder reDefine(char key, Map<Direction, Supplier<BlockState>> statePairs) {
        return (HorizontalMultiblockBuilder) super.reDefine(key, statePairs);
    }
}
