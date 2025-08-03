package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.AllBlockEntityTypes;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.util.NBTHelperEI;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class MultiblockInventoryBE extends BlockEntity implements IMultiblockComponentBE {
    ItemStackHandler inv;

    AbstractMultiblockControllerBE controller = null;
    BlockPos controllerPos = null;
    boolean initialised = false;

    boolean chunkUnloaded = false;

    public MultiblockInventoryBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        inv = new ItemStackHandler();
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                EIBlockEntityTypes.MULTIBLOCK_INVENTORY.get(),
                (be, context) -> be.inv
        );
    }

    public ItemStack insert(ItemStack stack) {
        ItemStack result = inv.insertItem(0, stack, false);
        if (result == ItemStack.EMPTY) {
            setChanged();
        }
        return result;
    }

    public ItemStack contents() {
        return inv.getStackInSlot(0);
    }


    @Override
    public void tick() {
        if (!initialised) {
            if (!level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                findController();
            }
            initialised = true;
        }
    }

    public void findController() {
        if (controller != null || !hasLevel() || controllerPos == null) {
            return;
        }
        controller = (AbstractMultiblockControllerBE) level.getBlockEntity(controllerPos);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("controller_pos", NBTHelperEI.posAsCompound(controllerPos));
        tag.put("inventory", inv.serializeNBT(registries));

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        controllerPos = NBTHelperEI.safeCompoundToPos(tag.getCompound("controller_pos"));
        inv.deserializeNBT(registries, tag.getCompound("inventory"));

        super.loadAdditional(tag, registries);
    }


    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        chunkUnloaded = true;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (!chunkUnloaded)
            remove();
    }

    // A "remove"-like method is ideal, as, in SmartBlockEntity, it already checks whether the chunk is unloaded
    public void remove() {
        onDestroy();
        //super.remove();
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


    @Override
    public @NotNull BlockEntity getInstance() {
        return this;
    }

    @Override
    public void setController(AbstractMultiblockControllerBE mbc) {
        controller = mbc;
        controllerPos = mbc.getBlockPos();
        mbc.addInventory(this);
    }

    @Override
    public AbstractMultiblockControllerBE getController() {
        return controller;
    }
}
