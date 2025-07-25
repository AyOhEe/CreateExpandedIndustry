package io.github.ayohee.expandedindustry.content.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.ayohee.expandedindustry.CreateExpandedIndustry;
import io.github.ayohee.expandedindustry.register.EIStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.Optional;

public class HardenedStonePatchStructures extends Structure {
    public static final MapCodec<HardenedStonePatchStructures> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(HardenedStonePatchStructures.settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
                    DimensionPadding.CODEC.optionalFieldOf("dimension_padding", JigsawStructure.DEFAULT_DIMENSION_PADDING).forGetter(structure -> structure.dimensionPadding),
                    LiquidSettings.CODEC.optionalFieldOf("liquid_settings", JigsawStructure.DEFAULT_LIQUID_SETTINGS).forGetter(structure -> structure.liquidSettings),
                    Codec.INT.fieldOf("min_origin_distance").forGetter(structure -> structure.minOriginDistance),
                    Codec.INT.fieldOf("max_origin_distance").forGetter(structure -> structure.maxOriginDistance)
            ).apply(instance, HardenedStonePatchStructures::new));

    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final int maxDistanceFromCenter;
    private final DimensionPadding dimensionPadding;
    private final LiquidSettings liquidSettings;
    private final int minOriginDistance;
    private final int maxOriginDistance;

    public HardenedStonePatchStructures(Structure.StructureSettings config,
                                        Holder<StructureTemplatePool> startPool,
                                        Optional<ResourceLocation> startJigsawName,
                                        int size,
                                        HeightProvider startHeight,
                                        int maxDistanceFromCenter,
                                        DimensionPadding dimensionPadding,
                                        LiquidSettings liquidSettings,
                                        int minOriginDistance,
                                        int maxOriginDistance)
    {
        super(config);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.startHeight = startHeight;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.dimensionPadding = dimensionPadding;
        this.liquidSettings = liquidSettings;
        this.minOriginDistance = minOriginDistance;
        this.maxOriginDistance = maxOriginDistance;
    }

    private boolean extraSpawningChecks(Structure.GenerationContext context) {
        ChunkPos chunkpos = context.chunkPos();

        int x = chunkpos.getMiddleBlockX();
        int z = chunkpos.getMiddleBlockZ();
        double originDistanceSqr = (x*x) + (z*z);
        if (minOriginDistance != -1 && originDistanceSqr < minOriginDistance*minOriginDistance){
            return false;
        }
        if (maxOriginDistance != -1 && originDistanceSqr > maxOriginDistance*maxOriginDistance){
            return false;
        }

        return true;
    }

    @Override
    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        if (!extraSpawningChecks(context)) {
            return Optional.empty();
        }

        // Set's our spawning blockpos's y offset.
        int startY = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));

        // Turns the chunk coordinates into actual coordinates we can use. (Gets corner of that chunk)
        ChunkPos chunkPos = context.chunkPos();
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), startY, chunkPos.getMinBlockZ());

        Optional<Structure.GenerationStub> structurePiecesGenerator =
                JigsawPlacement.addPieces(
                        context,
                        this.startPool,
                        this.startJigsawName,
                        this.size,
                        blockPos,
                        false,
                        Optional.empty(),
                        this.maxDistanceFromCenter,
                        PoolAliasLookup.EMPTY,
                        this.dimensionPadding,
                        this.liquidSettings);

        return structurePiecesGenerator;
    }

    @Override
    public StructureType<?> type() {
        return EIStructures.ORE_PATCHES.get();
    }
}