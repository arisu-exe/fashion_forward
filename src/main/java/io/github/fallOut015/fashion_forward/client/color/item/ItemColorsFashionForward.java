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
        ForgeRegistries.ITEMS.getValues().stream().filter(item -> item instanceof WearableItem).forEach(item -> event.getItemColors().register((itemStack, tintIndex) ->
            switch(WearableItem.getColor(itemStack, tintIndex)) {
                case WHITE -> 16383998;
                case ORANGE -> 16351261;
                case MAGENTA -> 13061821;
                case LIGHT_BLUE -> 3847130;
                case YELLOW -> 16701501;
                case LIME -> 8439583;
                case PINK -> 15961002;
                case GRAY -> 4673362;
                case LIGHT_GRAY -> 10329495;
                case CYAN -> 1481884;
                case PURPLE -> 8991416;
                case BLUE -> 3949738;
                case BROWN -> 8606770;
                case GREEN -> 6192150;
                case RED -> 11546150;
                case BLACK -> 1908001;
            }
        , item));
    }
}