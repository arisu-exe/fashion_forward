package io.github.fallOut015.fashion_forward.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MultitextureModel extends Model {
    private final ModelPart bone;

    public MultitextureModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.bone = root.getChild("bone");
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
    }

    public void renderPart(PoseStack poseStack, int childIndex, VertexConsumer buffer, int packedLight, int packedOverlay, float r, float g, float b) {
        bone.getAllParts().toArray(ModelPart[]::new)[childIndex + 1].render(poseStack, buffer, packedLight, packedOverlay, r, g, b, 1.0f);
    }

    public void copyFrom(ModelPart modelPart) {
        this.bone.copyFrom(modelPart);
    }
}