package io.github.fallOut015.fashion_forward.client.model.geom;

import io.github.fallOut015.fashion_forward.MainFashionForward;
import io.github.fallOut015.fashion_forward.world.item.WearableItem;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModelLayersFashionForward {
    private static final Map<Item, ModelLayerLocation> MODEL_LAYER_LOCATIONS;
    private static final Map<ResourceLocation, Supplier<LayerDefinition>> LAYER_DEFINITIONS;

    static {
        MODEL_LAYER_LOCATIONS = new HashMap<>();

        LAYER_DEFINITIONS = new HashMap<>();
        LAYER_DEFINITIONS.put(new ResourceLocation(MainFashionForward.MODID, "top_hat"), ModelLayersFashionForward::createTopHatLayer);
        LAYER_DEFINITIONS.put(new ResourceLocation(MainFashionForward.MODID, "headphones"), ModelLayersFashionForward::createHeadphonesLayer);
    }

    public static ModelLayerLocation fromItem(Item item) {
        return MODEL_LAYER_LOCATIONS.get(item);
    }

    @SubscribeEvent
    public static void onEntityRenderersRegisterLayerDefinition(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        ForgeRegistries.ITEMS.getValues().stream().filter(item -> item instanceof WearableItem).forEach(item -> {
            ModelLayerLocation modelLayerLocation = new ModelLayerLocation(item.getRegistryName(), "main");
            MODEL_LAYER_LOCATIONS.put(item, modelLayerLocation);
            try {
                event.registerLayerDefinition(modelLayerLocation, LAYER_DEFINITIONS.get(item.getRegistryName()));
            } catch (final Exception exception) {
                MainFashionForward.LOGGER.error(exception.getMessage());
            }
        });
    }

    public static LayerDefinition createTopHatLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, -24.0F, 0.0F));
        bone.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -41.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        bone.addOrReplaceChild("line", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -34.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        bone.addOrReplaceChild("brim", CubeListBuilder.create().texOffs(0, 15).addBox(-6.0F, -33.0F, -6.0F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }
    public static LayerDefinition createHeadphonesLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition right = bone.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -6.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition band = bone.addOrReplaceChild("band", CubeListBuilder.create().texOffs(0, 13).addBox(-3.5F, -9.0F, -1.0F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-4.5F, -9.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 8).addBox(3.5F, -9.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition left = bone.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(4.0F, -6.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }
}