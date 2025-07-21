package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.api.stress.BlockStressValues;
import io.github.ayohee.expandedindustry.register.config.EICommon;
import io.github.ayohee.expandedindustry.register.config.EIServer;
import net.createmod.catnip.config.ConfigBase;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Supplier;


public class EIConfig {
    private static EICommon common;
    private static EIServer server;

    public static EICommon common() { return common; }
    public static EIServer server() { return server; }

    private static <T extends ConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
        Pair<T, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(builder -> {
            T config = factory.get();
            config.registerAll(builder);
            return config;
        });

        T config = specPair.getLeft();
        config.specification = specPair.getRight();
        return config;
    }

    public static void register(ModContainer modContainer) {
        common = register(EICommon::new, ModConfig.Type.COMMON);
        server = register(EIServer::new, ModConfig.Type.SERVER);

        modContainer.registerConfig(ModConfig.Type.COMMON, common.specification);
        modContainer.registerConfig(ModConfig.Type.SERVER, server.specification);

        BlockStressValues.IMPACTS.registerProvider(server.stress::getImpact);
        BlockStressValues.CAPACITIES.registerProvider(server.stress::getCapacity);
    }
}
