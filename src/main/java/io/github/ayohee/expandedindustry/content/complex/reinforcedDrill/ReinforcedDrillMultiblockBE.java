package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import io.github.ayohee.expandedindustry.content.blockentities.HardenedStoneBlockEntity;
import io.github.ayohee.expandedindustry.multiblock.*;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.register.EIItems;
import io.github.ayohee.expandedindustry.util.NBTHelperEI;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public class ReinforcedDrillMultiblockBE extends AbstractMultiblockControllerBE {
    public static final int DRILL_TICKS = 100;
    public static final int DRILL_DIAMETER = 5;

    protected int progress = 0;
    protected List<ItemStack> inventoryQueue = new LinkedList<>();

    public ReinforcedDrillMultiblockBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public BiConsumer<LevelAccessor, BlockPos> deconstructFunction() {
        return ReinforcedDrillMultiblock::deconstructMBS;
    }

    @Override
    public void tick() {
        super.tick();

        if (isPowered() && canTickMining()) {
            tickMining();
        }

        tryInsertInventory();
    }

    protected boolean isPowered() {
        for (IMultiblockComponentBE be : components.values()) {
            if (be instanceof MultiblockKineticIOBE kioBe && !kioBe.isPowered()) {
                return false;
            }
        }

        return true;
    }

    protected boolean canTickMining() {
        if (progress < DRILL_TICKS - 1) {
            return true;
        }

        if (!inventories.values().iterator().hasNext()) {
            return false;
        }
        MultiblockInventoryBE invBe = inventories.values().iterator().next();

        return invBe.contents().isEmpty() && inventoryQueue.isEmpty();
    }

    protected void tickMining() {
        if (progress >= DRILL_TICKS) {
            //TODO mine from actual hardened stone blocks
            //HardenedStoneBlockEntity hsbe = findRandomHardenedStone();

            inventoryQueue.add(new ItemStack(EIBlocks.ERYTHRITE_BLOCK, 10));
            inventoryQueue.add(new ItemStack(EIItems.CRUSHED_RAW_COBALT.get(), 10));

            progress -= DRILL_TICKS;
        } else {
            progress += 1;
        }
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        setChanged();
    }

    protected void tryInsertInventory() {
        if (inventoryQueue.isEmpty() || !inventories.values().iterator().hasNext()) {
            return;
        }

        MultiblockInventoryBE invBe = inventories.values().iterator().next();
        ItemStack additionalContents = inventoryQueue.getFirst();
        ItemStack currentContents = invBe.contents();

        if (invBe.insert(additionalContents) == ItemStack.EMPTY) {
            inventoryQueue.removeFirst();
            setChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("mining_progress", progress);
        tag.put("inventory_queue", NBTHelper.writeCompoundList(inventoryQueue, (s) -> (CompoundTag) s.save(registries)));

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        progress = tag.getInt("mining_progress");
        inventoryQueue = NBTHelper.readCompoundList(tag.getList("inventory_queue", Tag.TAG_COMPOUND), (t) -> ItemStack.parse(registries, (Tag) t).orElseThrow());

        super.loadAdditional(tag, registries);
    }


    @Override
    public int multiblockGoggleTooltipPriority(boolean isPlayerSneaking) {
        return 4;
    }

    @Override
    public List<Component> multiblockGoggleTooltip(boolean isPlayerSneaking) {
        List<Component> tooltip = new ArrayList<>();
        tooltip.add(Component.literal("    Mining progress: " + percentage(progress, DRILL_TICKS) + "%"));

        if (!isPowered()) {
            tooltip.add(Component.literal("        (Underpowered - mining stopped)").withStyle(ChatFormatting.DARK_GRAY));
        } else if (!canTickMining()) {
            tooltip.add(Component.literal("        (Full - mining stopped)").withStyle(ChatFormatting.DARK_GRAY));
        }
        return tooltip;
    }

    public static int percentage(float a, float b) {
        return 10 * Math.round(10 * (a / b));
    }
}
