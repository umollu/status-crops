package com.umollu.statuscrops.block;

import com.umollu.statuscrops.block.entity.StatusCropBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatusCropBlock extends CropBlock implements BlockEntityProvider {
    public static final IntProperty STATUS_TYPE = IntProperty.of("status_type", 0, 4);

    public StatusCropBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(STATUS_TYPE, 0));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new StatusCropBlockEntity();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(AGE);
        stateManager.add(STATUS_TYPE);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (itemStack.hasTag()) {
            CompoundTag tag =  itemStack.getTag();
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof StatusCropBlockEntity) {
                ((StatusCropBlockEntity)blockEntity).setStatusType(tag.getInt("StatusType"));
                ((StatusCropBlockEntity)blockEntity).setLevel(tag.getInt("Level"));
                ((StatusCropBlockEntity)blockEntity).setDuration(tag.getInt("Duration"));
                ((StatusCropBlockEntity)blockEntity).setBalance(tag.getInt("Balance"));
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        whenMatures(world, pos, state, random);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        super.grow(world, random, pos, state);
        whenMatures(world, pos, state, random);
    }

    private void whenMatures(World world, BlockPos pos, BlockState state, Random random) {
        state = world.getBlockState(pos);
        if(this.isMature(state))
        {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof StatusCropBlockEntity) {

                world.setBlockState(pos, state.with(STATUS_TYPE, ((StatusCropBlockEntity) blockEntity).getStatusType()));

                List<Direction> validDirections = new ArrayList<>();

                for (Direction direction : Direction.Type.HORIZONTAL) {
                    BlockEntity blockEntity2 = world.getBlockEntity(pos.offset(direction));
                    if (blockEntity2 instanceof StatusCropBlockEntity) {
                        validDirections.add(direction);
                    }
                }

                if(validDirections.size() > 0){
                    Direction randomValidDirection = (Direction) Util.getRandom(validDirections.toArray(), random);
                    BlockEntity blockEntity2 = world.getBlockEntity(pos.offset(randomValidDirection));
                    ((StatusCropBlockEntity) blockEntity).crossBreedingStats((StatusCropBlockEntity)blockEntity2, random);
                }
                else {
                    ((StatusCropBlockEntity) blockEntity).setRandomStats(random);
                }
            }
        }
    }
}
