package com.elthisboy.travelmenu.screen;

import com.elthisboy.travelmenu.config.TravelConfigManager;
import com.elthisboy.travelmenu.config.TravelDestination;
import com.elthisboy.travelmenu.economy.MoneyHelper;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.List;

public class TravelMenuScreenHandler extends ScreenHandler {

    private final SimpleInventory inventory = new SimpleInventory(9);
    private final PlayerEntity player;

    public TravelMenuScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ScreenHandlerType.GENERIC_9X1, syncId);

        this.player = playerInventory.player;

        createDestination(1, "market", Items.EMERALD);
        createDestination(3, "forest", Items.OAK_SAPLING);
        createDestination(5, "lake", Items.COD);
        createDestination(7, "defense", Items.IRON_SWORD);

        for (int i = 0; i < 9; i++) {
            this.addSlot(new ReadOnlySlot(inventory, i, 8 + i * 18, 20));
        }
    }

    private void createDestination(int slot, String id, Item icon) {

        TravelDestination dest = TravelConfigManager.getDestination(id);

        if (dest == null) return;

        int money = MoneyHelper.getMoney(player);

        if (money < dest.cost) {

            ItemStack locked = new ItemStack(Items.BARRIER);

            locked.set(
                    DataComponentTypes.CUSTOM_NAME,
                    Text.translatable("travel.destination.locked")
            );

            locked.set(
                    DataComponentTypes.LORE,
                    new LoreComponent(List.of(
                            Text.translatable("travel.menu.price", dest.cost)
                    ))
            );

            inventory.setStack(slot, locked);

        } else {

            ItemStack item = new ItemStack(icon);

            item.set(
                    DataComponentTypes.CUSTOM_NAME,
                    Text.translatable("travel.destination." + id)
            );

            item.set(
                    DataComponentTypes.LORE,
                    new LoreComponent(List.of(
                            Text.translatable("travel.menu.price", dest.cost)
                    ))
            );

            inventory.setStack(slot, item);
        }
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {

        if (slotIndex < 0 || slotIndex >= inventory.size()) return;

        if (slotIndex == 1) teleport(player, "market");
        if (slotIndex == 3) teleport(player, "forest");
        if (slotIndex == 5) teleport(player, "lake");
        if (slotIndex == 7) teleport(player, "defense");
    }

    private void teleport(PlayerEntity player, String id) {

        TravelDestination dest = TravelConfigManager.getDestination(id);

        if (dest == null) {
            player.sendMessage(Text.translatable("travel.chat.invalid_destination"), false);
            return;
        }

        if (!MoneyHelper.hasEnough(player, dest.cost)) {

            player.sendMessage(
                    Text.translatable("travel.chat.not_enough_money", dest.cost),
                    false
            );

            playFailEffects(player);
            return;
        }

        MoneyHelper.removeMoney(player, dest.cost);

        player.sendMessage(
                Text.translatable(
                        "travel.chat.teleporting",
                        Text.translatable("travel.destination." + id),
                        dest.cost
                ),
                false
        );

        player.teleport(dest.x, dest.y, dest.z, true);

        playSuccessEffects(player);
    }

    private void playSuccessEffects(PlayerEntity player) {

        player.getWorld().playSound(
                null,
                player.getBlockPos(),
                SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                SoundCategory.PLAYERS,
                1.0f,
                1.0f
        );

        if (player.getWorld() instanceof ServerWorld serverWorld) {

            serverWorld.spawnParticles(
                    ParticleTypes.PORTAL,
                    player.getX(),
                    player.getY() + 1,
                    player.getZ(),
                    40,
                    0.5,
                    0.5,
                    0.5,
                    0.2
            );
        }
    }

    private void playFailEffects(PlayerEntity player) {

        player.getWorld().playSound(
                null,
                player.getBlockPos(),
                SoundEvents.ENTITY_VILLAGER_NO,
                SoundCategory.PLAYERS,
                1.0f,
                1.0f
        );

        if (player.getWorld() instanceof ServerWorld serverWorld) {

            serverWorld.spawnParticles(
                    ParticleTypes.SMOKE,
                    player.getX(),
                    player.getY() + 1,
                    player.getZ(),
                    12,
                    0.3,
                    0.3,
                    0.3,
                    0.01
            );
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }
}