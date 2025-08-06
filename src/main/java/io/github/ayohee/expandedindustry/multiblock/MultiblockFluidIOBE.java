package io.github.ayohee.expandedindustry.multiblock;

import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.util.NBTHelperEI;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class MultiblockFluidIOBE extends BlockEntity implements IMultiblockComponentBE {
    FluidTank inv;

    AbstractMultiblockControllerBE controller = null;
    BlockPos controllerPos = null;
    boolean initialised = false;

    boolean chunkUnloaded = false;

    public MultiblockFluidIOBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        inv = new FluidTank(1000);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                EIBlockEntityTypes.MULTIBLOCK_FLUID_IO.get(),
                (be, context) -> {
                    if (context == null || context == be.getBlockState().getValue(BlockStateProperties.FACING)) {
                        return be.inv;
                    }
                    return null;
                }
        );
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
        tag.put("inventory", inv.writeToNBT(registries, new CompoundTag()));

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        controllerPos = NBTHelperEI.safeCompoundToPos(tag.getCompound("controller_pos"));
        inv.readFromNBT(registries, tag.getCompound("inventory"));
        int overflow = inv.getFluidAmount() - inv.getCapacity();
        if (overflow > 0)
            inv.drain(overflow, IFluidHandler.FluidAction.EXECUTE);

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
        controller.addTank(this);
    }

    @Override
    public AbstractMultiblockControllerBE getController() {
        return controller;
    }
}