package io.github.fallOut015.fashion_forward.tags;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class ItemTagsFashionForward {
    public static final Tag.Named<Item> FABRIC;

    static {
        FABRIC = ItemTags.bind("fabric");
    }
}