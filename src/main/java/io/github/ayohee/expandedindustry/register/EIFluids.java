package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Vector3f;

import java.util.function.Supplier;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.REGISTRATE;

public class EIFluids {
    static {
        REGISTRATE.setCreativeTab(EICreativeTabs.MAIN_TAB);
    }


    public static final FluidEntry<BaseFlowingFluid.Flowing> CRUDE_OIL = REGISTRATE
            .standardFluid("crude_oil", SolidRenderedPlaceableFluidType.create(0x404040, () -> 1f / 16f))
            .tag(AllTags.commonFluidTag("crude_oil"), AllTags.AllFluidTags.BOTTOMLESS_DENY.tag)
            .properties(b -> b.viscosity(1500)
                    .density(1400))
            .fluidProperties(p -> p.levelDecreasePerBlock(2)
                    .tickRate(25)
                    .slopeFindDistance(3)
                    .explosionResistance(100f))
            .source(BaseFlowingFluid.Source::new)
            .block()
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK))
            .build()
            .bucket()
            .tag(AllTags.commonItemTag("buckets/oil"))
            .build()
            .register();



    public static final FluidEntry<BaseFlowingFluid.Flowing> NAPHTHA = REGISTRATE
            .standardFluid("naphtha", SolidRenderedPlaceableFluidType.create(0xFF6F00, () -> 1f / 2f))
            .tag(AllTags.commonFluidTag("naphtha"), AllTags.AllFluidTags.BOTTOMLESS_DENY.tag)
            .properties(b -> b.viscosity(1500)
                    .density(1400))
            .fluidProperties(p -> p.levelDecreasePerBlock(2)
                    .tickRate(25)
                    .slopeFindDistance(3)
                    .explosionResistance(100f))
            .source(BaseFlowingFluid.Source::new)
            .block()
            .properties(p -> p.mapColor(MapColor.COLOR_BROWN))
            .build()
            .bucket()
            .tag(AllTags.commonItemTag("buckets/naphtha"))
            .build()
            .register();

    public static final FluidEntry<VirtualFluid> LIQUID_PETROLEUM_GAS = REGISTRATE.virtualFluid("liquid_petroleum_gas")
            .tag(AllTags.commonFluidTag("liquid_petroleum_gas"), AllTags.AllFluidTags.BOTTOMLESS_DENY.tag)
            .register();

    public static final FluidEntry<BaseFlowingFluid.Flowing> KEROSENE = REGISTRATE
            .standardFluid("kerosene", SolidRenderedPlaceableFluidType.create(0x808080, () -> 1f / 2f))
            .tag(AllTags.commonFluidTag("kerosene"), AllTags.AllFluidTags.BOTTOMLESS_DENY.tag)
            .properties(b -> b.viscosity(1500)
                    .density(1400))
            .fluidProperties(p -> p.levelDecreasePerBlock(2)
                    .tickRate(25)
                    .slopeFindDistance(3)
                    .explosionResistance(100f))
            .source(BaseFlowingFluid.Source::new)
            .block()
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
            .build()
            .bucket()
            .tag(AllTags.commonItemTag("buckets/kerosene"))
            .build()
            .register();

    public static final FluidEntry<VirtualFluid> ETHYLENE = REGISTRATE.virtualFluid("ethylene")
            .tag(AllTags.commonFluidTag("ethylene"), AllTags.AllFluidTags.BOTTOMLESS_DENY.tag)
            .register();

    public static final FluidEntry<BaseFlowingFluid.Flowing> RAW_LUBRICATING_OIL = REGISTRATE
            .standardFluid("raw_lubricating_oil", SolidRenderedPlaceableFluidType.create(0xF2F542, () -> 1f / 2f))
            .tag(AllTags.commonFluidTag("raw_lubricating_oil"), AllTags.AllFluidTags.BOTTOMLESS_DENY.tag)
            .properties(b -> b.viscosity(1500)
                    .density(1400))
            .fluidProperties(p -> p.levelDecreasePerBlock(2)
                    .tickRate(25)
                    .slopeFindDistance(3)
                    .explosionResistance(100f))
            .source(BaseFlowingFluid.Source::new)
            .block()
            .properties(p -> p.mapColor(MapColor.COLOR_YELLOW))
            .build()
            .bucket()
            .tag(AllTags.commonItemTag("buckets/raw_lubricating_oil"))
            .build()
            .register();

    public static final FluidEntry<BaseFlowingFluid.Flowing> LUBRICANT = REGISTRATE
            .standardFluid("lubricant", SolidRenderedPlaceableFluidType.create(0x1AB000, () -> 1f / 2f))
            .tag(AllTags.commonFluidTag("lubricant"), AllTags.AllFluidTags.BOTTOMLESS_DENY.tag)
            .properties(b -> b.viscosity(1500)
                    .density(1400))
            .fluidProperties(p -> p.levelDecreasePerBlock(2)
                    .tickRate(25)
                    .slopeFindDistance(3)
                    .explosionResistance(100f))
            .source(BaseFlowingFluid.Source::new)
            .block()
            .properties(p -> p.mapColor(MapColor.COLOR_GREEN))
            .build()
            .bucket()
            .tag(AllTags.commonItemTag("buckets/lubricant"))
            .build()
            .register();

    public static final FluidEntry<BaseFlowingFluid.Flowing> HOT_BITUMEN = REGISTRATE
            .standardFluid("hot_bitumen", SolidRenderedPlaceableFluidType.create(0x606060, () -> 1f / 2f))
            .tag(AllTags.commonFluidTag("hot_bitumen"), AllTags.AllFluidTags.BOTTOMLESS_DENY.tag)
            .properties(b -> b.viscosity(1500)
                    .density(1400))
            .fluidProperties(p -> p.levelDecreasePerBlock(2)
                    .tickRate(25)
                    .slopeFindDistance(3)
                    .explosionResistance(100f))
            .source(BaseFlowingFluid.Source::new)
            .block()
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK))
            .build()
            .bucket()
            .tag(AllTags.commonItemTag("buckets/hot_bitumen"))
            .build()
            .register();


    public static void register() { }


    private static class SolidRenderedPlaceableFluidType extends AllFluids.TintedFluidType {

        private Vector3f fogColor;
        private Supplier<Float> fogDistance;
        private int tint;


        public static FluidBuilder.FluidTypeFactory create(int fogColor, Supplier<Float> fogDistance) {
            return create(fogColor, NO_TINT, fogDistance);
        }

        public static FluidBuilder.FluidTypeFactory create(int fogColor, int tint, Supplier<Float> fogDistance) {
            return (p, s, f) -> {
                SolidRenderedPlaceableFluidType fluidType = new SolidRenderedPlaceableFluidType(p, s, f);
                fluidType.fogColor = new Color(fogColor, false).asVectorF();
                fluidType.fogDistance = fogDistance;
                fluidType.tint = tint;
                return fluidType;
            };
        }

        private SolidRenderedPlaceableFluidType(Properties properties, ResourceLocation stillTexture,
                                                ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        protected int getTintColor(FluidStack stack) {
            return tint;
        }

        /*
         * Removing alpha from tint prevents optifine from forcibly applying biome
         * colors to modded fluids (this workaround only works for fluids in the solid
         * render layer)
         */
        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return tint & 0x00ffffff;
        }

        @Override
        protected Vector3f getCustomFogColor() {
            return fogColor;
        }

        @Override
        protected float getFogDistanceModifier() {
            return fogDistance.get();
        }

    }
}
