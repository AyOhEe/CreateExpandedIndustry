package io.github.ayohee.expandedindustry.multiblock;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.Block;

public abstract class MultiblockController <T extends MultiblockControllerBE> extends Block implements IBE<T> {
    public MultiblockController(Properties properties) {
        super(properties);
    }
}
