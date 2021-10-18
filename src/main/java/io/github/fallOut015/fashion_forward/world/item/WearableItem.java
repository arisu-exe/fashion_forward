package io.github.fallOut015.fashion_forward.world.item;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class WearableItem extends Item {
    EquipmentSlot equipmentSlot;
    DyeColor[] defaultColors;

    public WearableItem(EquipmentSlot equipmentSlot, DyeColor[] defaultColors, Properties properties) {
        super(properties);

        this.equipmentSlot = equipmentSlot;
        this.defaultColors = defaultColors;
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
    @Override
    public void fillItemCategory(CreativeModeTab creativeModeTab, NonNullList<ItemStack> itemStacks) {
        if(this.allowdedIn(creativeModeTab)) {
            ItemStack itemStack = new ItemStack(this);
            for(int i = 0; i < this.defaultColors.length; ++ i) {
                WearableItem.setColor(itemStack, i, this.defaultColors[i]);
            }
            itemStacks.add(itemStack);
        }
    }

    public int getPatternSlots() {
        return this.defaultColors.length;
    }
    public EquipmentSlot getEquipmentSlot() {
        return this.equipmentSlot;
    }

    public static void setColor(ItemStack itemStack, int tintIndex, DyeColor dyeColor) {
        CompoundTag data;
        if(itemStack.getOrCreateTag().contains("data")) {
            data = (CompoundTag) itemStack.getOrCreateTag().get("data");
        } else {
            data = new CompoundTag();
        }
        data.putInt(String.valueOf(tintIndex), dyeColor.getId());
        itemStack.getOrCreateTag().put("data", data);
    }
    public static DyeColor getColor(ItemStack itemStack, int tintIndex) {
        CompoundTag data = (CompoundTag) itemStack.getOrCreateTag().get("data");
        return DyeColor.byId(data.getInt(String.valueOf(tintIndex)));
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