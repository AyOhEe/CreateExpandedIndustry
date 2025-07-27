package io.github.ayohee.expandedindustry.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class NBTHelperEI {
    //FIXME are we sure this isn't already implemented somewhere else?
    public static CompoundTag posAsCompound(BlockPos blockPos) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("x", blockPos.getX());
        tag.putInt("y", blockPos.getY());
        tag.putInt("z", blockPos.getZ());

        return tag;
    }
}
