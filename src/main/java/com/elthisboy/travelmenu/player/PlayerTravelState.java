package com.elthisboy.travelmenu.player;

import net.minecraft.entity.player.PlayerEntity;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerTravelState {

    private static final Set<String> unlocked = new HashSet<>();

    public static boolean isUnlocked(PlayerEntity player, String id) {
        return unlocked.contains(key(player, id));
    }

    public static void unlock(PlayerEntity player, String id) {
        unlocked.add(key(player, id));
    }

    private static String key(PlayerEntity player, String id) {
        UUID uuid = player.getUuid();
        return uuid.toString() + ":" + id;
    }
}