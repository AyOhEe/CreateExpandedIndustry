package io.github.ayohee.expandedindustry.content.blocks;

import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.IBE;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import io.github.ayohee.expandedindustry.content.blockentities.HighPressurePortBlockEntity;
import io.github.ayohee.expandedindustry.register.EIBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.MODID;

public class HighPressurePortBlock extends WrenchableBlock implements IBE<HighPressurePortBlockEntity> {
    public HighPressurePortBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(BlockStateProperties.FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AllShapes.EIGHT_VOXEL_POLE.get(state.getValue(BlockStateProperties.FACING).getAxis());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(BlockStateProperties.FACING, context.getClickedFace().getOpposite());
    }

    public static void generateBlockstate(DataGenContext<Block, HighPressurePortBlock> ctx, RegistrateBlockstateProvider prov) {
        ResourceLocation model = ResourceLocation.fromNamespaceAndPath(MODID, "block/high_pressure_port");
        prov.directionalBlock(ctx.get(), prov.models().getExistingFile(model));
    }

    @Override
    public Class<HighPressurePortBlockEntity> getBlockEntityClass() {
        return HighPressurePortBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends HighPressurePortBlockEntity> getBlockEntityType() {
        return EIBlockEntityTypes.HIGH_PRESSURE_PORT.get();
    }
}
