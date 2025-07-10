package io.github.ayohee.expandedindustry.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class HardenedStonePatchFeature extends Feature<HardenedStonePatchConfiguration> {
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
        int y0 = origin.getY();
        int z0 = origin.getZ();

        for (int y = -59; !worldGenLevel.isOutsideBuildHeight(y); y++){
            mutablePos.set(x0, y, z0);
            LevelChunkSection levelChunkSection = bulkSectionAccess.getSection(mutablePos);

            int localX = SectionPos.sectionRelative(x0);
            int localY = SectionPos.sectionRelative(y);
            int localZ = SectionPos.sectionRelative(z0);

            levelChunkSection.setBlockState(localX, localY, localZ, config.hardened_variant, false);
        }

        bulkSectionAccess.close();


        //only return true if the structure placed
        HardenedStonePatchSavedData.addPatch(origin, config.type);
        return true;
    }
}
