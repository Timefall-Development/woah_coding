/*
package me.lemo.woah_coding.client;


import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;

// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class GroblinModel<T extends BipedEntityRenderState> extends BipedEntityModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_arm;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart left_arm;

    public GroblinModel(ModelPart modelPart) {
        super(modelPart);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.right_arm = root.getChild("right_arm");
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
        this.left_arm = root.getChild("left_arm");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.0F, 0.0F));

        ModelPartData cube_r1 = head.addChild("cube_r1", ModelPartBuilder.create().uv(0, 16).cuboid(-2.5F, 0.0F, 0.0F, 5.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, 2.0F, 0.0262F, 0.0F, 0.0F));

        ModelPartData cube_r2 = head.addChild("cube_r2", ModelPartBuilder.create().uv(0, 8).cuboid(-3.0F, -4.0F, -2.0F, 6.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 4).cuboid(-2.0F, 0.0F, 1.5F, 4.0F, 4.0F, 0.0F, new Dilation(0.0F))
                .uv(8, 1).mirrored().cuboid(2.0F, 0.0F, -1.5F, 0.0F, 4.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 0).mirrored().cuboid(-2.0F, 0.0F, -1.5F, 4.0F, 4.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
                .uv(8, 1).cuboid(-2.0F, 0.0F, -1.5F, 0.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(5, 0).cuboid(-2.0F, 3.0F, -1.5F, 4.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 19.0F, 0.0F));

        ModelPartData cube_r3 = body.addChild("cube_r3", ModelPartBuilder.create().uv(3, 4).cuboid(-2.0F, -2.0F, -1.5F, 4.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(18, 3).cuboid(-0.75F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.25F, 19.5F, 0.0F, 0.0F, 0.0F, 0.0436F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(10, 3).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 22.0F, 0.0F));

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(8, 3).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 22.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(23, 3).cuboid(-0.25F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.25F, 19.5F, 0.0F, 0.0F, 0.0F, -0.0436F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(T bipedEntityRenderState) {
        super.setAngles(bipedEntityRenderState);
    }

    public ModelPart getBody() {
        return body;
    }

    @Override
    public ModelPart getHead() {
        return head;
    }

    @Override
    protected ModelPart getArm(Arm arm) {
        return arm == Arm.LEFT ? this.left_arm : this.right_arm;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        right_arm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        right_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        left_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        left_arm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
*/