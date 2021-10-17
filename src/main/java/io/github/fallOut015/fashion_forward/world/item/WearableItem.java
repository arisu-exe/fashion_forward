package io.github.fallOut015.fashion_forward.world.item;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class WearableItem extends Item {
    EquipmentSlot equipmentSlot;
    int patternSlots;

    public WearableItem(EquipmentSlot equipmentSlot, int patternSlots, Properties properties) {
        super(properties);

        this.equipmentSlot = equipmentSlot;
        this.patternSlots = patternSlots;
    }

    @Nullable
    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return this.equipmentSlot;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
        ItemStack itemstack1 = player.getItemBySlot(equipmentslot);
        if (itemstack1.isEmpty()) {
            player.setItemSlot(equipmentslot, itemstack.copy());
            if (!level.isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(this));
            }

            itemstack.setCount(0);
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public static void applyColor(ItemStack itemStack, DyeColor dyeColor, int i) {
        String data = itemStack.getOrCreateTag().getString("data");
        // format as so
        /*
        0:0,1:1,2:4,D:FORMATTED_DESIGN_STRING
         */
        itemStack.getOrCreateTag().putString("data", data);
    }
    public static DyeColor getColor(ItemStack itemStack, int i) {
        String data = itemStack.getOrCreateTag().getString("data");
        // read data and retrieve number from index i
        int dci = 5;
        return DyeColor.byId(dci);
    }
}

/*
FORMATTED_DESIGN_STRING
8x8 pixel art with any of the 16 dye colors and transparent
64 digit heptadecimal
0123456789ABCDEFG
(each dye in order of enum + transparent at end (G))
i.e.
EEEEEEEE EGGGGGGE ... (ignore whitespace, it'll all be squished together when being written)
 */