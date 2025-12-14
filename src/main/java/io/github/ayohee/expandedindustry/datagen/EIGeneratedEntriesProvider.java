package io.github.ayohee.expandedindustry.datagen;

import io.github.ayohee.expandedindustry.register.EIBiomeData;
import io.github.ayohee.expandedindustry.register.EIBiomeModifiers;
import io.github.ayohee.expandedindustry.register.EIConfiguredFeatures;
import io.github.ayohee.expandedindustry.register.EIPlacedFeature;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EIGeneratedEntriesProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, EIConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, EIPlacedFeature::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, EIBiomeModifiers::bootstrap)
            .add(Registries.BIOME, EIBiomeData::bootstrap);


    public EIGeneratedEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(MODID));
    }

    @Override
    public String getName() {
        return "Create: Expanded Industry's Generated Registry Entries";
    }
}
