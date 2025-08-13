package io.github.ayohee.expandedindustry.multiblock;


import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

// Maintains a fluid level of 0 at all times, but keeps track of how much fluid has been added in the last N ticks
public class FluidInputTank extends FluidTank {
    protected int addedThisTick = 0;
    protected float average = 0;
    protected List<Integer> previousTicks = new LinkedList<>();
    protected int tickWindow;
    protected int threshold;

    public FluidInputTank(int capacity, int tickWindow, int threshold) {
        this(capacity, tickWindow, threshold, e -> true);
    }

    public FluidInputTank(int capacity, int tickWindow, int threshold, Predicate<FluidStack> validator) {
        super(capacity, validator);
        this.tickWindow = tickWindow;
        this.threshold = threshold;
    }

    public boolean tick() {
        previousTicks.addLast(addedThisTick);
        addedThisTick = 0;

        if (previousTicks.size() > tickWindow) {
            previousTicks.removeFirst();
        }

        average = (float) previousTicks.stream().reduce(0, Integer::sum) / previousTicks.size();

        if (average == 0) {
            fluid = FluidStack.EMPTY;
        }

        return true;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (fluid.isEmpty() && isFluidValid(resource)) {
            fluid = new FluidStack(resource.getFluid(), 1);
        }

        if (!resource.is(fluid.getFluidType())) {
            return 0;
        }

        int canTake = Math.min(threshold - addedThisTick, resource.getAmount());
        if (action.execute()) {
            addedThisTick += canTake;
        }

        return canTake;
    }

    @Override
    public void setFluid(FluidStack stack) {
        if (isFluidValid(stack)) {
            addedThisTick += stack.getAmount();
            fluid = new FluidStack(stack.getFluid(), 1);
        }
    }

    public FluidType getFluidType() { return fluid.getFluidType(); }

    public float getAverageInput() {
        return average;
    }

    public FluidStack getFluid() {
        return FluidStack.EMPTY;
    }

    public int getFluidAmount() {
        return 0;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return this.threshold;
    }

    public boolean isSatisfied() {
        // Typically, because fluids are weird, even if it's being supplied N mb/t, it'll go between (N) and (N - 1)
        // To make sure things actually acknowledge that they are powered, lower the threshold by *just a bit*
        return threshold != 0 && average >= (threshold * 0.9f);
    }


    public FluidTank readFromNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
        fluid = FluidStack.parseOptional(lookupProvider, nbt.getCompound("Fluid"));
        previousTicks = new LinkedList<>();
        nbt.getList("previous_tick_amounts", Tag.TAG_INT).forEach(v -> previousTicks.add(((IntTag)v).getAsInt()));

        if (!previousTicks.isEmpty()) {
            average = (float) previousTicks.stream().reduce(0, Integer::sum) / previousTicks.size();
        }

        return this;
    }

    public CompoundTag writeToNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
        if (!fluid.isEmpty()) {
            nbt.put("Fluid", fluid.save(lookupProvider));
        }

        ListTag ticks = new ListTag();
        ticks.addAll(previousTicks.stream().map(IntTag::valueOf).toList());
        nbt.put("previous_tick_amounts", ticks);

        return nbt;
    }
}
