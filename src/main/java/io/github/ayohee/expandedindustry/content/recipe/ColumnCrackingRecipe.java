package io.github.ayohee.expandedindustry.content.recipe;


import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import io.github.ayohee.expandedindustry.register.EIRecipeTypes;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;

public class ColumnCrackingRecipe extends StandardProcessingRecipe<RecipeInput> {
    public static final int MAX_FLUIDS = 4;

    public ColumnCrackingRecipe(ProcessingRecipeParams params) {
        super(EIRecipeTypes.COLUMN_CRACKING, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 0;
    }

    @Override
    protected int getMaxFluidInputCount() {
        return MAX_FLUIDS;
    }

    @Override
    protected int getMaxOutputCount() {
        return 0;
    }

    @Override
    protected int getMaxFluidOutputCount() {
        return MAX_FLUIDS;
    }

    @Override
    protected boolean canRequireHeat() {
        return true;
    }

    @Override
    public boolean matches(RecipeInput input, Level level) {
        return false;
    }
}
