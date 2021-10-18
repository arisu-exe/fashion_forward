package io.github.fallOut015.fashion_forward.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.fallOut015.fashion_forward.MainFashionForward;
import io.github.fallOut015.fashion_forward.world.inventory.DyingStationMenu;
import io.github.fallOut015.fashion_forward.world.inventory.SewingTableMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DyingStationScreen extends AbstractContainerScreen<DyingStationMenu> {
    private static final ResourceLocation BG_LOCATION = new ResourceLocation(MainFashionForward.MODID, "textures/gui/container/dying_station.png");
    private ItemStack[] inputStacks;

    public DyingStationScreen(DyingStationMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        menu.registerUpdateListener(this::containerChanged);
        this.titleLabelY -= 2;
        this.inputStacks = new ItemStack[65];
        for(int i = 0; i < 65; ++ i) {
            this.inputStacks[i] = ItemStack.EMPTY;
        }
    }

    @Override
    protected void renderBg(PoseStack poseStack, float p_99100_, int p_99101_, int p_99102_) {
        this.renderBackground(poseStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BG_LOCATION);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(poseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        for(int s = 0; s < this.menu.getDyeSlots().length; ++ s) {
            // if filled make background color of dye
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
            // don't render preview
        } else {
            // render preview
        }
    }
}