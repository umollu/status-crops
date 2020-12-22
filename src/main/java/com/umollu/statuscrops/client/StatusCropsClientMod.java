package com.umollu.statuscrops.client;

import com.umollu.statuscrops.StatusCropsMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.nbt.CompoundTag;

public class StatusCropsClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(StatusCropsMod.STATUSCROP, RenderLayer.getCutout());
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            int primaryColor = 0xffffff;
            int secondaryColor = 0xffffff;

            CompoundTag compoundTag = stack.getTag();
            if (compoundTag != null && compoundTag.contains("StatusType", 3)) {
                int statusType = compoundTag.getInt("StatusType");
                switch (statusType) {
                    case 0:
                        primaryColor = 0xf4b41b;
                        secondaryColor = 0xf47e1b;
                        break;
                    case 1:
                        primaryColor = 0xb6d53c;
                        secondaryColor = 0x71aa34;
                        break;
                    case 2:
                        primaryColor = 0xe6482e;
                        secondaryColor = 0xa93b3b;
                        break;
                    case 3:
                        primaryColor = 0xdff6f5;
                        secondaryColor = 0x8aebf1;
                        break;
                    case 4:
                        primaryColor = 0x28ccdf;
                        secondaryColor = 0x3978a8;
                        break;
                }
            }
            switch(tintIndex) {
                case 2:
                    return primaryColor;
                case 3:
                    return secondaryColor;
            }
            return 0xffffff;
        }, StatusCropsMod.STATUSFRUIT);
    }
}
