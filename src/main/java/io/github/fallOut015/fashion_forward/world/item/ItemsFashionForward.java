package io.github.fallOut015.fashion_forward.world.item;

import io.github.fallOut015.fashion_forward.MainFashionForward;
import io.github.fallOut015.fashion_forward.world.level.block.BlocksFashionForward;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemsFashionForward {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MainFashionForward.MODID);

    public static final RegistryObject<Item> SEWING_TABLE = ITEMS.register("sewing_table", () -> new BlockItem(BlocksFashionForward.SEWING_TABLE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<Item> DYING_STATION = ITEMS.register("dying_station", () -> new BlockItem(BlocksFashionForward.DYING_STATION.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<Item> TOP_HAT = ITEMS.register("top_hat", () -> new WearableItem(EquipmentSlot.HEAD, new DyeColor[] { DyeColor.BLACK, DyeColor.WHITE, DyeColor.BLACK }, new Item.Properties().tab(CreativeModeTabFashionForward.TAB_FASHION)));
    public static final RegistryObject<Item> HEADPHONES = ITEMS.register("headphones", () -> new WearableItem(EquipmentSlot.HEAD, new DyeColor[] { DyeColor.BLACK, DyeColor.BLACK, DyeColor.BLACK }, new Item.Properties().tab(CreativeModeTabFashionForward.TAB_FASHION)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}