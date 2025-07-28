package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.infrastructure.config.AllConfigs;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.util.NBTHelperEI;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MultiblockKineticIOBE extends KineticBlockEntity implements IMultiblockComponentBE {
    MultiblockControllerBE controller = null;
    protected List<BlockPos> pool = new LinkedList<>();
    BlockPos controllerPos = null;
    boolean initialised = false;

    float stressImpact = 0;

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

    public void setStress(float stress) {
        stressImpact = stress;
        if(lastStressApplied != stress && hasNetwork()) {
            getOrCreateNetwork().updateStressFor(this, calculateStressApplied());
            networkDirty = true;
            notifyUpdate();
        }
    }

    public float getRotationSpeed() {
        if(overStressed || lastStressApplied != stressImpact)return 0;
        return Math.abs(speed);
    }


    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.put("controller_pos", NBTHelperEI.posAsCompound(controllerPos));
        compound.put("linked_pool", NBTHelper.writeCompoundList(pool, NBTHelperEI::posAsCompound));
        compound.putFloat("configured_stress_impact", stressImpact);

        super.write(compound, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        pool = NBTHelper.readCompoundList(compound.getList("linked_pool", Tag.TAG_COMPOUND), BlockEntity::getPosFromTag);
        controllerPos = getPosFromTag(compound.getCompound("controller_pos"));

        super.read(compound, registries, clientPacket);

        setStress(compound.getFloat("configured_stress_impact"));
    }

    @Override
    public BlockEntity getInstance() {
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
        boolean isConsumingStress = (lastStressApplied == 0) && (stressImpact == 0);

        return ((notFastEnough || overstressTooltip) && isConsumingStress) ? 0 : -1;
    }

    @Override
    public List<Component> multiblockTooltip(boolean isPlayerSneaking) {
        List<Component> tooltip = new LinkedList<>();
        super.addToTooltip(tooltip, isPlayerSneaking);
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
