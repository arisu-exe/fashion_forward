package io.github.fallOut015.fashion_forward.world.item;

import net.minecraft.client.gui.screens.inventory.LoomScreen;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

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
    public static int getDecimalFromDye(DyeColor dyeColor) {
        return switch(dyeColor) {
            case WHITE -> 16383998;
            case ORANGE -> 16351261;
            case MAGENTA -> 13061821;
            case LIGHT_BLUE -> 3847130;
            case YELLOW -> 16701501;
            case LIME -> 8439583;
            case PINK -> 15961002;
            case GRAY -> 4673362;
            case LIGHT_GRAY -> 10329495;
            case CYAN -> 1481884;
            case PURPLE -> 8991416;
            case BLUE -> 3949738;
            case BROWN -> 8606770;
            case GREEN -> 6192150;
            case RED -> 11546150;
            case BLACK -> 1908001;
        };
    }
    @Nullable
    public static DyeColor getDyeFromWool(Item item) {
        Block block = ((BlockItem) item).getBlock();
        if(block.equals(Blocks.WHITE_WOOL)) {
            return DyeColor.WHITE;
        } else if(block.equals(Blocks.ORANGE_WOOL)) {
            return DyeColor.ORANGE;
        } else if(block.equals(Blocks.MAGENTA_WOOL)) {
            return DyeColor.MAGENTA;
        } else if(block.equals(Blocks.LIGHT_BLUE_WOOL)) {
            return DyeColor.LIGHT_BLUE;
        } else if(block.equals(Blocks.YELLOW_WOOL)) {
            return DyeColor.YELLOW;
        } else if(block.equals(Blocks.LIME_WOOL)) {
            return DyeColor.LIME;
        } else if(block.equals(Blocks.PINK_WOOL)) {
            return DyeColor.PINK;
        } else if(block.equals(Blocks.GRAY_WOOL)) {
            return DyeColor.GRAY;
        } else if(block.equals(Blocks.LIGHT_GRAY_WOOL)) {
            return DyeColor.LIGHT_GRAY;
        } else if(block.equals(Blocks.CYAN_WOOL)) {
            return DyeColor.CYAN;
        } else if(block.equals(Blocks.PURPLE_WOOL)) {
            return DyeColor.PURPLE;
        } else if(block.equals(Blocks.BLUE_WOOL)) {
            return DyeColor.BLUE;
        } else if(block.equals(Blocks.BROWN_WOOL)) {
            return DyeColor.BROWN;
        } else if(block.equals(Blocks.GREEN_WOOL)) {
            return DyeColor.GREEN;
        } else if(block.equals(Blocks.RED_WOOL)) {
            return DyeColor.RED;
        } else if(block.equals(Blocks.BLACK_WOOL)) {
            return DyeColor.BLACK;
        } else {
            return null;
        }
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