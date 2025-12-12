package io.github.ayohee.expandedindustry.compat.jei;

import com.simibubi.create.AllItems;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import io.github.ayohee.expandedindustry.compat.jei.categories.ColumnCrackingCategory;
import io.github.ayohee.expandedindustry.content.recipe.ColumnCrackingRecipe;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.register.EIRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.ArrayList;
import java.util.List;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

@JeiPlugin
public class ExpandedIndustryJEI implements IModPlugin {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MODID, "jei_plugin");

    public static IJeiRuntime runtime;

    private final List<CreateRecipeCategory<?>> allCategories = new ArrayList<>();

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(builder(ColumnCrackingRecipe.class)
                .addTypedRecipes(EIRecipeTypes.COLUMN_CRACKING)
                .catalyst(() -> EIBlocks.CRACKING_COLUMN_BASE)
                .doubleItemIcon(EIBlocks.CRACKING_COLUMN_BASE.get(), AllItems.BLAZE_CAKE)
                .emptyBackground(177, 85)
                .build("column_cracking", ColumnCrackingCategory::new));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        allCategories.forEach(c -> c.registerRecipes(registration));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        allCategories.forEach(c -> c.registerCatalysts(registration));
    }



    private <T extends Recipe<? extends RecipeInput>> CategoryBuilder<T> builder(Class<T> recipeClass) {
        return new CategoryBuilder<>(recipeClass);
    }

    private class CategoryBuilder<T extends Recipe<?>> extends CreateRecipeCategory.Builder<T> {
        public CategoryBuilder(Class<? extends T> recipeClass) {
            super(recipeClass);
        }

        @Override
        public CreateRecipeCategory<T> build(ResourceLocation id, CreateRecipeCategory.Factory<T> factory) {
            CreateRecipeCategory<T> category = super.build(id, factory);
            allCategories.add(category);
            return category;
        }
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {
        ExpandedIndustryJEI.runtime = runtime;
    }
}
