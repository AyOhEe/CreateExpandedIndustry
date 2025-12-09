package io.github.ayohee.expandedindustry.content.complex.crackingcolumn;

import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockControllerBE;
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
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.util.ArrayList;
import java.util.List;

import static io.github.ayohee.expandedindustry.content.recipe.ColumnCrackingRecipe.MAX_FLUIDS;


public class CrackingColumnMultiblockBE extends AbstractMultiblockControllerBE {
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
        tooltip.add(Component.literal("    Fluid contents: "));
        for (FluidTank tank : fluids) {
            tooltip.add(Component.literal("    " + tank.getFluid().toString()));
        }

        return List.of();
    }


    public FluidStack insertFluid(FluidStack f) {
        if(fluids.size() >= MAX_FLUIDS) {
            return FluidStack.EMPTY;
        }

        //TODO do some checks

        return f;
    }

    public FluidStack removeFluid(FluidStack f) {
        //TODO do some checks

        return f;
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
