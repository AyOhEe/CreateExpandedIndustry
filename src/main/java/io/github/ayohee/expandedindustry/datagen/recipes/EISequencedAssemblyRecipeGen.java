package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.data.recipe.SequencedAssemblyRecipeGen;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import io.github.ayohee.expandedindustry.register.EIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

import java.util.concurrent.CompletableFuture;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class EISequencedAssemblyRecipeGen extends SequencedAssemblyRecipeGen {
    public final GeneratedRecipe BOLT_CASTING = create("bolt_casting", b -> b
            .require(EIItems.BOLT_CAST)
            .transitionTo(EIItems.INCOMPLETE_BOLT)
            .addOutput(EIItems.HEATED_FILLED_BOLT_CAST, 100)
            .addStep(DeployerApplicationRecipe::new, r -> r.require(Items.IRON_INGOT))
            .addStep(DeployerApplicationRecipe::new, r -> r.require(EIItems.BOLT_CAST))
            .addStep(FillingRecipe::new, r -> r.require(Fluids.LAVA, 500))
            .loops(1)
    );

    public final GeneratedRecipe KINETIC_TRANSISTOR = create("kinetic_transistor", b -> b
            .require(AllBlocks.SPEEDOMETER)
            .transitionTo(EIItems.INCOMPLETE_KINETIC_TRANSISTOR)
            .addOutput(EIItems.KINETIC_TRANSISTOR, 100)
            .addStep(DeployerApplicationRecipe::new, r -> r.require(Blocks.COMPARATOR))
            .addStep(DeployerApplicationRecipe::new, r -> r.require(AllBlocks.CLUTCH))
            .loops(1)
    );

    public EISequencedAssemblyRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MODID);
    }
}
