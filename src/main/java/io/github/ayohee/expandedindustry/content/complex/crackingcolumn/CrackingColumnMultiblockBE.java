package io.github.ayohee.expandedindustry.content.complex.crackingcolumn;

import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockControllerBE;
import io.github.ayohee.expandedindustry.multiblock.IHaveFluidStorage;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.util.ArrayList;
import java.util.List;

import static io.github.ayohee.expandedindustry.content.recipe.ColumnCrackingRecipe.MAX_FLUIDS;


public class CrackingColumnMultiblockBE extends AbstractMultiblockControllerBE implements IHaveFluidStorage {
    private final int INDIVIDUAL_CAPACITY = 8000;

    ArrayList<FluidTank> fluids = new ArrayList<>();

    private final int size;

    public CrackingColumnMultiblockBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        size = blockState.getValue(CrackingColumnMultiblock.SIZE);
    }

    @Override
    public void deconstruct(LevelAccessor level, BlockPos pos) {
        CrackingColumnMultiblock.deconstructMBS(level, pos, size);
    }

    @Override
    public List<Component> multiblockTooltip(boolean isPlayerSneaking) {
        ArrayList<Component> tooltip = new ArrayList<>();

        if (!fluids.isEmpty()) {
            tooltip.add(Component.literal("    Fluid contents: "));
            for (FluidTank tank : fluids) {
                tooltip.add(Component.literal("    " + tank.getFluid()));
            }
        } else {
            tooltip.add(Component.literal("    Fluid contents: Empty"));
        }

        return tooltip;
    }


    public int insertFluid(FluidStack f) {
        FluidTank relevantTank = findTankWithFluid(f);

        // If we don't have room for another tank, and would have to create one, reject the insertion
        if((relevantTank == null) && (fluids.size() >= MAX_FLUIDS)) {
            return 0;
        }

        // We must have room, or we'd have failed the last check, so make the tank if it doesn't exist
        if (relevantTank == null) {
            relevantTank = new FluidTank(INDIVIDUAL_CAPACITY);
            fluids.add(relevantTank);
        }

        return relevantTank.fill(f, FluidAction.EXECUTE);
    }

    public FluidStack removeFluid(FluidStack f) {
        FluidTank relevantTank = findTankWithFluid(f);

        if (relevantTank == null) {
            return FluidStack.EMPTY;
        }

        return relevantTank.drain(f, FluidAction.EXECUTE);
    }

    public List<FluidStack> getContents() {
        return fluids.stream().map(FluidTank::getFluid).toList();
    }


    private FluidTank findTankWithFluid(FluidStack f) {
        FluidTank relevantTank = null;
        for (FluidTank tank : fluids) {
            if (FluidStack.isSameFluidSameComponents(tank.getFluid(), f)) {
                relevantTank = tank;
            }
        }

        return relevantTank;
    }


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("fluids", NBTHelper.writeCompoundList(
                fluids, (t) -> t.writeToNBT(registries, new CompoundTag())
        ));

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        fluids = new ArrayList<>(NBTHelper.readCompoundList(tag.getList("fluids", Tag.TAG_COMPOUND),
                (t) -> new FluidTank(INDIVIDUAL_CAPACITY).readFromNBT(registries, t)
        ));

        super.loadAdditional(tag, registries);
    }

    @Override
    public int multiblockTooltipPriority(boolean isPlayerSneaking) {
        return 5;
    }
}
