package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import io.github.ayohee.expandedindustry.content.blocks.WrenchableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;


public class DrillMotorBlock extends WrenchableBlock {
    public DrillMotorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        // Must be performed by a player
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }

        // Ensure that all blocks are present, and in the correct blockstate
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (ReinforcedDrillMultiblock.PLACEMENT_SET.findFirstPlacement(level, pos) == null) {
            player.displayClientMessage(Component.literal("Incorrect assembly!"), true);
            return InteractionResult.FAIL;
        }

        // Early return - this should only happen on the server, and we know the placement will succeed by now
        // The client should NOT place the multiblock, only *trigger* the placement.
        if (!(context.getLevel() instanceof ServerLevel)) {
            player.displayClientMessage(Component.literal("Successful assembly!"), true);
            return InteractionResult.SUCCESS;
        }

        ReinforcedDrillMultiblock.placeMBS(level, pos);
        return InteractionResult.SUCCESS;
    }
}
