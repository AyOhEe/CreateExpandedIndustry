package io.github.ayohee.expandedindustry.content.complex.fractionatingcolumn;

import io.github.ayohee.expandedindustry.content.blocks.WrenchableBlock;
import io.github.ayohee.expandedindustry.content.complex.pressurisedTank.PressurisedFluidTankBlockEntity;
import io.github.ayohee.expandedindustry.multiblock.placement.PressurisedFluidTankExplorer;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;


public class FractionatingColumnBaseBlock extends WrenchableBlock {
    public FractionatingColumnBaseBlock(Properties properties) {
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
        Pair<Vec3i, Vec3i> placementInfo = PressurisedFluidTankExplorer.explorePressurisedTank(level, pos.above());
        if (placementInfo == null
                || !hasColumnBase(level, placementInfo)
                || placementInfo.getSecond().getY() < 3
                || !tankIsEmpty(level, pos.above())) {
            player.displayClientMessage(Component.literal("Incorrect assembly!"), true);
            return InteractionResult.FAIL;
        }

        // Early return - this should only happen on the server, and we know the placement will succeed by now
        // The client should NOT place the multiblock, only *trigger* the placement.
        if (!(context.getLevel() instanceof ServerLevel)) {
            player.displayClientMessage(Component.literal("Successful assembly!"), true);
            return InteractionResult.SUCCESS;
        }

        FractionatingColumnMultiblock.placeMBS(level, placementInfo.getFirst(), placementInfo.getSecond());
        return InteractionResult.SUCCESS;
    }

    private boolean tankIsEmpty(Level level, BlockPos tankPos) {
        Optional<PressurisedFluidTankBlockEntity> optionalTank = level.getBlockEntity(tankPos, EIBlockEntityTypes.PRESSURISED_FLUID_TANK.get());
        if (optionalTank.isEmpty()) {
            return false;
        }

        PressurisedFluidTankBlockEntity controller = optionalTank.get().getControllerBE();
        if (controller == null) {
            return false;
        }

        return controller.getTankInventory().isEmpty();
    }

    private boolean hasColumnBase(Level level, Pair<Vec3i, Vec3i> placementInfo) {
        int x0 = placementInfo.getFirst().getX();
        int y0 = placementInfo.getFirst().getY() - 1; // Column blocks go below the tank
        int z0 = placementInfo.getFirst().getZ();

        int xEnd = x0 + placementInfo.getSecond().getX();
        int zEnd = z0 + placementInfo.getSecond().getZ();

        for (int x = x0; x < xEnd; x++) {
            for (int z = z0; z < zEnd; z++) {
                BlockState bs = level.getBlockState(new BlockPos(x, y0, z));
                if (bs.getBlock() != EIBlocks.FRACTIONATING_COLUMN_BASE.get()) {
                    return false;
                }
            }
        }

        // All blocks passed, so the column must be correctly assembled
        return true;
    }
}
