package com.umollu.statuscrops.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.umollu.statuscrops.StatusCropsMod;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonSerializer;

public class RandomChanceConfigAwareLootCondition implements LootCondition {
    private static final RandomChanceConfigAwareLootCondition INSTANCE = new RandomChanceConfigAwareLootCondition();

    private RandomChanceConfigAwareLootCondition() {
    }

    public LootConditionType getType() {
        return StatusCropsMod.RANDOM_CHANCE_CONFIG_AWARE;
    }

    public boolean test(LootContext lootContext) {
        return lootContext.getRandom().nextFloat() < StatusCropsMod.config.seedsDropChance;
    }

    public static LootCondition.Builder builder() {
        return () -> {
            return INSTANCE;
        };
    }

    public static class Serializer implements JsonSerializer<RandomChanceConfigAwareLootCondition> {
        public void toJson(JsonObject jsonObject, RandomChanceConfigAwareLootCondition randomChanceConfigAwareLootCondition, JsonSerializationContext jsonSerializationContext) {
        }

        public RandomChanceConfigAwareLootCondition fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return RandomChanceConfigAwareLootCondition.INSTANCE;
        }
    }
}
