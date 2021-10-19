package io.github.fallOut015.fashion_forward;

import io.github.fallOut015.fashion_forward.client.gui.screens.inventory.DyeingStationScreen;
import io.github.fallOut015.fashion_forward.client.gui.screens.inventory.SewingTableScreen;
import io.github.fallOut015.fashion_forward.sounds.SoundEventsFashionForward;
import io.github.fallOut015.fashion_forward.tags.ItemTagsFashionForward;
import io.github.fallOut015.fashion_forward.world.inventory.ContainersFashionForward;
import io.github.fallOut015.fashion_forward.world.item.ItemsFashionForward;
import io.github.fallOut015.fashion_forward.world.level.block.BlocksFashionForward;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MainFashionForward.MODID)
public class MainFashionForward {
    public static final String MODID = "fashion_forward";
    public static final Logger LOGGER = LogManager.getLogger();

    public MainFashionForward() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client);

        MinecraftForge.EVENT_BUS.register(this);

        BlocksFashionForward.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemsFashionForward.register(FMLJavaModLoadingContext.get().getModEventBus());
        ContainersFashionForward.register(FMLJavaModLoadingContext.get().getModEventBus());
        SoundEventsFashionForward.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void setup(final FMLCommonSetupEvent event) {
    }
    private void enqueueIMC(final InterModEnqueueEvent event) {
    }
    private void processIMC(final InterModProcessEvent event) {
    }

    private void client(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ContainersFashionForward.SEWING_TABLE.get(), SewingTableScreen::new);
            MenuScreens.register(ContainersFashionForward.DYEING_STATION.get(), DyeingStationScreen::new);
        });
    }


    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }
}