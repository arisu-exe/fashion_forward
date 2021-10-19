package io.github.fallOut015.fashion_forward.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import io.github.fallOut015.fashion_forward.MainFashionForward;
import io.github.fallOut015.fashion_forward.world.inventory.DyeingStationMenu;
import io.github.fallOut015.fashion_forward.world.item.WearableItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DyeingStationScreen extends AbstractContainerScreen<DyeingStationMenu> {
    private static final ResourceLocation BG_LOCATION = new ResourceLocation(MainFashionForward.MODID, "textures/gui/container/dyeing_station.png");
//    private ItemStack[] inputStacks;

    public DyeingStationScreen(DyeingStationMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        menu.registerUpdateListener(this::containerChanged);
//        this.inputStacks = new ItemStack[65];
//        for(int i = 0; i < 65; ++ i) {
//            this.inputStacks[i] = ItemStack.EMPTY;
//        }
        this.imageWidth = 205;
        this.imageHeight = 236;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float p_99100_, int p_99101_, int p_99102_) {
        this.renderBackground(poseStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BG_LOCATION);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(poseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        
        if(this.menu.getWearableSlot().hasItem() && WearableItem.hasDesign(this.menu.getWearableSlot().getItem())) {
            for(int s = 0; s < this.menu.getDyeSlots().length; ++ s) {
                if(WearableItem.getColorInDesignAtIndex(this.menu.getWearableSlot().getItem(), s) != null) {
                    BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                    float[] rgb = WearableItem.getRGBOfDec(WearableItem.getDecimalFromDye(WearableItem.getColorInDesignAtIndex(this.menu.getWearableSlot().getItem(), s)));
                    this.fillRect(bufferbuilder, this.menu.getDyeSlots()[s].x + i, this.menu.getDyeSlots()[s].y + j, (int) (18d * Minecraft.getInstance().getWindow().getGuiScale()), (int) (18d * Minecraft.getInstance().getWindow().getGuiScale()), (int) (rgb[0] * 255f), (int) (rgb[1] * 255f), (int) (rgb[2] * 255f), 128);
                }
            }
        }

        Lighting.setupForFlatItems();

        /*if (this.resultBannerPatterns != null && !this.hasMaxPatterns) {
            MultiBufferSource.BufferSource multibuffersource$buffersource = this.minecraft.renderBuffers().bufferSource();
            poseStack.pushPose();
            poseStack.translate((double)(i + 139), (double)(j + 52), 0.0D);
            poseStack.scale(24.0F, -24.0F, 1.0F);
            poseStack.translate(0.5D, 0.5D, 0.5D);
            float f = 0.6666667F;
            poseStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
            // Render wearable
            //this.wearableModel.xRot = 0.0F;
            //this.wearableModel.y = -32.0F;
            //BannerRenderer.renderPatterns(poseStack, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, this.wearableModel, ModelBakery.BANNER_BASE, true, this.resultBannerPatterns);
            poseStack.popPose();
            multibuffersource$buffersource.endBatch();
        } else if (this.hasMaxPatterns) {
            // render something for failing
            //this.blit(poseStack, i + slot3.x - 2, j + slot3.y - 2, this.imageWidth, 17, 17, 16);
        }*/

        Lighting.setupFor3DItems();
    }
    @Override
    protected void renderLabels(PoseStack poseStack, int x, int y) {
    }
    @Override
    public void render(PoseStack poseStack, int x, int y, float partialTicks) {
        super.render(poseStack, x, y, partialTicks);
        this.renderTooltip(poseStack, x, y);
    }

    private void containerChanged() {
        /*for(int i = 0; i < this.menu.getNumSlots(); ++ i) {
            this.inputStacks[i] = this.menu.getInputSlots()[i].getItem().copy();
        }*/

        ItemStack itemstack = this.menu.getResultSlot().getItem();
        if (itemstack.isEmpty()) {
            // don't render current
        } else {
            // render current
        }
    }
    // from ItemRenderer::fillRect
    private void fillRect(BufferBuilder bufferBuilder, int x, int y, int w, int h, int r, int g, int b, int a) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex((double)(x + 0), (double)(y + 0), 0.0D).color(r, g, b, a).endVertex();
        bufferBuilder.vertex((double)(x + 0), (double)(y + h), 0.0D).color(r, g, b, a).endVertex();
        bufferBuilder.vertex((double)(x + w), (double)(y + h), 0.0D).color(r, g, b, a).endVertex();
        bufferBuilder.vertex((double)(x + w), (double)(y + 0), 0.0D).color(r, g, b, a).endVertex();
        bufferBuilder.end();
        BufferUploader.end(bufferBuilder);
    }
}