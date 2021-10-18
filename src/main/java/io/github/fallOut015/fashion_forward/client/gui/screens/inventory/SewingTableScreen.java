package io.github.fallOut015.fashion_forward.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.fallOut015.fashion_forward.MainFashionForward;
import io.github.fallOut015.fashion_forward.sounds.SoundEventsFashionForward;
import io.github.fallOut015.fashion_forward.world.inventory.SewingTableMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class SewingTableScreen extends AbstractContainerScreen<SewingTableMenu> {
    private static final ResourceLocation BG_LOCATION = new ResourceLocation(MainFashionForward.MODID, "textures/gui/container/sewing_table.png");
    //private static final int BASE_PATTERN_INDEX = 1;
//    private static final int PATTERN_COLUMNS = 4;
//    private static final int PATTERN_ROWS = 4;
    private static final int TOTAL_PATTERN_ROWS = (SewingTableMenu.SewingPatterns.values().length - 1 + 4 - 1) / 4;
//    private static final int SCROLLER_WIDTH = 12;
//    private static final int SCROLLER_HEIGHT = 15;
//    private static final int PATTERN_IMAGE_SIZE = 14;
//    private static final int SCROLLER_FULL_HEIGHT = 56;
//    private static final int PATTERNS_X = 60;
//    private static final int PATTERNS_Y = 13;
//    private ModelPart wearableModel;
    @Nullable
//    private List<Pair<BannerPattern, DyeColor>> resultBannerPatterns;
    private ItemStack[] inputStacks;
    private float scrollOffs;
    private boolean scrolling;
    private int startIndex = 0;

    public SewingTableScreen(SewingTableMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        menu.registerUpdateListener(this::containerChanged);
        this.titleLabelY -= 2;
        this.inputStacks = new ItemStack[] {
            ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY
        };
    }

    @Override
    protected void init() {
        super.init();
        //this.wearableModel = this.minecraft.getEntityModels().bakeLayer(ModelLayers.BANNER).getChild("flag");
    }
    @Override
    public void render(PoseStack poseStack, int x, int y, float partialTicks) {
        super.render(poseStack, x, y, partialTicks);
        this.renderTooltip(poseStack, x, y);
    }
    @Override
    protected void renderBg(PoseStack poseStack, float p_99100_, int p_99101_, int p_99102_) {
        this.renderBackground(poseStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BG_LOCATION);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(poseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        for(int s = 0; s < this.menu.getInputSlots().length; ++ s) {
            Slot slot = this.menu.getInputSlots()[s];
            if(s < this.menu.getNumSlots()) {
                RenderSystem.setShaderTexture(0, BG_LOCATION);
                this.blit(poseStack, i + slot.x, j + slot.y, this.imageWidth + (s + 1) * 16, this.menu.getSelectedPatternIndex() * 16, 16, 16);
            } else {
                renderSlotDisabled(poseStack, i + slot.x, i + slot.y, this.getBlitOffset(), 0x000000);
            }
        }

        int k = (int)(41.0F * this.scrollOffs);
        boolean displayPatterns = true;
        this.blit(poseStack, i + 119, j + 13 + k,  this.scrolling ? 12 : 0, 214, 12, 15);
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

        if (displayPatterns) {
            int j2 = i + 60;
            int l2 = j + 13;
            int l = this.startIndex + 16;

            for(int i1 = this.startIndex; i1 < l && i1 < SewingTableMenu.SewingPatterns.values().length; ++i1) {
                int j1 = i1 - this.startIndex;
                int k1 = j2 + j1 % 4 * 16;
                int l1 = l2 + j1 / 4 * 16;
                RenderSystem.setShaderTexture(0, BG_LOCATION);
                int i2 = this.imageHeight;
                if (i1 == this.menu.getSelectedPatternIndex()) {
                    i2 += 16;
                } else if (p_99101_ >= k1 && p_99102_ >= l1 && p_99101_ < k1 + 16 && p_99102_ < l1 + 16) {
                    i2 += 32;
                }

                this.blit(poseStack, k1, l1, 0, i2, 16, 16);
                RenderSystem.setShaderTexture(0, BG_LOCATION);
                this.blit(poseStack, k1,l1,176,16 * i1, 16, 16);
                //this.renderPattern(i1, k1, l1);
            }
        }

        Lighting.setupFor3DItems();
    }
    @Override
    public boolean mouseClicked(double x, double y, int p_99085_) {
        this.scrolling = false;
        boolean displayPatterns = true;
        if (displayPatterns) {
            int i = this.leftPos + 60;
            int j = this.topPos + 13;
            int k = this.startIndex + 16;

            for(int l = this.startIndex; l < k; ++l) {
                int i1 = l - this.startIndex;
                double d0 = x - (double)(i + i1 % 4 * 14);
                double d1 = y - (double)(j + i1 / 4 * 14);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 14.0D && d1 < 14.0D && this.menu.clickMenuButton(this.minecraft.player, l)) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEventsFashionForward.UI_SEWING_TABLE_SELECT_PATTERN.get(), 1.0F));
                    this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, l);
                    return true;
                }
            }

            i = this.leftPos + 119;
            j = this.topPos + 9;
            if (x >= (double)i && x < (double)(i + 12) && y >= (double)j && y < (double)(j + 56)) {
                this.scrolling = true;
            }
        }

        return super.mouseClicked(x, y, p_99085_);
    }
    @Override
    public boolean mouseDragged(double p_99087_, double p_99088_, int p_99089_, double p_99090_, double p_99091_) {
        boolean displayPatterns = true;
        if (this.scrolling && displayPatterns) {
            int i = this.topPos + 13;
            int j = i + 56;
            this.scrollOffs = ((float)p_99088_ - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            int k = TOTAL_PATTERN_ROWS - 4;
            int l = (int)((double)(this.scrollOffs * (float)k) + 0.5D);
            if (l < 0) {
                l = 0;
            }

            this.startIndex = 1 + l * 4;
            return true;
        } else {
            return super.mouseDragged(p_99087_, p_99088_, p_99089_, p_99090_, p_99091_);
        }
    }
    @Override
    public boolean mouseScrolled(double p_99079_, double p_99080_, double p_99081_) {
        boolean displayPatterns = true;
        if (displayPatterns) {
            int i = TOTAL_PATTERN_ROWS - 4;
            this.scrollOffs = (float)((double)this.scrollOffs - p_99081_ / (double)i);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = 1 + (int)((double)(this.scrollOffs * (float)i) + 0.5D) * 4;
        }

        return true;
    }
    @Override
    protected boolean hasClickedOutside(double p_99093_, double p_99094_, int p_99095_, int p_99096_, int p_99097_) {
        return p_99093_ < (double)p_99095_ || p_99094_ < (double)p_99096_ || p_99093_ >= (double)(p_99095_ + this.imageWidth) || p_99094_ >= (double)(p_99096_ + this.imageHeight);
    }

    private void renderPattern(int p_99109_, int p_99110_, int p_99111_) {
        ItemStack itemstack = new ItemStack(Items.GRAY_BANNER);
        CompoundTag compoundtag = itemstack.getOrCreateTagElement("BlockEntityTag");
//        ListTag listtag = (new BannerPattern.Builder()).addPattern(BannerPattern.BASE, DyeColor.GRAY).addPattern(BannerPattern.values()[p_99109_], DyeColor.WHITE).toListTag();
//        compoundtag.put("Patterns", listtag);
        PoseStack posestack = new PoseStack();
        posestack.pushPose();
        posestack.translate((double)((float)p_99110_ + 0.5F), (double)(p_99111_ + 16), 0.0D);
        posestack.scale(6.0F, -6.0F, 1.0F);
        posestack.translate(0.5D, 0.5D, 0.0D);
        posestack.translate(0.5D, 0.5D, 0.5D);
        float f = 0.6666667F;
        posestack.scale(0.6666667F, -0.6666667F, -0.6666667F);
        MultiBufferSource.BufferSource multibuffersource$buffersource = this.minecraft.renderBuffers().bufferSource();
        //this.wearableModel.xRot = 0.0F;
        //this.wearableModel.y = -32.0F;
//        List<Pair<BannerPattern, DyeColor>> list = BannerBlockEntity.createPatterns(DyeColor.GRAY, BannerBlockEntity.getItemPatterns(itemstack));
        //BannerRenderer.renderPatterns(posestack, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, this.wearableModel, ModelBakery.BANNER_BASE, true, list);
        posestack.popPose();
        multibuffersource$buffersource.endBatch();
    }
    private void containerChanged() {
        for(int i = 0; i < this.menu.getNumSlots(); ++ i) {
            this.inputStacks[i] = this.menu.getInputSlots()[i].getItem().copy();
        }

        ItemStack itemstack = this.menu.getResultSlot().getItem();
        if (itemstack.isEmpty()) {
//            this.resultBannerPatterns = null;
        } else {
            //this.resultBannerPatterns = BannerBlockEntity.createPatterns(((BannerItem)itemstack.getItem()).getColor(), BannerBlockEntity.getItemPatterns(itemstack));
        }

        /*ItemStack itemstack1 = this.menu.getBannerSlot().getItem();
        ItemStack itemstack2 = this.menu.getDyeSlot().getItem();
        ItemStack itemstack3 = this.menu.getPatternSlot().getItem();*/

        /*if (!ItemStack.matches(itemstack1, this.bannerStack) || !ItemStack.matches(itemstack2, this.dyeStack) || !ItemStack.matches(itemstack3, this.patternStack)) {
            this.displayPatterns = !itemstack1.isEmpty() && !itemstack2.isEmpty() && itemstack3.isEmpty() && !this.hasMaxPatterns;
        }*/

        /*this.bannerStack = itemstack1.copy();
        this.dyeStack = itemstack2.copy();
        this.patternStack = itemstack3.copy();*/
    }
    private static void renderSlotDisabled(PoseStack poseStack, int x, int y, int offset, int slotColor) {
        RenderSystem.disableDepthTest();
        RenderSystem.colorMask(true, true, true, false);
        fillGradient(poseStack, x, y, x + 16, y + 16, slotColor, slotColor, offset);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.enableDepthTest();
    }
}