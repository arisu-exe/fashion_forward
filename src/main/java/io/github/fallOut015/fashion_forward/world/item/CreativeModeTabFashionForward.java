package io.github.fallOut015.fashion_forward.world.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class CreativeModeTabFashionForward {
    public static final CreativeModeTab TAB_FASHION_FORWARD = new CreativeModeTab("fashion_forward") {
        public ItemStack makeIcon() {
            ItemStack topHat = new ItemStack(ItemsFashionForward.TOP_HAT.get());
            WearableItem.setColor(topHat, 0, DyeColor.BLACK);
            WearableItem.setColor(topHat, 1, DyeColor.WHITE);
            WearableItem.setColor(topHat, 2, DyeColor.BLACK);
            return topHat;
        }
    };
}