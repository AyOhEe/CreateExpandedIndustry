package io.github.ayohee.expandedindustry.multiblock;

import net.minecraft.world.level.block.entity.BlockEntity;

public interface IMultiblockComponentBE {
    BlockEntity getInstance();

    void setControllerReference(MultiblockControllerBE mbc);
}
