package com.elthisboy.travelmenu.economy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CurrencyHelper {

    public static boolean tryPay(PlayerEntity player, int cost) {

        int count = 0;

        for (ItemStack stack : player.getInventory().main) {

            if (stack.getItem() == Items.EMERALD) {
                count += stack.getCount();
            }

        }

        if (count < cost) return false;

        int remaining = cost;

        for (ItemStack stack : player.getInventory().main) {

            if (stack.getItem() == Items.EMERALD) {

                int remove = Math.min(stack.getCount(), remaining);

                stack.decrement(remove);

                remaining -= remove;

                if (remaining <= 0) break;
            }
        }

        return true;
    }
}