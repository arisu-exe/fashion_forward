package io.github.fallOut015.fashion_forward.world.inventory;

import io.github.fallOut015.fashion_forward.sounds.SoundEventsFashionForward;
import io.github.fallOut015.fashion_forward.world.item.ItemsFashionForward;
import io.github.fallOut015.fashion_forward.world.item.WearableItem;
import io.github.fallOut015.fashion_forward.world.level.block.BlocksFashionForward;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.function.Supplier;

public class SewingTableMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    final DataSlot selectedSewingTablePatternIndex = DataSlot.standalone();
    Runnable slotUpdateListener = () -> {
    };
    private final Slot[] inputSlots;
    private final Slot resultSlot;
    long lastSoundTime;
    private final Container inputContainer = new SimpleContainer(4) {
        public void setChanged() {
            super.setChanged();
            SewingTableMenu.this.slotsChanged(this);
            SewingTableMenu.this.slotUpdateListener.run();
        }
    };
    private final Container outputContainer = new SimpleContainer(1) {
        public void setChanged() {
            super.setChanged();
            SewingTableMenu.this.slotUpdateListener.run();
        }
    };

    public SewingTableMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }
    public SewingTableMenu(int containerId, Inventory inventory, final ContainerLevelAccess containerLevelAccess) {
        super(MenuType.LOOM, containerId);
        this.access = containerLevelAccess;
        this.inputSlots = new Slot[4];
        for(int i = 0; i < this.inputSlots.length; ++ i) {
            this.inputSlots[i] = this.addSlot(new Slot(this.inputContainer, 0, 13, 26 + i * 24) {
                public boolean mayPlace(ItemStack itemStack) {
                    return itemStack.is(ItemTags.WOOL);
                }
            });
        }

        this.resultSlot = this.addSlot(new Slot(this.outputContainer, 0, 143, 58) {
            public boolean mayPlace(ItemStack p_39950_) {
                return false;
            }

            public void onTake(Player player, ItemStack itemStack) {
                for(int i = 0; i < SewingPatterns.values()[SewingTableMenu.this.selectedSewingTablePatternIndex.get()].get().getPatternSlots(); ++ i) {
                    SewingTableMenu.this.inputSlots[i].remove(1);
                }

                containerLevelAccess.execute((level, blockPos) -> {
                    long l = level.getGameTime();
                    if (SewingTableMenu.this.lastSoundTime != l) {
                        level.playSound((Player)null, blockPos, SoundEventsFashionForward.UI_SEWING_TABLE_TAKE_RESULT.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                        SewingTableMenu.this.lastSoundTime = l;
                    }
                });
                super.onTake(player, itemStack);
            }
        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }

        this.addDataSlot(this.selectedSewingTablePatternIndex);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, BlocksFashionForward.SEWING_TABLE.get());
    }

    public boolean clickMenuButton(Player player, int buttonId) {
        if (buttonId > 0 && buttonId <= SewingPatterns.values().length) {
            this.selectedSewingTablePatternIndex.set(buttonId);
            this.setupResultSlot();
            return true;
        } else {
            return false;
        }
    }

    public void slotsChanged(Container container) { // TODO
        ItemStack itemstack = this.bannerSlot.getItem();
        ItemStack itemstack1 = this.dyeSlot.getItem();
        ItemStack itemstack2 = this.patternSlot.getItem();
        ItemStack itemstack3 = this.resultSlot.getItem();
        if (itemstack3.isEmpty() || !itemstack.isEmpty() && !itemstack1.isEmpty() && this.selectedBannerPatternIndex.get() > 0 && (this.selectedBannerPatternIndex.get() < BannerPattern.COUNT - BannerPattern.PATTERN_ITEM_COUNT || !itemstack2.isEmpty())) {
            if (!itemstack2.isEmpty() && itemstack2.getItem() instanceof BannerPatternItem) {
                CompoundTag compoundtag = itemstack.getOrCreateTagElement("BlockEntityTag");
                boolean flag = compoundtag.contains("Patterns", 9) && !itemstack.isEmpty() && compoundtag.getList("Patterns", 10).size() >= 6;
                if (flag) {
                    this.selectedBannerPatternIndex.set(0);
                } else {
                    this.selectedBannerPatternIndex.set(((BannerPatternItem)itemstack2.getItem()).getBannerPattern().ordinal());
                }
            }
        } else {
            this.resultSlot.set(ItemStack.EMPTY);
            this.selectedBannerPatternIndex.set(0);
        }

        this.setupResultSlot();
        this.broadcastChanges();
    }

    public void registerUpdateListener(Runnable slotUpdateListener) {
        this.slotUpdateListener = slotUpdateListener;
    }

    public ItemStack quickMoveStack(Player player, int index) { // TODO
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == this.resultSlot.index) {
                if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != this.dyeSlot.index && index != this.bannerSlot.index && index != this.patternSlot.index) {
                if (itemstack1.getItem() instanceof BannerItem) {
                    if (!this.moveItemStackTo(itemstack1, this.bannerSlot.index, this.bannerSlot.index + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack1.getItem() instanceof DyeItem) {
                    if (!this.moveItemStackTo(itemstack1, this.dyeSlot.index, this.dyeSlot.index + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack1.getItem() instanceof BannerPatternItem) {
                    if (!this.moveItemStackTo(itemstack1, this.patternSlot.index, this.patternSlot.index + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 4 && index < 31) {
                    if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 31 && index < 40 && !this.moveItemStackTo(itemstack1, 4, 31, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, blockPos) -> this.clearContainer(player, this.inputContainer));
    }

    private void setupResultSlot() {
        if (this.selectedSewingTablePatternIndex.get() > 0) {
            WearableItem wearableItem = SewingPatterns.values()[this.selectedSewingTablePatternIndex.get()].get();
            ItemStack resultStack = new ItemStack(wearableItem);
            for(int i = 0; i < wearableItem.getPatternSlots(); ++ i) {
                WearableItem.setColor(resultStack, i, WearableItem.getDyeFromWool(this.inputSlots[i].getItem().getItem()));
            }
            if(!ItemStack.matches(resultStack, this.resultSlot.getItem())) {
                this.resultSlot.set(resultStack);
            }
        }
    }

    public enum SewingPatterns {
        TOP_HAT(ItemsFashionForward.TOP_HAT),
        HEADPHONES(ItemsFashionForward.HEADPHONES);

        LazyLoadedValue<Item> item;

        SewingPatterns(Supplier<Item> item) {
            this.item = new LazyLoadedValue<>(item);
        }

        WearableItem get() {
            return (WearableItem) this.item.get();
        }
    }
}