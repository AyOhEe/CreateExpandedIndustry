package io.github.ayohee.expandedindustry.multiblock;

import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public interface IHaveFluidStorage {
    int insertFluid(FluidStack f);
    FluidStack removeFluid(FluidStack f);
    List<FluidStack> getContents();
}
