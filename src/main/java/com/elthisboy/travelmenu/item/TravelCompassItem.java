package com.elthisboy.travelmenu.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.item.Item;

public class TravelCompassItem extends Item{


    public TravelCompassItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient && player instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.networkHandler.sendPacket(
                    new net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket(0)
            );

            com.elthisboy.travelmenu.network.OpenTravelMenuPayload.send(serverPlayer);
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
