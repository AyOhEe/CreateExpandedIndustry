package io.github.ayohee.expandedindustry.content.complex.reinforcedDrill;

import io.github.ayohee.expandedindustry.content.blockentities.HardenedStoneBlockEntity;
import io.github.ayohee.expandedindustry.multiblock.*;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.register.EIFluids;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class ReinforcedDrillMultiblockBE extends AbstractMultiblockControllerBE {
    public static final int DRILL_TICKS = 100;
    public static final float RPM_SPEED_MULTIPLIER = 0.5f;

    protected float progress = 0;
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

        if (isPowered() && canTickMining() && hasViableHardenedStone()) {
            tickMining();
        }

        tryInsertInventory();
    }

    protected boolean isPowered() {
        MultiblockKineticIOBE kioBe = getFirstPoweredShaft();
        if (kioBe != null && !kioBe.isPowered()) {
            return false;
        }

        return true;
    }

    protected boolean canTickMining() {
        if (progress < DRILL_TICKS - 1) {
            return true;
        }

        if (inventories.isEmpty()) {
            return false;
        }
        MultiblockInventoryBE invBe = inventories.values().iterator().next();

        return invBe.contents().isEmpty() && inventoryQueue.isEmpty();
    }

    protected void tickMining() {
        if (progress >= DRILL_TICKS) {
            HardenedStoneBlockEntity hsbe = findRandomDeepHardenedStone();

            if (hsbe != null) {
                inventoryQueue.add(hsbe.drillFrom());
                progress -= DRILL_TICKS;
            }
        } else {
            progress += 1f * speedFactor();
        }

        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        setChanged();
    }

    protected float speedFactor() {
        MultiblockKineticIOBE kioBe = getFirstPoweredShaft();
        if (kioBe == null || !kioBe.isPowered()) {
            return 0;
        }

        return 1 + (RPM_SPEED_MULTIPLIER * ((kioBe.getRotationSpeed() - 64) / (256 - 64)));
    }

    private List<HardenedStoneBlockEntity> findAllTopLayerHardenedStone() {
        List<HardenedStoneBlockEntity> hsbes = new LinkedList<>();
        BlockPos floorPosition = getBlockPos().below(2);

        for (int xOffset = -2; xOffset < 3; xOffset++) {
            for (int zOffset = -2; zOffset < 3; zOffset++) {
                if (level.getBlockEntity(floorPosition.east(xOffset).south(zOffset)) instanceof HardenedStoneBlockEntity hsbe) {
                    hsbes.add(hsbe);
                }
            }
        }

        return hsbes;
    }

    private HardenedStoneBlockEntity findRandomDeepHardenedStone() {
        List<HardenedStoneBlockEntity> hsbes = findAllTopLayerHardenedStone();
        if (hsbes.isEmpty()) {
            return null;
        }

        return hsbes.get(level.random.nextInt(hsbes.size())).findLowest();
    }

    private boolean hasViableHardenedStone() {
        return !findAllTopLayerHardenedStone().isEmpty();
    }

    protected void tryInsertInventory() {
        if (inventoryQueue.isEmpty() || inventories.isEmpty()) {
            return;
        }

        MultiblockInventoryBE invBe = inventories.values().iterator().next();
        ItemStack additionalContents = inventoryQueue.getFirst();

        if (invBe.insert(additionalContents) == ItemStack.EMPTY) {
            inventoryQueue.removeFirst();
            setChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putFloat("mining_progress", progress);
        tag.put("inventory_queue", NBTHelper.writeCompoundList(inventoryQueue, (s) -> (CompoundTag) s.save(registries)));

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        progress = tag.getFloat("mining_progress");
        inventoryQueue = NBTHelper.readCompoundList(tag.getList("inventory_queue", Tag.TAG_COMPOUND), (t) -> ItemStack.parse(registries, (Tag) t).orElseThrow());

        super.loadAdditional(tag, registries);
    }

    public Predicate<FluidStack> addTank(MultiblockFluidIOBE multiblockFluidIOBE) {
        super.addTank(multiblockFluidIOBE);

        return e -> (e.is(EIFluids.LUBRICANT.getType()) || e.is(Fluids.WATER.getFluidType()));
    }


    @Override
    public int multiblockGoggleTooltipPriority(boolean isPlayerSneaking) {
        return 4;
    }

    @Override
    public List<Component> multiblockGoggleTooltip(boolean isPlayerSneaking) {
        List<Component> tooltip = new ArrayList<>();
        tooltip.add(Component.literal("    Mining progress: " + percentage(progress, DRILL_TICKS) + "%"));
        tooltip.add(Component.literal("        (Mining speed - " + decimalPlaces(speedFactor(), 2) + ")"));

        if (!hasViableHardenedStone()) {
            tooltip.add(Component.literal("        (No mineable stone - mining stopped)").withStyle(ChatFormatting.DARK_GRAY));
        } else if (!isPowered()) {
            tooltip.add(Component.literal("        (Underpowered - mining stopped)").withStyle(ChatFormatting.DARK_GRAY));
        } else if (!canTickMining()) {
            tooltip.add(Component.literal("        (Full - mining stopped)").withStyle(ChatFormatting.DARK_GRAY));
        }
        return tooltip;
    }

    public static int percentage(float a, float b) {
        return 10 * Math.round(10 * (a / b));
    }

    public static double decimalPlaces(double v, int p) {
        return (Math.round(v * Math.pow(10, p))) / Math.pow(10, p);
    }
}
