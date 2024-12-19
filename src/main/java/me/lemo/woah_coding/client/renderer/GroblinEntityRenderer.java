package me.lemo.woah_coding.client.renderer;

import me.lemo.woah_coding.client.renderer.entity.model.GroblinEntityModel;
import me.lemo.woah_coding.client.renderer.entity.model.WoahCodingEntityModelLayers;
import me.lemo.woah_coding.client.renderer.entity.state.GroblinEntityRenderState;
import me.lemo.woah_coding.entity.GroblinEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GroblinEntityRenderer extends MobEntityRenderer<GroblinEntity, GroblinEntityRenderState, GroblinEntityModel> {
    private static final Identifier TEXTURE = Identifier.of("woah_coding", "textures/entity/groblin_entity.png");

    public GroblinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GroblinEntityModel(context.getPart(WoahCodingEntityModelLayers.GROBLIN_ENTITY_MODEL_LAYER)), 0.5f);
    }


    @Override
    public Identifier getTexture(GroblinEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public GroblinEntityRenderState createRenderState() {
        return new GroblinEntityRenderState();
    }
}
