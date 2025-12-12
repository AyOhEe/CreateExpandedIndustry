package io.github.ayohee.expandedindustry.content.complex.crackingcolumn;

import io.github.ayohee.expandedindustry.content.recipe.ColumnCrackingRecipe;
import io.github.ayohee.expandedindustry.multiblock.IHaveFluidStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class CrackingColumnMultiblockBE extends FluidColumnMultiblockBE implements IHaveFluidStorage {
    private static final Object columnCrackingKey = new Object();

    public CrackingColumnMultiblockBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }
    @Override
    public void deconstruct(LevelAccessor level, BlockPos pos) {
        CrackingColumnMultiblock.deconstructMBS(level, pos, size);
    }

    @Override
    public int getSize() {
        return getBlockState().getValue(CrackingColumnMultiblock.SIZE);
    }

    @Override
    public Object getRecipeKey() {
        return columnCrackingKey;
    }

    @Override
    protected boolean matchStaticFilters(RecipeHolder<? extends Recipe<?>> holder) {
        Recipe<?> r = holder.value();
        return (r instanceof ColumnCrackingRecipe ccr)
                && (!ccr.getFluidIngredients().isEmpty());
    }
}
