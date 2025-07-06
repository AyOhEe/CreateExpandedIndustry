package io.github.ayohee.expandedindustry;

import io.github.ayohee.expandedindustry.datagen.EIRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EIDatagen {
    public static void gatherDataHighPriority(GatherDataEvent event) {
        if (event.getMods().contains(MODID)) {
            addExtraRegistrateData();
        }
    }

    public static void gatherData(GatherDataEvent event) {
        if (!event.getMods().contains(MODID)) {
            return;
        }
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        System.out.println("Gathering data for Create: Expanded Industry");
        System.out.println(event.includeServer());
        if (event.includeServer()) {
            EIRecipeProvider.registerAllProcessing(generator, output, lookupProvider);
        }
    }

    private static void addExtraRegistrateData() {
        EIRegistrateTags.addGenerators();
    }
}
