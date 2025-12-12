package io.github.ayohee.expandedindustry.content.complex.crackingcolumn;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import io.github.ayohee.expandedindustry.content.recipe.ColumnCrackingRecipe;
import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockControllerBE;
import io.github.ayohee.expandedindustry.multiblock.IHaveFluidStorage;
import io.github.ayohee.expandedindustry.register.EIRecipeTypes;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.ayohee.expandedindustry.content.recipe.ColumnCrackingRecipe.MAX_FLUIDS;


public class CrackingColumnMultiblockBE extends AbstractMultiblockControllerBE implements IHaveFluidStorage {
    private final int INDIVIDUAL_CAPACITY = 8000;
    private static final Object columnCrackingKey = new Object();

    ArrayList<FluidTank> fluids = new ArrayList<>();
    protected ColumnCrackingRecipe currentRecipe;

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
    public void tick() {
        super.tick();

        // Each recipe is defined as the amount processed per tick
        findRecipe();
        if (currentRecipe != null && sufficientlyHeated()) {
            applyRecipe();
        }
    }

    private boolean sufficientlyHeated() {
        // Are we sufficiently heated?
        int nHeated = findUsableBurners(currentRecipe);
        int requiredBurners = (size*size) / 2;

        // Nope - stop here
        if (nHeated < requiredBurners) {
            return false;
        }

        return true;
    }

    private void findRecipe() {
        List<Recipe<?>> recipes = getMatchingRecipes();
        if (recipes.isEmpty()){
            return;
        }
        currentRecipe = (ColumnCrackingRecipe) recipes.getFirst();
    }

    private void applyRecipe() {
        // Can we remove enough of each ingredient?
        for (FluidIngredient fIngredient : currentRecipe.getFluidIngredients()) {
            boolean foundMatch = false;
            for (FluidTank tank : fluids) {
                if (!fIngredient.test(tank.getFluid())) {
                    continue; // Not the right fluid
                }

                FluidStack drained = tank.drain(fIngredient.getRequiredAmount(), FluidAction.SIMULATE);
                if (drained.getAmount() < fIngredient.getRequiredAmount()) {
                    continue; // We don't have enough for this ingredient
                }

                foundMatch = true;
            }

            if (!foundMatch) {
                return; // None of the tanks could properly supply the ingredient - stop here.
            }
        }

        // Yes we can - can we add the results?
        int nRequiredTanks = 0;
        for (FluidStack result : currentRecipe.getFluidResults()) {
            boolean foundMatch = false;
            for (FluidTank tank : fluids) {
                // Can we even insert it?
                if (!FluidStack.isSameFluidSameComponents(tank.getFluid(), result)) {
                    continue;
                }

                // Do we have room? If not, the recipe flat out can't continue, as we know the fluid
                // matches by the time we get here
                if ((tank.getCapacity() - tank.getFluidAmount()) < result.getAmount()) {
                    return;
                }

                foundMatch = true;
            }

            // No match - we'll need to make another tank
            if (!foundMatch) {
                nRequiredTanks += 1;
            }
        }

        // We'd need to make more tanks than we have room for, recipe can't continue
        if (nRequiredTanks > ((MAX_FLUIDS * 2) - fluids.size())) {
            return;
        }


        // Now, actually remove the ingredients
        for (FluidIngredient fIngredient : currentRecipe.getFluidIngredients()) {
            for (FluidTank tank : fluids) {
                if (!fIngredient.test(tank.getFluid())) {
                    continue;
                }

                if (tank.getFluidAmount() < fIngredient.getRequiredAmount()) {
                    continue;
                }

                tank.drain(fIngredient.getRequiredAmount(), FluidAction.EXECUTE);
                break; // Only drain from one tank
            }
        }

        // Remove any empty tanks we might have
        fluids = fluids.stream().filter((t) -> !t.isEmpty()).collect(Collectors.toCollection(ArrayList::new));

        // And insert the results
        for (FluidStack result : currentRecipe.getFluidResults()) {
            boolean wasAdded = false;
            for (FluidTank tank : fluids) {
                if (FluidStack.isSameFluidSameComponents(result, tank.getFluid())) {
                    tank.fill(result, FluidAction.EXECUTE);
                    wasAdded = true;
                    break;
                }
            }

            if (!wasAdded) {
                FluidTank newTank = new FluidTank(INDIVIDUAL_CAPACITY);
                newTank.fill(result, FluidAction.EXECUTE);
                fluids.add(newTank);
            }
        }
    }

    protected List<Recipe<?>> getMatchingRecipes() {
        if (fluids.isEmpty()) {
            return new ArrayList<>();
        }

        List<Recipe<?>> list = new ArrayList<>();
        for (RecipeHolder<? extends Recipe<?>> r : RecipeFinder.get(columnCrackingKey, level, this::matchStaticFilters)) {
            if (matchColumnRecipe(r.value())) {
                list.add(r.value());
            }
        }

        list.sort((r1, r2) -> r2.getIngredients().size() - r1.getIngredients().size());

        return list;
    }

    private boolean matchColumnRecipe(Recipe<?> value) {
        if (!(value instanceof ColumnCrackingRecipe ccr)) {
            return false;
        }

        List<Ingredient> sIngredients = ccr.getIngredients();
        List<FluidIngredient> fIngredients = ccr.getFluidIngredients();
        List<ItemStack> sResults = ccr.getRollableResultsAsItemStacks();
        List<FluidStack> fResults = ccr.getFluidResults();

        // We literally don't have room for any solids
        if (!sIngredients.isEmpty() || !sResults.isEmpty()) {
            return false;
        }

        // Check that we have each ingredient
        for (FluidIngredient f : fIngredients) {
            boolean foundMatch = false;
            for (FluidTank tank : fluids) {
                if (f.test(tank.getFluid())) {
                    foundMatch = true;
                }
            }

            // One of the ingredients wasn't present.
            if (!foundMatch) {
                return false;
            }
        }

        // Check that we have room for the results
        int nRemaining = 0;
        for (FluidStack f : fResults) {
            boolean foundMatch = false;
            for (FluidTank tank : fluids) {
                if (FluidStack.isSameFluidSameComponents(tank.getFluid(), f)) {
                    foundMatch = true;
                }
            }

            // No matching tank - we'd have to make a new one
            if (!foundMatch) {
                nRemaining += 1;
            }
        }

        // We'd have to make more tanks than we have room for.
        if (nRemaining > ((MAX_FLUIDS * 2) - fluids.size())) {
            return false;
        }

        return true;
    }

    private int findUsableBurners(ColumnCrackingRecipe recipe) {
        if (recipe == null) {
            return 0;
        }

        int nHeated = 0;
        for (BlockPos burnerPos : burnersBelowColumn()) {
            BlockState bs = level.getBlockState(burnerPos);
            BlazeBurnerBlock.HeatLevel heat = BasinBlockEntity.getHeatLevelOf(bs);
            if (recipe.getRequiredHeat().testBlazeBurner(heat)) {
                nHeated += 1;
            }
        }

        return nHeated;
    }

    private List<BlockPos> burnersBelowColumn() {
        LinkedList<BlockPos> burners = new LinkedList<>();

        BlockPos p = getBlockPos().below();
        while (level.getBlockState(p).getBlock() instanceof CrackingColumnModelBlock) {
            p = p.below();
        }

        switch (size) {
            case 1 -> {
                burners.add(p);
            }
            case 2 -> {
                burners.add(p);
                burners.add(p.north());
                burners.add(p.west());
                burners.add(p.north().west());
            }
            default -> {
                burners.add(p);
                burners.add(p.south());
                burners.add(p.south().west());
                burners.add(p.south().east());
                burners.add(p.north());
                burners.add(p.north().west());
                burners.add(p.north().east());
                burners.add(p.west());
                burners.add(p.east());
            }
        }

        return burners;
    }

    private boolean matchStaticFilters(RecipeHolder<? extends Recipe<?>> holder) {
        Recipe<?> r = holder.value();
        return (r instanceof ColumnCrackingRecipe ccr)
                && (!ccr.getFluidIngredients().isEmpty());
    }

    @Override
    public List<Component> multiblockTooltip(boolean isPlayerSneaking) {
        ArrayList<Component> tooltip = new ArrayList<>();

        if (currentRecipe != null) {
            tooltip.add(Component.literal("    Viable burners: " + findUsableBurners(currentRecipe)));
        } else {
            tooltip.add(Component.literal("    Viable burners: No recipe selected"));
        }

        if (!fluids.isEmpty()) {
            tooltip.add(Component.literal("    Fluid contents: "));
            for (FluidTank tank : fluids) {
                tooltip.add(Component.literal("     - " + describeFluidstack(tank.getFluid())));
            }
        } else {
            tooltip.add(Component.literal("    Fluid contents: Empty"));
        }

        return tooltip;
    }

    private String describeFluidstack(FluidStack fluid) {
        return fluid.getAmount() + " / " + INDIVIDUAL_CAPACITY + " :: " + fluid.getFluidType().getDescription().getString();
    }


    public int insertFluid(FluidStack f) {
        if (f.isEmpty()) {
            return 0;
        }
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

        int result = relevantTank.fill(f, FluidAction.EXECUTE);

        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        return result;
    }

    public FluidStack removeFluid(FluidStack f) {
        if (f.isEmpty()) {
            return FluidStack.EMPTY;
        }
        FluidTank relevantTank = findTankWithFluid(f);

        if (relevantTank == null) {
            return FluidStack.EMPTY;
        }

        FluidStack result = relevantTank.drain(f, FluidAction.EXECUTE);
        if (relevantTank.isEmpty()) {
            fluids.remove(relevantTank);
        }

        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        return result;
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
