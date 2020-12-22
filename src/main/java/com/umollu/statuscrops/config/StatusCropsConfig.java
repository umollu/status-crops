package com.umollu.statuscrops.config;

import com.umollu.statuscrops.StatusCropsMod;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = StatusCropsMod.MOD_ID)
public class StatusCropsConfig implements ConfigData {
    public float seedsDropChance = 0.01f;
    public float statsChangeChance = 0.1f;
    public float geneticCrossChance = 0.2f;
}
