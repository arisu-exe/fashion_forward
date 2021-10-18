package io.github.fallOut015.fashion_forward.sounds;

import io.github.fallOut015.fashion_forward.MainFashionForward;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundEventsFashionForward {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MainFashionForward.MODID);

    public static final RegistryObject<SoundEvent> UI_SEWING_TABLE_SELECT_PATTERN = SOUND_EVENTS.register("ui.loom.select_pattern", () -> new SoundEvent(new ResourceLocation(MainFashionForward.MODID, "ui.loom.select_pattern")));
    public static final RegistryObject<SoundEvent> UI_SEWING_TABLE_TAKE_RESULT = SOUND_EVENTS.register("ui.loom.take_result", () -> new SoundEvent(new ResourceLocation(MainFashionForward.MODID, "ui.loom.take_result")));

    public static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }
}
