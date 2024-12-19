package me.lemo.woah_coding.client;

import me.lemo.woah_coding.client.renderer.GroblinEntityRenderer;
import me.lemo.woah_coding.client.renderer.entity.model.GroblinEntityModel;
import me.lemo.woah_coding.client.renderer.entity.model.WoahCodingEntityModelLayers;
import me.lemo.woah_coding.entity.type.WoahCodingEntityTypes;
import me.lemo.woah_coding.registry.WoahCodingBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class WoahCodingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(WoahCodingBlocks.FADING_CLOUD, RenderLayer.getTranslucent());

        EntityRendererRegistry.register(WoahCodingEntityTypes.GROBLIN_ENTITY_ENTITY_TYPE, GroblinEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(WoahCodingEntityModelLayers.GROBLIN_ENTITY_MODEL_LAYER, GroblinEntityModel::getTexturedModelData);
    }
}
