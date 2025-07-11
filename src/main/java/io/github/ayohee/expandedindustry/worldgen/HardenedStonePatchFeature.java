package io.github.ayohee.expandedindustry.worldgen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class HardenedStonePatchFeature extends Feature<HardenedStonePatchConfiguration> {
    public static int MIN_SEPARATION = 500;

    public enum Types {
        // Value must exactly match identifier!
        ERYTHRITE("ERYTHRITE"),
        OCHRUM("OCHRUM"),
        CRIMSITE("CRIMSITE"),
        ASURINE("ASURINE"),
        VERIDIUM("VERIDIUM");

        public final String serialised_value;

        Types(String v) {
            serialised_value = v;
        }
    }

    public HardenedStonePatchFeature() {
        super(HardenedStonePatchConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<HardenedStonePatchConfiguration> context) {
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        WorldGenLevel worldGenLevel = context.level();
        HardenedStonePatchConfiguration config = context.config();

        MutableBlockPos mutablePos = new MutableBlockPos();
        BulkSectionAccess bulkSectionAccess = new BulkSectionAccess(worldGenLevel);

        int x0 = origin.getX();
        int y0 = origin.getY() + 120;
        int z0 = origin.getZ();

        // Don't generate within 300 blocks of 0,0
        if ((x0*x0 + z0*z0) < 300*300) {
            return false;
        }
        // Don't generate within MIN_SEPARATION of another patch
        Pair<BlockPos, Types> nearest = HardenedStonePatchSavedData.findNearestPatch(origin);
        if (nearest != null && nearest.getFirst().distSqr(origin) < MIN_SEPARATION*MIN_SEPARATION) {
            return false;
        }


        //TODO this is a debug feature to identify patch locations easily.
        //     this should be replaced with /cei locate patch [OPTIONAL: type] [OPTIONAL: radius]
        generatePillar(x0, z0, worldGenLevel, bulkSectionAccess, config.hardened_variant);


        double min = 3;
        double max = 16;
        double reachesMaxAt = 25000;
        int radius = calculateRadius(x0, z0, min, max, reachesMaxAt);
        int height = radius;

        for (int x = x0 - radius; x <= x0 + radius; x++) {
            for (int y = y0 - height; y <= y0; y++) {
                for (int z = z0 - radius; z <= z0 + radius; z++) {
                    mutablePos.set(x, y, z);
                    LevelChunkSection levelChunkSection = bulkSectionAccess.getSection(mutablePos);

                    int localX = SectionPos.sectionRelative(x);
                    int localY = SectionPos.sectionRelative(y);
                    int localZ = SectionPos.sectionRelative(z);

                    levelChunkSection.setBlockState(localX, localY, localZ, config.hardened_variant, false);
                }
            }
        }


        bulkSectionAccess.close();

        //only return true if the structure placed
        HardenedStonePatchSavedData.addPatch(origin, config.type);
        return true;
    }

    //https://www.desmos.com/calculator/2ijztdufdp
    private int calculateRadius(int x0, int z0, double min, double max, double reachesMaxAt) {
        double originDistance = Math.sqrt(x0*x0 + z0*z0); // XZ plane distance - top down
        double alpha = Math.log(max - min + 1) / reachesMaxAt; // Make sure that it is equal to min at originDistance == 0, based on the other parameters
        return (int)Math.max(min, Math.ceil(max - Math.exp(alpha * (reachesMaxAt - originDistance))));
    }

    public void generatePillar(int x, int z, WorldGenLevel worldGenLevel, BulkSectionAccess bulkSectionAccess, BlockState placed) {
        MutableBlockPos mutablePos = new MutableBlockPos();
        for (int y = -59; y < 80; y++){
            mutablePos.set(x, y, z);
            LevelChunkSection levelChunkSection = bulkSectionAccess.getSection(mutablePos);

            int localX = SectionPos.sectionRelative(x);
            int localY = SectionPos.sectionRelative(y);
            int localZ = SectionPos.sectionRelative(z);

            levelChunkSection.setBlockState(localX, localY, localZ, placed, false);
        }
    }
}
