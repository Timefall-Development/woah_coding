package me.lemo.woah_coding.client.renderer;

import me.lemo.woah_coding.client.renderer.entity.model.BrainyGroblinEntityModel;
import me.lemo.woah_coding.client.renderer.entity.model.GroblinEntityModel;
import me.lemo.woah_coding.client.renderer.entity.model.WoahCodingEntityModelLayers;
import me.lemo.woah_coding.client.renderer.entity.state.BrainyGroblinEntityRenderState;
import me.lemo.woah_coding.client.renderer.entity.state.GroblinEntityRenderState;
import me.lemo.woah_coding.entity.BrainyGroblinEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BrainyGroblinEntityRenderer extends MobEntityRenderer<BrainyGroblinEntity, GroblinEntityRenderState, GroblinEntityModel> {
    private static final Identifier TEXTURE = Identifier.of("woah_coding", "textures/entity/groblin_entity.png");

    public BrainyGroblinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GroblinEntityModel(context.getPart(WoahCodingEntityModelLayers.GROBLIN_ENTITY_MODEL_LAYER)), 0.2f);
    }

    @Override
    public GroblinEntityRenderState createRenderState() {
        return new GroblinEntityRenderState();
    }

    @Override
    public Identifier getTexture(GroblinEntityRenderState state) {
        return TEXTURE;
    }
@Override
    public void updateRenderState(BrainyGroblinEntity livingEntity, GroblinEntityRenderState livingEntityRenderState, float tickDelta) {
        super.updateRenderState(livingEntity, livingEntityRenderState, tickDelta);
    }

    @Override
    public void render(GroblinEntityRenderState livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int lightValue) {
        super.render(livingEntityRenderState, matrixStack, vertexConsumerProvider, lightValue);
    }
}