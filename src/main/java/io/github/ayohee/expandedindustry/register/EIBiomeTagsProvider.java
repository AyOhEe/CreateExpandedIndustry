package io.github.ayohee.expandedindustry.register;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class EIBiomeTagsProvider extends TagsProvider<Biome> {
    public EIBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, String modId, ExistingFileHelper existingFileHelper) {
        super(output, Registries.BIOME, provider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BiomeTags.IS_DEEP_OCEAN).add(EIBiomes.OCEAN_OIL_FIELD);
        this.tag(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS).add(EIBiomes.INLAND_OIL_FIELD);
        this.tag(BiomeTags.IS_OVERWORLD).add(EIBiomes.OCEAN_OIL_FIELD).add(EIBiomes.INLAND_OIL_FIELD);
    }
}
