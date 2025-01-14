
package me.lemo.woah_coding.client.renderer.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

public class GroblinEntityModel extends BipedEntityModel<BipedEntityRenderState> {
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart body;
    private final ModelPart right_arm;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart left_arm;

    public GroblinEntityModel(ModelPart modelPart) {
        super(modelPart);
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.hat = this.head.getChild(EntityModelPartNames.HAT);
        this.body = root.getChild(EntityModelPartNames.BODY);
        this.right_arm = root.getChild(EntityModelPartNames.RIGHT_ARM);
        this.right_leg = root.getChild(EntityModelPartNames.RIGHT_LEG);
        this.left_leg = root.getChild(EntityModelPartNames.LEFT_LEG);
        this.left_arm = root.getChild(EntityModelPartNames.LEFT_ARM);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.0F, 0.0F));

        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(0, 16).cuboid(-2.5F, 0.0F, 0.0F, 5.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, 2.0F, 0.0262F, 0.0F, 0.0F));

        head.addChild(EntityModelPartNames.HAT_RIM, ModelPartBuilder.create().uv(0, 8).cuboid(-3.0F, -4.0F, -2.0F, 6.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 4).cuboid(-2.0F, 0.0F, 1.5F, 4.0F, 4.0F, 0.0F, new Dilation(0.0F))
                .uv(8, 1).mirrored().cuboid(2.0F, 0.0F, -1.5F, 0.0F, 4.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 0).mirrored().cuboid(-2.0F, 0.0F, -1.5F, 4.0F, 4.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
                .uv(8, 1).cuboid(-2.0F, 0.0F, -1.5F, 0.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(5, 0).cuboid(-2.0F, 3.0F, -1.5F, 4.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 19.0F, 0.0F));

        body.addChild("cube_r3", ModelPartBuilder.create().uv(3, 4).cuboid(-2.0F, -2.0F, -1.5F, 4.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(18, 3).cuboid(-0.75F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.25F, 19.5F, 0.0F, 0.0F, 0.0F, 0.0436F));

        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(23, 3).cuboid(-0.25F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.25F, 19.5F, 0.0F, 0.0F, 0.0F, -0.0436F));


        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(10, 3).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 22.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(8, 3).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 22.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }
}