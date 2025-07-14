package io.github.ayohee.expandedindustry.content.blocks;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.ayohee.expandedindustry.content.blockentities.HardenedStoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class HardenedStoneBlock extends Block implements EntityBlock {
    BlockEntityEntry<HardenedStoneBlockEntity> blockEntityEntry;

    public HardenedStoneBlock(Properties properties, BlockEntityEntry<HardenedStoneBlockEntity> blockEntityEntry) {
        super(properties);
        this.blockEntityEntry = blockEntityEntry;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return blockEntityEntry.create(pos, state);
    }
}
