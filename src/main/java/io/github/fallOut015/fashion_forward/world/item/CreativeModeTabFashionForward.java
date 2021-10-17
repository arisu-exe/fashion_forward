package io.github.fallOut015.fashion_forward.world.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class CreativeModeTabFashionForward {
    public static final CreativeModeTab TAB_FASHION_FORWARD = new CreativeModeTab("fashion_forward") {
        public ItemStack makeIcon() {
            ItemStack topHat = new ItemStack(ItemsFashionForward.TOP_HAT.get());
            WearableItem.applyDye(topHat, DyeColor.BLACK, 0);
            WearableItem.applyDye(topHat, DyeColor.WHITE, 1);
            WearableItem.applyDye(topHat, DyeColor.BLACK, 2);
            return topHat;
        }
    };
}
