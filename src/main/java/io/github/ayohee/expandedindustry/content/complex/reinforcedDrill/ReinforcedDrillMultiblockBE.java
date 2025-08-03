package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import io.github.ayohee.expandedindustry.content.blockentities.HardenedStoneBlockEntity;
import io.github.ayohee.expandedindustry.multiblock.*;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.register.EIItems;
import io.github.ayohee.expandedindustry.util.NBTHelperEI;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public class ReinforcedDrillMultiblockBE extends AbstractMultiblockControllerBE {
    public static final int DRILL_TICKS = 60;
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

        if (isPowered() && !isFull()) {
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

    protected boolean isFull() {
        return !inventoryQueue.isEmpty();
    }

    protected void tickMining() {
        if (progress >= DRILL_TICKS) {
            //TODO mine from actual hardened stone blocks
            //HardenedStoneBlockEntity hsbe = findRandomHardenedStone();

            inventoryQueue.add(new ItemStack(EIBlocks.ERYTHRITE_BLOCK, 10));
            inventoryQueue.add(new ItemStack(EIItems.CRUSHED_RAW_COBALT.get(), 10));

            progress -= DRILL_TICKS;
            setChanged();
        } else {
            progress += 1;
        }
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

    //TODO own tooltip. overriding goggle tooltip isn't great.
}
