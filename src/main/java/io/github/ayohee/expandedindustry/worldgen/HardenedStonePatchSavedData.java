package io.github.ayohee.expandedindustry.worldgen;

import com.mojang.datafixers.util.Pair;
import io.github.ayohee.expandedindustry.CreateExpandedIndustry;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.event.level.LevelEvent;

import java.util.LinkedList;
import java.util.List;

public class HardenedStonePatchSavedData extends SavedData {
    private static HardenedStonePatchSavedData overworldInstance = null;
    //TODO this scales awfully for large worlds. Consider a hashmap of quadrants.
    private List<Pair<BlockPos, HardenedStonePatchFeature.Types>> patchLocations = new LinkedList<>(); // We're only ever iterating or adding to the end - reasonable DS AFAIK

    public HardenedStonePatchSavedData() {}

    // Load existing instance of saved data
    public static HardenedStonePatchSavedData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        HardenedStonePatchSavedData data = HardenedStonePatchSavedData.create();
        NBTHelper.iterateCompoundList(tag.getList("patch_locations", CompoundTag.TAG_COMPOUND), c -> {
            data.patchLocations.add(new Pair<>(
                    readBlockPos(c),
                    HardenedStonePatchFeature.Types.valueOf(c.getString("type")))
            );
        });


        CreateExpandedIndustry.LOGGER.debug("Loading saved patch data. Count: " + data.patchLocations.size());
        overworldInstance = data;
        return data;
    }

    private static BlockPos readBlockPos(CompoundTag c) {
        return new BlockPos(
                c.getInt("x"),
                c.getInt("y"),
                c.getInt("z")
        );
    }

    public static void addPatch(BlockPos origin, HardenedStonePatchFeature.Types type) {
        overworldInstance.patchLocations.addLast(new Pair<>(origin, type));
        overworldInstance.setDirty();

        CreateExpandedIndustry.LOGGER.debug("Registered new patch. Count: " + overworldInstance.patchLocations.size());
        CreateExpandedIndustry.LOGGER.debug("                   Location: " + origin.toString());
    }


    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("patch_locations", NBTHelper.writeCompoundList(patchLocations, p -> writeLocationPair(p)));

        return tag;
    }

    private CompoundTag writeLocationPair(Pair<BlockPos, HardenedStonePatchFeature.Types> p) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("x", p.getFirst().getX());
        tag.putInt("y", p.getFirst().getY());
        tag.putInt("z", p.getFirst().getZ());
        tag.putString("type", p.getSecond().serialised_value);

        return tag;
    }


    public static void onLoadWorld(LevelEvent.Load event) {
        LevelAccessor accessor = event.getLevel();
        MinecraftServer server = accessor.getServer();
        if (server == null || server.overworld() != accessor)
            return;

        load(server);
    }

    public static HardenedStonePatchSavedData load(MinecraftServer server) {
        return server.overworld()
                .getDataStorage()
                .computeIfAbsent(new Factory<>(HardenedStonePatchSavedData::create, HardenedStonePatchSavedData::load), "hardened_stone_patches");
    }

    public static HardenedStonePatchSavedData create() {
        overworldInstance = new HardenedStonePatchSavedData();
        return overworldInstance;
    }
}
