package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import io.github.ayohee.expandedindustry.util.NBTHelperEI;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class MultiblockControllerBE extends BlockEntity implements IHaveGoggleInformation {
    List<BlockPos> _componentPositions = null;
    boolean initialised = false;

    Map<BlockPos, IMultiblockComponentBE> components = new HashMap<>();


    public MultiblockControllerBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public void tick() {
        if (!initialised) {
            if (!level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
            initialised = true;
        }
    }

    public void addComponent(IMultiblockComponentBE be) {
        components.put(be.getInstance().getBlockPos(), be);
        be.setControllerReference(this);
    }

    protected void findComponents() {
        if (_componentPositions == null) {
            return;
        }

        components = new HashMap<>();
        for (BlockPos pos : _componentPositions) {
            BlockEntity be = getLevel().getBlockEntity(pos);
            if (be instanceof IMultiblockComponentBE) {
                addComponent((IMultiblockComponentBE) be);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        findComponents();
        tag.put(
            "child_components",
            NBTHelper.writeCompoundList(components.entrySet(), e -> NBTHelperEI.posAsCompound(e.getKey()))
        );
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        _componentPositions = NBTHelper.readCompoundList(tag.getList("child_components", Tag.TAG_COMPOUND), BlockEntity::getPosFromTag);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        findComponents();
        tooltip.add(Component.literal("    Component count: " + components.size()));

        return true;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
