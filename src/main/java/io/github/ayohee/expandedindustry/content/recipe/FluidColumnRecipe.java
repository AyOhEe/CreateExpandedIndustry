package io.github.ayohee.expandedindustry.content.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;

public class FluidColumnRecipe extends StandardProcessingRecipe<RecipeInput> {
    public static final int MAX_FLUIDS = 4;

    public FluidColumnRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeParams params) {
        super(typeInfo, params);
    }

    @Override
    public int getMaxInputCount() {
        return 0;
    }

    @Override
    public int getMaxFluidInputCount() {
        return MAX_FLUIDS;
    }

    @Override
    public int getMaxOutputCount() {
        return 0;
    }

    @Override
    public int getMaxFluidOutputCount() {
        return MAX_FLUIDS;
    }

    @Override
    public boolean canRequireHeat() {
        return true;
    }

    @Override
    public boolean matches(RecipeInput input, Level level) {
        return false;
    }
}
