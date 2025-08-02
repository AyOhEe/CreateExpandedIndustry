package io.github.ayohee.expandedindustry.multiblock;

import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import io.github.ayohee.expandedindustry.register.EIBlocks;
import io.github.ayohee.expandedindustry.util.ConstSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MultiblockInventoryBlock extends AbstractMultiblockComponent<MultiblockInventoryBE> {
    public static final ConstSupplier<BlockState> MULTIBLOCK_INVENTORY = new ConstSupplier<>(EIBlocks.MULTIBLOCK_INVENTORY::getDefaultState);


    public MultiblockInventoryBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected float getShadeBrightness(BlockState p_308911_, BlockGetter p_308952_, BlockPos p_308918_) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }

    @Override
    public Class<MultiblockInventoryBE> getBlockEntityClass() {
        return MultiblockInventoryBE.class;
    }

    @Override
    public BlockEntityType<? extends MultiblockInventoryBE> getBlockEntityType() {
        return EIBlockEntityTypes.MULTIBLOCK_INVENTORY.get();
    }
}
