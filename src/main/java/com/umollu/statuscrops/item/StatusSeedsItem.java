package com.umollu.statuscrops.item;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class StatusSeedsItem extends AliasedBlockItem {
    public StatusSeedsItem(Block block, Settings settings) {
        super(block, settings);
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
}
