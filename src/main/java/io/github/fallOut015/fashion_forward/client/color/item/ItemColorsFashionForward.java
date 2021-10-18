package io.github.fallOut015.fashion_forward.client.color.item;

import io.github.fallOut015.fashion_forward.world.item.WearableItem;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ItemColorsFashionForward {
    @SubscribeEvent
    public static void onColorHandlerItem(final ColorHandlerEvent.Item event) {
        ForgeRegistries.ITEMS.getValues().stream().filter(item -> item instanceof WearableItem).forEach(item -> event.getItemColors().register((itemStack, tintIndex) -> WearableItem.getDecimalFromDye(WearableItem.getColor(itemStack, tintIndex)), item));
    }
}