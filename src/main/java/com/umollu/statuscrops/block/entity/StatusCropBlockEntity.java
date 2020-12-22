package com.umollu.statuscrops.block.entity;

import com.umollu.statuscrops.StatusCropsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class StatusCropBlockEntity extends BlockEntity {
    private int statusType = 0;
    private int level = 0;
    private int duration = 1;
    private int balance = 5;

    public int getStatusType() {
        return statusType;
    }

    public void setStatusType(int statusType) {
        this.statusType = statusType;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public StatusCropBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(StatusCropsMod.STATUSCROP_BLOCK_ENTITY, blockPos, blockState);
    }

    public StatusCropBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(StatusCropsMod.STATUSCROP_BLOCK_ENTITY, blockPos, blockState);
    }

    public void setRandomStats(Random random) {
        level = random.nextFloat() < StatusCropsMod.config.statsChangeChance ? level + MathHelper.nextInt(random, -1, 1) : level;
        level = MathHelper.clamp(level, 0, 5);

        duration = random.nextFloat() < StatusCropsMod.config.statsChangeChance ? duration + MathHelper.nextInt(random, -1, 1) : duration;
        duration = MathHelper.clamp(duration, 1, 5);

        balance = random.nextFloat() < StatusCropsMod.config.statsChangeChance ? balance + MathHelper.nextInt(random, -1, 1): balance;
        balance = MathHelper.clamp(balance, 0, 10);
    }

    public void crossBreedingStats(StatusCropBlockEntity otherEntity, Random random) {
        level = random.nextFloat() < StatusCropsMod.config.geneticCrossChance ? otherEntity.level : level;
        level = MathHelper.clamp(level, 0, 5);

        duration = random.nextFloat() < StatusCropsMod.config.geneticCrossChance ? otherEntity.duration : duration;
        duration = MathHelper.clamp(duration, 1, 5);

        balance = random.nextFloat() < StatusCropsMod.config.geneticCrossChance ? otherEntity.balance: balance;
        balance = MathHelper.clamp(balance, 0, 10);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putInt("StatusType", statusType);
        tag.putInt("Level", level);
        tag.putInt("Duration", duration);
        tag.putFloat("Balance", balance);
        return tag;
    }

    @Override
    public void fromTag(CompoundTag compoundTag) {
        super.fromTag(compoundTag);
        statusType = compoundTag.getInt("StatusType");
        level = compoundTag.getInt("Level");
        duration = compoundTag.getInt("Duration");
        balance = compoundTag.getInt("Balance");
    }

}