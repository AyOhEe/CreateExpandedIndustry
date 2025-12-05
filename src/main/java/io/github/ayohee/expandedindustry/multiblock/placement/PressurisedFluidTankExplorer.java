package io.github.ayohee.expandedindustry.multiblock.placement;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class PressurisedFluidTankExplorer {
    /**
     * Uses a rudimentary flood-fill algorithm to determine the bounds of a pressurised fluid tank
     * @param tankPos The position of any block part of this potential tank
     * @return A Pair, containing the coordinates of the lowest north-westernmost (lowest XZ) tank block, and the dimensions of the tank
     */
    public static @Nullable Pair<Vec3i, Vec3i> explorePressurisedTank(LevelAccessor level, BlockPos tankPos) {
        if (!isTank(level, tankPos)) {
            return null;
        }

        HashSet<BlockPos> tankBlocks = new HashSet<>();
        tankBlocks.add(tankPos);

        Queue<BlockPos> blocksToCheck = new LinkedList<>();
        blocksToCheck.add(tankPos);

        while (!blocksToCheck.isEmpty()) {
            BlockPos currentBlockPos = blocksToCheck.remove();
            for (BlockPos neighbour : getNeighbours(currentBlockPos)) {
                if (tankBlocks.contains(neighbour)) {
                    continue;
                }

                if (!isTank(level, neighbour)) {
                    continue;
                }
                if (!ConnectivityHandler.isConnected(level, currentBlockPos, neighbour)) {
                    continue;
                }
                tankBlocks.add(neighbour);

                if (!blocksToCheck.contains(neighbour)) {
                    blocksToCheck.add(neighbour);
                }
            }
        }


        BlockPos lowestNorthWest = tankPos; // Closest to (-inf, -inf, -inf)
        BlockPos highestSouthEast = tankPos; // Closest to (inf, inf, inf)


        //FIXME could be more elegant, but is it even worth it?
        // Stupid, but simple and relatively clean.
        while (tankBlocks.contains(lowestNorthWest)) {
            lowestNorthWest = lowestNorthWest.below();
        }
        // Last iteration we went out of bounds, so head back in. Similar logic for following iterations
        lowestNorthWest = lowestNorthWest.above();

        while (tankBlocks.contains(lowestNorthWest)) {
            lowestNorthWest = lowestNorthWest.north();
        }
        lowestNorthWest = lowestNorthWest.south();
        while (tankBlocks.contains(lowestNorthWest)) {
            lowestNorthWest = lowestNorthWest.west();
        }
        lowestNorthWest = lowestNorthWest.east();


        // And again for the other corner.
        while (tankBlocks.contains(highestSouthEast)) {
            highestSouthEast = highestSouthEast.above();
        }
        highestSouthEast = highestSouthEast.below();
        while (tankBlocks.contains(highestSouthEast)) {
            highestSouthEast = highestSouthEast.south();
        }
        highestSouthEast = highestSouthEast.north();
        while (tankBlocks.contains(highestSouthEast)) {
            highestSouthEast = highestSouthEast.east();
        }
        highestSouthEast = highestSouthEast.west();


        // Increase by 1 to get the side lengths, instead of the difference in coordinates.
        // e.g. if the tank was composed of just one block, dimensions would otherwise be (0, 0, 0)
        return Pair.of(lowestNorthWest, highestSouthEast.subtract(lowestNorthWest).offset(1, 1, 1));
    }

    private static boolean isTank(LevelAccessor level, BlockPos pos) {
        return !level.getBlockEntity(pos, EIBlockEntityTypes.PRESSURISED_FLUID_TANK.get()).isEmpty();
    }

    public static BlockPos[] getNeighbours(BlockPos pos) {
        return new BlockPos[] {
                pos.above(),
                pos.below(),
                pos.north(),
                pos.south(),
                pos.east(),
                pos.west()
        };
    }
}
