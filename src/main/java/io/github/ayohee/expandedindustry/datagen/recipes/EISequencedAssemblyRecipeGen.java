package io.github.ayohee.expandedindustry.datagen.recipes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.SequencedAssemblyRecipeGen;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
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
            .addOutput(EIItems.HEATED_FILLED_BOLT_CAST, 90)
            .addOutput(Items.IRON_INGOT, 10)
            .addStep(DeployerApplicationRecipe::new, r -> r.require(Items.IRON_INGOT))
            .addStep(DeployerApplicationRecipe::new, r -> r.require(EIItems.BOLT_CAST))
            .addStep(FillingRecipe::new, r -> r.require(Fluids.LAVA, 500))
            .loops(1)
    );

    public final GeneratedRecipe KINETIC_TRANSISTOR = create("kinetic_transistor", b -> b
            .require(AllItems.BRASS_SHEET)
            .transitionTo(EIItems.INCOMPLETE_KINETIC_TRANSISTOR)
            .addOutput(EIItems.KINETIC_TRANSISTOR, 80)
            .addOutput(Items.COMPARATOR, 10)
            .addOutput(AllBlocks.SHAFT, 10)
            .addStep(DeployerApplicationRecipe::new, r -> r.require(AllBlocks.SPEEDOMETER))
            .addStep(DeployerApplicationRecipe::new, r -> r.require(Blocks.COMPARATOR))
            .addStep(DeployerApplicationRecipe::new, r -> r.require(AllBlocks.CLUTCH))
            .loops(1)
    );

    public final GeneratedRecipe PLASTIC_COMPOSITE = create("plastic_composite", b -> b
            .require(EIItems.POLYETHYLENE_SHEET)
            .transitionTo(EIItems.INCOMPLETE_PLASTIC_COMPOSITE)
            .addOutput(EIItems.PLASTIC_COMPOSITE, 90)
            .addOutput(EIItems.POLYETHYLENE_SHEET, 10)
            .addStep(DeployerApplicationRecipe::new, r -> r.require(EIItems.REINFORCED_PLATING))
            .addStep(DeployerApplicationRecipe::new, r -> r.require(EIItems.POLYETHYLENE_SHEET))
            .addStep(PressingRecipe::new, r -> r)
            .loops(3)
    );

    public final GeneratedRecipe MAGNETIC_STORAGE_DRIVE = create("magnetic_storage_drive", b -> b
            .require(EIItems.PLASTIC_COMPOSITE)
            .transitionTo(EIItems.INCOMPLETE_MAGNETIC_STORAGE_DRIVE)
            .addOutput(EIItems.MAGNETIC_STORAGE_DRIVE, 10)
            .addOutput(AllBlocks.SHAFT, 20)
            .addOutput(EIItems.MAGNETIC_PLATTER, 70)
            .addStep(DeployerApplicationRecipe::new, r -> r.require(AllBlocks.SHAFT))
            .addStep(DeployerApplicationRecipe::new, r -> r.require(EIItems.MAGNETIC_PLATTER))
            .addStep(DeployerApplicationRecipe::new, r -> r.require(EIItems.BRASS_NEEDLE))
            .addStep(PressingRecipe::new, r -> r)
            .loops(10)
    );

    public final GeneratedRecipe KINETIC_MEMORY_CELL = create("kinetic_memory_cell", b -> b
            .require(EIItems.KINETIC_LOGIC_GATE)
            .transitionTo(EIItems.INCOMPLETE_KINETIC_MEMORY_CELL)
            .addOutput(EIItems.KINETIC_MEMORY_CELL, 100)
            .addStep(DeployerApplicationRecipe::new, r -> r.require(AllItems.ELECTRON_TUBE))
            .addStep(DeployerApplicationRecipe::new, r -> r.require(EIItems.KINETIC_LOGIC_GATE))
            .addStep(DeployerApplicationRecipe::new, r -> r.require(EIItems.KINETIC_LOGIC_GATE))
            .addStep(DeployerApplicationRecipe::new, r -> r.require(AllItems.GOLDEN_SHEET))
            .loops(8)
    );

    public final GeneratedRecipe KINETIC_CIRCUIT = create("kinetic_circuit", b -> b
            .require(EIItems.KINETIC_LOGIC_GATE)
            .transitionTo(EIItems.INCOMPLETE_KINETIC_CIRCUIT)
            .addOutput(EIItems.KINETIC_CIRCUIT, 100)
            .addStep(DeployerApplicationRecipe::new, r -> r.require(EIItems.KINETIC_LOGIC_GATE))
            .addStep(DeployerApplicationRecipe::new, r -> r.require(EIItems.KINETIC_LOGIC_GATE))
            .addStep(DeployerApplicationRecipe::new, r -> r.require(EIItems.KINETIC_LOGIC_GATE))
            .addStep(DeployerApplicationRecipe::new, r -> r.require(AllItems.GOLDEN_SHEET))
            .loops(8)
    );

    public EISequencedAssemblyRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MODID);
    }
}
