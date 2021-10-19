package io.github.fallOut015.fashion_forward.stats;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

public class StatsFashionForward {
    public static final ResourceLocation INTERACT_WITH_SEWING_TABLE = makeCustomStat("interact_with_sewing_table", StatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_DYEING_STATION = makeCustomStat("interact_with_dying_station", StatFormatter.DEFAULT);

    private static ResourceLocation makeCustomStat(String id, StatFormatter statFormatter) {
        ResourceLocation resourcelocation = new ResourceLocation(id);
        Registry.register(Registry.CUSTOM_STAT, id, resourcelocation);
        Stats.CUSTOM.get(resourcelocation, statFormatter);
        return resourcelocation;
    }
}