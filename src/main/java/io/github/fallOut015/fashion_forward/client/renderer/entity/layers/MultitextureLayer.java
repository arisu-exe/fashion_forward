package io.github.fallOut015.fashion_forward.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.fallOut015.fashion_forward.MainFashionForward;
import io.github.fallOut015.fashion_forward.client.model.MultitextureModel;
import io.github.fallOut015.fashion_forward.client.model.geom.ModelLayersFashionForward;
import io.github.fallOut015.fashion_forward.world.item.WearableItem;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.Deadmau5EarsLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class MultitextureLayer<T extends Player> extends RenderLayer<T, PlayerModel<T>> {
    private static final Map<Item, ResourceLocation> TEXTURE_MAP;

    static {
        TEXTURE_MAP = new HashMap<>();
    }

    private final WearableItem item;
    private final MultitextureModel model;

    public MultitextureLayer(Item item, RenderLayerParent<T, PlayerModel<T>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.item = (WearableItem) item;
        this.model = new MultitextureModel(entityModelSet.bakeLayer(ModelLayersFashionForward.fromItem(item)));
        TEXTURE_MAP.put(item, new ResourceLocation(MainFashionForward.MODID, "textures/models/" + item.getRegistryName().getPath() + ".png"));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, T player, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        ItemStack itemStack = player.getItemBySlot(this.item.getEquipmentSlot());
        if(itemStack.is(this.item)) {
            int packedOverlay = 0;
            for(int i = 0; i < ((WearableItem) itemStack.getItem()).getPatternSlots(); ++ i) {
                float[] textureDiffuseColors = WearableItem.getColor(itemStack, i).getTextureDiffuseColors();
                if(this.item.getEquipmentSlot().equals(EquipmentSlot.HEAD)) {
                    this.model.copyFrom(this.getParentModel().getHead());
                }
                this.model.renderPart(poseStack, i, multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(player))), packedLight, packedOverlay, textureDiffuseColors[0], textureDiffuseColors[1], textureDiffuseColors[2]);
            }
        }
    }
    @Override
    protected ResourceLocation getTextureLocation(T player) {
        return TEXTURE_MAP.get(this.item);
    }
}