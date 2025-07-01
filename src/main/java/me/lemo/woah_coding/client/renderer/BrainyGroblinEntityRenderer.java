/*
package me.lemo.woah_coding.client.renderer;

import me.lemo.woah_coding.client.renderer.entity.model.GroblinEntityModel;
import me.lemo.woah_coding.client.renderer.entity.model.WoahCodingEntityModelLayers;
import me.lemo.woah_coding.entity.BrainyGroblinEntity;
import me.lemo.woah_coding.entity.GroblinEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BrainyGroblinEntityRenderer extends BipedEntityRenderer<BrainyGroblinEntity, BipedEntityRenderState, GroblinEntityModel> {
    private static final Identifier TEXTURE = Identifier.of("woah_coding", "textures/entity/groblin_entity.png");

    public BrainyGroblinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GroblinEntityModel(context.getPart(WoahCodingEntityModelLayers.GROBLIN_ENTITY_MODEL_LAYER)), 0.2f);
    }

    @Override
    public BipedEntityRenderState createRenderState() {
        return new BipedEntityRenderState();
    }

    @Override
    public Identifier getTexture(BipedEntityRenderState state) {
        return TEXTURE;
    }
}

 */
