package io.github.ayohee.expandedindustry.content.blockentities;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.Supplier;

public class HardenedStoneBlockEntity extends BlockEntity implements IHaveGoggleInformation {
    protected int remainingResources = -1;


    protected final RichnessProfile profile;


    protected HardenedStoneBlockEntity(BlockEntityType<?> type,
                                       BlockPos pos,
                                       BlockState blockState,
                                       RichnessProfile profile) {
        super(type, pos, blockState);
        this.profile = profile;
    }

    public static BlockEntityBuilder.BlockEntityFactory<HardenedStoneBlockEntity> createNew(Supplier<RichnessProfile> profile) {
        return (t, p, b) -> new HardenedStoneBlockEntity(t, p, b, profile.get());
    }


    @Override
    public void onLoad() {
        //TODO this necessitates a command to set an area of hardened stone blocks to contain -1
        //     so that structures for worldgen can be appropriately set
        if (remainingResources == -1 && getLevel() != null && !getLevel().isClientSide()) {
            ServerLevel overworld = getLevel().getServer().getLevel(ServerLevel.OVERWORLD);
            if (overworld != null) {
                this.remainingResources = calculateInitialResourceCount(getBlockPos(), overworld.getSeed(), profile);
                getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
            }
        }
    }

    private int calculateInitialResourceCount(BlockPos pos, long seed, RichnessProfile profile) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        double originDistance = Math.sqrt((x*x) + (z*z));

        double alpha = (double) (profile.richnessAtThreshold - profile.minRichness) / profile.threshold;
        double beta = alpha / 10;

        double avgRichness;
        if (originDistance > profile.threshold) {
            avgRichness = profile.richnessAtThreshold + (beta * (originDistance - profile.threshold));
        } else {
            avgRichness = (alpha * originDistance) + profile.minRichness;
        }

        int localMinRichness = (int)(avgRichness * (1 - profile.spread));
        int localMaxRichness = (int)(avgRichness * (1 + profile.spread));
        int rangeSize = localMaxRichness - localMinRichness;

        // Deterministic W.R.T. seed
        return localMinRichness + Math.abs((int)((x * y * z * seed) % rangeSize));
    }


    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.remainingResources = tag.getInt("remaining_resources");
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("remaining_resources", this.remainingResources);
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
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        if (isPlayerSneaking) {
            tooltip.addLast(Component.literal("    Remaining resources: " + remainingResources));

            return true;
        }

        return false;
    }


    //https://www.desmos.com/calculator/mr4xmmfdv5
    public static class RichnessProfile {
        public ItemStack stack;
        public int minRichness = 5;
        public int richnessAtThreshold = 50;
        public int threshold = 10000;
        public float spread = 0.1f;

        public RichnessProfile(ItemStack stack, int minRichness, int richnessAtThreshold, int threshold, float spread){
            this.stack = stack;
            this.minRichness = minRichness;
            this.richnessAtThreshold = richnessAtThreshold;
            this.threshold = threshold;
            this.spread = spread;
        }
    }
}
