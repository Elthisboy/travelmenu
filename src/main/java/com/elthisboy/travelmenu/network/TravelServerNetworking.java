package com.elthisboy.travelmenu.network;

import com.elthisboy.travelmenu.config.TravelConfigManager;
import com.elthisboy.travelmenu.config.TravelDestination;
import com.elthisboy.travelmenu.economy.MoneyHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class TravelServerNetworking {

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(TeleportRequestPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                var player = context.player();
                TravelDestination dest = TravelConfigManager.getDestination(payload.destinationId());

                if (dest == null) {
                    player.sendMessage(Text.translatable("travel.chat.invalid"), false);
                    return;
                }

                if (!MoneyHelper.hasEnough(player, dest.cost)) {
                    player.sendMessage(
                            Text.translatable("travel.chat.nomoney", dest.cost),
                            false
                    );

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
                                player.getY() + 1.0,
                                player.getZ(),
                                12,
                                0.3,
                                0.3,
                                0.3,
                                0.01
                        );
                    }

                    return;
                }

                MoneyHelper.removeMoney(player, dest.cost);

                player.sendMessage(
                        Text.translatable(
                                "travel.chat.teleport",
                                Text.translatable("travel.destination." + payload.destinationId()),
                                dest.cost
                        ),
                        false
                );

                player.teleport(
                        (ServerWorld) player.getWorld(),
                        dest.x,
                        dest.y,
                        dest.z,
                        player.getYaw(),
                        player.getPitch()
                );

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
                            player.getY() + 1.0,
                            player.getZ(),
                            40,
                            0.5,
                            0.5,
                            0.5,
                            0.2
                    );
                }
            });
        });


    }


}