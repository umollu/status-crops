package com.umollu.statuscrops;

import com.umollu.statuscrops.block.StatusCropBlock;
import com.umollu.statuscrops.block.entity.StatusCropBlockEntity;
import com.umollu.statuscrops.config.StatusCropsConfig;
import com.umollu.statuscrops.item.StatusFruitItem;
import com.umollu.statuscrops.item.StatusSeedsItem;
import com.umollu.statuscrops.loot.condition.RandomChanceConfigAwareLootCondition;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigManager;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetNbtLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class StatusCropsMod implements ModInitializer {
    public static final String MOD_ID = "statuscrops";

    public static final Block STATUSCROP = new StatusCropBlock(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
    public static final Item STATUSCROPS_SEEDS = new StatusSeedsItem(STATUSCROP, new FabricItemSettings());
    public static final FoodComponent STATUSFRUIT_FOOD_COMPONENT = (new FoodComponent.Builder()).snack().alwaysEdible().build();
    public static Item STATUSFRUIT;

    public static LootConditionType RANDOM_CHANCE_CONFIG_AWARE;

    private static final Identifier GRASS_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/grass");
    private static final Identifier TALL_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/tall_grass");
    private static final Identifier FERN_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/fern");
    private static final Identifier LARGE_FERN_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/large_fern");

    public static BlockEntityType<StatusCropBlockEntity> STATUSCROP_BLOCK_ENTITY;

    public static ConfigManager configManager;
    public static StatusCropsConfig config;

    @Override
    public void onInitialize() {

        RANDOM_CHANCE_CONFIG_AWARE = Registry.register(Registry.LOOT_CONDITION_TYPE, new Identifier("random_change_config_aware"), new LootConditionType(new RandomChanceConfigAwareLootCondition.Serializer()));
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "statuscrop"), STATUSCROP);
        STATUSCROP_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "statuscrop"), FabricBlockEntityTypeBuilder.create(StatusCropBlockEntity::new, STATUSCROP).build(null));
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "seeds"), STATUSCROPS_SEEDS);
        STATUSFRUIT = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fruit"), new StatusFruitItem(new FabricItemSettings().food(STATUSFRUIT_FOOD_COMPONENT)));
        configManager = (ConfigManager) AutoConfig.register(StatusCropsConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(StatusCropsConfig.class).getConfig();
        CompostingChanceRegistry.INSTANCE.add(STATUSCROPS_SEEDS, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(STATUSFRUIT, 0.65f);

        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (GRASS_LOOT_TABLE_ID.equals(id) ||
                TALL_LOOT_TABLE_ID.equals(id) ||
                FERN_LOOT_TABLE_ID.equals(id) ||
                LARGE_FERN_LOOT_TABLE_ID.equals(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .withCondition(RandomChanceConfigAwareLootCondition.builder().build())
                        .withEntry(ItemEntry.builder(STATUSCROPS_SEEDS).apply(SetNbtLootFunction.builder((CompoundTag) Util.make(new CompoundTag(), (compoundTag) -> {
                            compoundTag.putInt("StatusType", 0);
                            compoundTag.putInt("Level", 0);
                            compoundTag.putInt("Duration", 1);
                            compoundTag.putInt("Balance", 5);}))).build())
                        .withEntry(ItemEntry.builder(STATUSCROPS_SEEDS).apply(SetNbtLootFunction.builder((CompoundTag) Util.make(new CompoundTag(), (compoundTag) -> {
                            compoundTag.putInt("StatusType", 1);
                            compoundTag.putInt("Level", 0);
                            compoundTag.putInt("Duration", 1);
                            compoundTag.putInt("Balance", 5);}))).build())
                        .withEntry(ItemEntry.builder(STATUSCROPS_SEEDS).apply(SetNbtLootFunction.builder((CompoundTag) Util.make(new CompoundTag(), (compoundTag) -> {
                            compoundTag.putInt("StatusType", 2);
                            compoundTag.putInt("Level", 0);
                            compoundTag.putInt("Duration", 1);
                            compoundTag.putInt("Balance", 5);}))).build())
                        .withEntry(ItemEntry.builder(STATUSCROPS_SEEDS).apply(SetNbtLootFunction.builder((CompoundTag) Util.make(new CompoundTag(), (compoundTag) -> {
                            compoundTag.putInt("StatusType", 3);
                            compoundTag.putInt("Level", 0);
                            compoundTag.putInt("Duration", 1);
                            compoundTag.putInt("Balance", 5);}))).build())
                        .withEntry(ItemEntry.builder(STATUSCROPS_SEEDS).apply(SetNbtLootFunction.builder((CompoundTag) Util.make(new CompoundTag(), (compoundTag) -> {
                            compoundTag.putInt("StatusType", 4);
                            compoundTag.putInt("Level", 0);
                            compoundTag.putInt("Duration", 1);
                            compoundTag.putInt("Balance", 5);}))).build());
                supplier.withPool(poolBuilder.build());
            }
        });
    }
}
