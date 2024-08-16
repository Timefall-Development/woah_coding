package me.lemo.woah_coding.client;

import me.lemo.woah_coding.registry.WoahCodingBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class WoahCodingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(WoahCodingBlocks.FADING_CLOUD, RenderLayer.getTranslucent());
    }
}
