package com.elthisboy.travelmenu.economy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ScoreAccess;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;

public class MoneyHelper {

    public static int getMoney(PlayerEntity player) {
        Scoreboard scoreboard = player.getScoreboard();
        ScoreboardObjective obj = scoreboard.getNullableObjective("money");

        if (obj == null) return 0;

        ScoreAccess score = scoreboard.getOrCreateScore(player, obj);
        return score.getScore();
    }

    public static boolean hasEnough(PlayerEntity player, int amount) {
        return getMoney(player) >= amount;
    }

    public static void removeMoney(PlayerEntity player, int amount) {
        Scoreboard scoreboard = player.getScoreboard();
        ScoreboardObjective obj = scoreboard.getNullableObjective("money");

        if (obj == null) return;

        ScoreAccess score = scoreboard.getOrCreateScore(player, obj);
        score.setScore(score.getScore() - amount);
    }
}