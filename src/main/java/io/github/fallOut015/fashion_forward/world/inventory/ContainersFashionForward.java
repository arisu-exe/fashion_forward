package io.github.fallOut015.fashion_forward.world.inventory;

import io.github.fallOut015.fashion_forward.MainFashionForward;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainersFashionForward {
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MainFashionForward.MODID);



    public static final RegistryObject<MenuType<SewingTableMenu>> SEWING_TABLE = CONTAINERS.register("sewing_table", () -> new MenuType<>(SewingTableMenu::new));
    public static final RegistryObject<MenuType<DyingStationMenu>> DYING_STATION = CONTAINERS.register("dying_station", () -> new MenuType<>(DyingStationMenu::new));



    public static void register(IEventBus bus) {
        CONTAINERS.register(bus);
    }
}
