package com.umollu.statuscrops.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class StatusFruitItem extends Item {
    public StatusFruitItem(Settings settings) {
        super(settings);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null && compoundTag.contains("StatusType", 3)) {
            int statusType = compoundTag.getInt("StatusType");
            return super.getTranslationKey(stack) + "." + statusType;
        }
        return super.getTranslationKey(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null && compoundTag.contains("StatusType", 3)) {
            int level = compoundTag.getInt("Level");
            int duration = compoundTag.getInt("Duration");
            int balance = compoundTag.getInt("Balance");
            tooltip.add(new LiteralText("Level: " + level));
            tooltip.add(new LiteralText("Duration: " + duration));
            tooltip.add(new LiteralText("Balance: " + balance));
        }
    }

    public ItemStack finishUsing(ItemStack itemStack, World world, LivingEntity livingEntity) {
        ItemStack itemStack2 = super.finishUsing(itemStack, world, livingEntity);
        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag != null && compoundTag.contains("StatusType", 3)) {
            int statusType = compoundTag.getInt("StatusType");
            int level = compoundTag.getInt("Level");
            int duration = compoundTag.getInt("Duration");
            int balance = compoundTag.getInt("Balance");

            int positiveDuration = MathHelper.floor(200 * duration * (balance / 10f));
            int negativeDuration = MathHelper.floor(200 * duration * (1f - (balance / 10f)));
            switch (statusType) {
                case 0:
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, positiveDuration, level));
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, negativeDuration, level));
                    break;
                case 1:
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, positiveDuration, level));
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, negativeDuration, level));
                    break;
                case 2:
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, positiveDuration, level));
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, negativeDuration, level));
                    break;
                case 3:
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, positiveDuration, level));
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, negativeDuration, level));
                    break;
                case 4:
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, positiveDuration, level));
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, negativeDuration, level));
                    break;
            }
        }
        return itemStack2;
    }
}
