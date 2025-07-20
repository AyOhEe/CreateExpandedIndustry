package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import com.simibubi.create.AllShapes;
import io.github.ayohee.expandedindustry.content.blocks.WrenchableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DrillBitBlock extends WrenchableBlock {
    public static final VoxelShape SHAPE = new AllShapes.Builder(box(0, 8, 0, 16, 16, 16))
            .add(3, 4, 3, 13, 8, 13)
            .add(6, 0, 6, 10, 4, 10)
            .build();

    public DrillBitBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
