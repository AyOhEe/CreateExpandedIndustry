package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.util.NBTHelperEI;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
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

    public MultiblockKineticIOBE(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
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
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.put("linked_pool", NBTHelper.writeCompoundList(pool, NBTHelperEI::posAsCompound));

        super.write(compound, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        pool = NBTHelper.readCompoundList(compound.getList("linked_pool", Tag.TAG_COMPOUND), BlockEntity::getPosFromTag);

        super.read(compound, registries, clientPacket);
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
    public void setController(MultiblockControllerBE mbc) {
        controller = mbc;
    }

    @Override
    public MultiblockControllerBE getController() {
        return controller;
    }
}
