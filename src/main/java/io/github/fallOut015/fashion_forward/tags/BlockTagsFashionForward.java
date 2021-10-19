package io.github.fallOut015.fashion_forward.tags;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class BlockTagsFashionForward {
    public static final Tag.Named<Block> FABRIC;

    static {
        FABRIC = BlockTags.bind("fabric");
    }
}