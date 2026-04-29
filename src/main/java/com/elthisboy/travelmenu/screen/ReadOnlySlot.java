package com.elthisboy.travelmenu.screen;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.entity.player.PlayerEntity;

public class ReadOnlySlot extends Slot {

    public ReadOnlySlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTakeItems(PlayerEntity player) {
        return false;
    }
}