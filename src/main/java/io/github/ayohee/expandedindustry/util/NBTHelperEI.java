package io.github.ayohee.expandedindustry.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.Nullable;

public class NBTHelperEI {
    //FIXME are we sure this isn't already implemented somewhere else?
    public static CompoundTag posAsCompound(BlockPos blockPos) {
        CompoundTag tag = new CompoundTag();
        if (blockPos != null) {
            tag.putInt("x", blockPos.getX());
            tag.putInt("y", blockPos.getY());
            tag.putInt("z", blockPos.getZ());
        }

        return tag;
    }

    @Nullable
    public static BlockPos safeCompoundToPos(CompoundTag tag) {
        if (tag.contains("x", Tag.TAG_INT) && tag.contains("y", Tag.TAG_INT) && tag.contains("z", Tag.TAG_INT)) {
            return new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
        }
        return null;
    }
}
