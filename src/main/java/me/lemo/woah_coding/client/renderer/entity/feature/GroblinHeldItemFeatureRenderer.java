package me.lemo.woah_coding.client.renderer.entity.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.RotationAxis;

public class GroblinHeldItemFeatureRenderer<S extends ArmedEntityRenderState, M extends EntityModel<S> & ModelWithArms> extends HeldItemFeatureRenderer<S, M> {

    public GroblinHeldItemFeatureRenderer(FeatureRendererContext<S, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, S armedEntityRenderState, float f, float g) {
        super.render(matrixStack, vertexConsumerProvider, i, armedEntityRenderState, f, g);
    }

    @Override
    protected void renderItem(S entityState, ItemRenderState itemState, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (!itemState.isEmpty()) {
            // Apply custom transformations
            if (!entityState.leftHandItemState.isEmpty()) {
                matrices.translate(-0.1, 0.1, 0.0); // Adjust left hand position
            } else {
                matrices.translate(0.1, 0.1, 0.0);  // Adjust right hand position
            }
            super.renderItem(entityState, itemState, arm, matrices, vertexConsumers, light);
        }
    }
}