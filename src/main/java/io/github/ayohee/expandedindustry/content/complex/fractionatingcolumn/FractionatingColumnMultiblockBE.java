package io.github.ayohee.expandedindustry.content.complex.fractionatingcolumn;

import io.github.ayohee.expandedindustry.content.complex.crackingcolumn.CrackingColumnMultiblock;
import io.github.ayohee.expandedindustry.content.complex.crackingcolumn.FluidColumnMultiblockBE;
import io.github.ayohee.expandedindustry.content.recipe.ColumnCrackingRecipe;
import io.github.ayohee.expandedindustry.content.recipe.FractionatingColumnRecipe;
import io.github.ayohee.expandedindustry.multiblock.AbstractMultiblockControllerBE;
import io.github.ayohee.expandedindustry.multiblock.IHaveFluidStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FractionatingColumnMultiblockBE extends FluidColumnMultiblockBE implements IHaveFluidStorage {
    private static final Object fractionatingKey = new Object();

    public FractionatingColumnMultiblockBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }
    @Override
    public void deconstruct(LevelAccessor level, BlockPos pos) {
        FractionatingColumnMultiblock.deconstructMBS(level, pos, size);
    }

    @Override
    public int getSize() {
        return getBlockState().getValue(FractionatingColumnMultiblock.SIZE);
    }

    @Override
    public Object getRecipeKey() {
        return fractionatingKey;
    }

    @Override
    protected boolean matchStaticFilters(RecipeHolder<? extends Recipe<?>> holder) {
        Recipe<?> r = holder.value();
        return (r instanceof FractionatingColumnRecipe fcr)
                && (!fcr.getFluidIngredients().isEmpty());
    }
}
