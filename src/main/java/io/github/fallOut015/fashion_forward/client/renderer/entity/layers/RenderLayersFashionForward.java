package io.github.fallOut015.fashion_forward.client.renderer.entity.layers;

import io.github.fallOut015.fashion_forward.world.item.WearableItem;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RenderLayersFashionForward {
    @SubscribeEvent
    public static void onEntityRenderersAddLayers(final EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(skinName -> {
            PlayerRenderer playerRenderer = event.getSkin(skinName);
            ForgeRegistries.ITEMS.getValues().stream().filter(item -> item instanceof WearableItem).forEach(item -> {
                playerRenderer.addLayer(new MultitextureLayer<>(item, playerRenderer, event.getEntityModels()));
            });
        });
    }
}