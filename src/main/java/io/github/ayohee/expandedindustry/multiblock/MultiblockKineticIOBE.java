package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.CreateLang;
import com.simibubi.create.infrastructure.config.AllConfigs;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.util.NBTHelperEI;
import net.createmod.catnip.lang.FontHelper;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static net.minecraft.ChatFormatting.GOLD;

public class MultiblockKineticIOBE extends KineticBlockEntity implements IMultiblockComponentBE {
    MultiblockControllerBE controller = null;
    protected List<BlockPos> pool = new LinkedList<>();
    BlockPos controllerPos = null;
    boolean initialised = false;

    float stressImpact = 0;
    float minimumRotationSpeed = 0;

    public MultiblockKineticIOBE(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (!initialised) {
            if (!level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
            initialised = true;
        }
    }


    public void poolWith(MultiblockKineticIOBE be) {
        pool.add(be.getBlockPos());
    }

    public List<MultiblockKineticIOBE> getPool() {
        List<MultiblockKineticIOBE> bePool = new LinkedList<>();
        for (BlockPos pos : pool) {
            Optional<MultiblockKineticIOBE> be = level.getBlockEntity(pos, EIBlockEntityTypes.MULTIBLOCK_KINETIC_IO.get());
            if (be.isPresent())
                bePool.add(be.get());
        }
        return bePool;
    }

    @Override
    public List<BlockPos> addPropagationLocations(IRotate block, BlockState state, List<BlockPos> neighbours) {
        for (MultiblockKineticIOBE be : getPool()) {
            neighbours.add(be.worldPosition);
        }
        return neighbours;
    }

    @Override
    public boolean isCustomConnection(KineticBlockEntity other, BlockState state, BlockState otherState) {
        if (!(other instanceof MultiblockKineticIOBE))
            return false;
        return getPool().contains(other);
    }

    @Override
    public float propagateRotationTo(KineticBlockEntity target, BlockState stateFrom, BlockState stateTo, BlockPos diff,
                                     boolean connectedViaAxes, boolean connectedViaCogs) {
        float normalPropagation = super.propagateRotationTo(target, stateFrom, stateTo, diff, connectedViaAxes, connectedViaCogs);
        if (normalPropagation != 0)
            return normalPropagation;

        if (!(target instanceof MultiblockKineticIOBE))
            return 0;
        if (getPool().contains(target))
            return 1;

        return 0;
    }


    @Override
    public float calculateStressApplied() {
        lastStressApplied = stressImpact;
        return stressImpact;
    }

    public void setConfiguredStressImpact(float stress) {
        stressImpact = stress;
        if(lastStressApplied != stress && hasNetwork()) {
            getOrCreateNetwork().updateStressFor(this, calculateStressApplied());
            networkDirty = true;
            notifyUpdate();
        }
    }

    public float getConfiguredStressImpact() {
        return stressImpact;
    }

    public void setMinimumRotationSpeed(float mrs) {
        this.minimumRotationSpeed = mrs;
    }

    public float getMinimumRotationSpeed() {
        return minimumRotationSpeed;
    }

    public float getRotationSpeed() {
        if (overStressed || lastStressApplied != stressImpact) {
            return 0;
        }
        return Math.abs(speed);
    }

    @Override
    public boolean isSpeedRequirementFulfilled() {
        return Math.abs(getSpeed()) >= minimumRotationSpeed;
    }



    // Equivalent to setRemoved. SmartBlockEntity makes it final, so we have to use the SBE-provided method
    @Override
    public void remove() {
        onDestroy();
        super.remove();
    }



    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.put("controller_pos", NBTHelperEI.posAsCompound(controllerPos));
        compound.put("linked_pool", NBTHelper.writeCompoundList(pool, NBTHelperEI::posAsCompound));
        compound.putFloat("configured_stress_impact", stressImpact);
        compound.putFloat("minimum_rotation_speed", minimumRotationSpeed);

        super.write(compound, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        pool = NBTHelper.readCompoundList(compound.getList("linked_pool", Tag.TAG_COMPOUND), NBTHelperEI::safeCompoundToPos);
        controllerPos = NBTHelperEI.safeCompoundToPos(compound.getCompound("controller_pos"));
        minimumRotationSpeed = compound.getFloat("minimum_rotation_speed");

        super.read(compound, registries, clientPacket);

        setConfiguredStressImpact(compound.getFloat("configured_stress_impact"));
    }

    @Override
    public @NotNull BlockEntity getInstance() {
        return this;
    }


    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return IMultiblockComponentBE.super.addToGoggleTooltip(tooltip, isPlayerSneaking);
    }

    @Override
    public boolean addToTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return IMultiblockComponentBE.super.addToTooltip(tooltip, isPlayerSneaking);
    }

    @Override
    public boolean containedFluidTooltip(List<Component> tooltip, boolean isPlayerSneaking,
                                         IFluidHandler handler) {
        return IMultiblockComponentBE.super.containedFluidTooltip(tooltip, isPlayerSneaking, handler);
    }

    @Override
    public int multiblockTooltipPriority(boolean isPlayerSneaking) {
        boolean notFastEnough = !isSpeedRequirementFulfilled() && getSpeed() != 0;
        boolean overstressTooltip = overStressed && AllConfigs.client().enableOverstressedTooltip.get();
        boolean isConsumingStress = (lastStressApplied != 0) && (stressImpact != 0);

        return ((notFastEnough || overstressTooltip) && isConsumingStress) ? 0 : -1;
    }

    @Override
    public List<Component> multiblockTooltip(boolean isPlayerSneaking) {
        List<Component> tooltip = new LinkedList<>();

        boolean notFastEnough = !isSpeedRequirementFulfilled() && getSpeed() != 0;
        boolean overstressTooltip = overStressed && AllConfigs.client().enableOverstressedTooltip.get();

        if (overstressTooltip) {
            CreateLang.translate("gui.stressometer.overstressed")
                    .style(GOLD)
                    .forGoggles(tooltip);
            Component hint = CreateLang.translateDirect("gui.contraptions.network_overstressed");
            List<Component> cutString = TooltipHelper.cutTextComponent(hint, FontHelper.Palette.GRAY_AND_WHITE);
            for (Component component : cutString)
                CreateLang.builder()
                        .add(component
                                .copy())
                        .forGoggles(tooltip);
        }

        if (notFastEnough) {
            CreateLang.translate("tooltip.speedRequirement")
                    .style(GOLD)
                    .forGoggles(tooltip);
            MutableComponent hint =
                    CreateLang.translateDirect("gui.contraptions.not_fast_enough", I18n.get(controller.getBlockState().getBlock()
                            .getDescriptionId()));
            List<Component> cutString = TooltipHelper.cutTextComponent(hint, FontHelper.Palette.GRAY_AND_WHITE);
            for (Component component : cutString)
                CreateLang.builder()
                        .add(component
                                .copy())
                        .forGoggles(tooltip);
        }

        return tooltip;
    }

    @Override
    public int multiblockGoggleTooltipPriority(boolean isPlayerSneaking) {
        return stressImpact == 0 ? -1 : 10;
    }

    @Override
    public List<Component> multiblockGoggleTooltip(boolean isPlayerSneaking) {
        List<Component> tooltip = new LinkedList<>();
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return tooltip;
    }


    @Override
    public void setController(MultiblockControllerBE mbc) {
        controller = mbc;
        controllerPos = mbc.getBlockPos();
    }

    @Override
    public void findController() {
        if (controllerPos == null) {
            return;
        }
        controller = (MultiblockControllerBE) level.getBlockEntity(controllerPos);
    }

    @Override
    public MultiblockControllerBE getController() {
        return controller;
    }
}
