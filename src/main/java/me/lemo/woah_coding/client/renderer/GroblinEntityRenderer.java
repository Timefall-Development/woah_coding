
package me.lemo.woah_coding.client.renderer;

import me.lemo.woah_coding.client.renderer.entity.model.GroblinEntityModel;
import me.lemo.woah_coding.client.renderer.entity.model.WoahCodingEntityModelLayers;
import me.lemo.woah_coding.client.renderer.entity.state.GroblinEntityRenderState;
import me.lemo.woah_coding.entity.GroblinEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GroblinEntityRenderer extends MobEntityRenderer<GroblinEntity, GroblinEntityRenderState, GroblinEntityModel> {
    public GroblinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GroblinEntityModel(context.getPart(WoahCodingEntityModelLayers.GROBLIN_ENTITY_MODEL_LAYER)), 0.2f);
    }

    private static final Identifier TEXTURE = Identifier.of("woah_coding", "textures/entity/groblin_entity.png");

    @Override
    public GroblinEntityRenderState createRenderState() {
        return new GroblinEntityRenderState();
    }

    @Override
    public void updateRenderState(GroblinEntity mobEntity, GroblinEntityRenderState bipedEntityRenderState, float tickDelta) {
        super.updateRenderState(mobEntity, bipedEntityRenderState, tickDelta);
        // If there was a saddled state or a change in the way that the mob looks

        // Type differences can be done using a Map from the EntityType<?> of the mob in question
        //   This is done using the first parameter as the reason and the second parameter as the result
    }

    @Override
    public Identifier getTexture(GroblinEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public void render(GroblinEntityRenderState livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int lightValue) {
        super.render(livingEntityRenderState, matrixStack, vertexConsumerProvider, lightValue);
        // If there was a saddled state or a change in the way that the mob looks
    }


}
