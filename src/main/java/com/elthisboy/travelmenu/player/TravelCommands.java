package com.elthisboy.travelmenu.command;

import com.elthisboy.travelmenu.config.TravelConfigManager;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class TravelCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(
                CommandManager.literal("updateDestinations")
                        .requires(source -> source.hasPermissionLevel(2))
                        .executes(context -> {

                            TravelConfigManager.load();

                            context.getSource().sendFeedback(
                                    () -> Text.literal("Travel destinations reloaded."),
                                    false
                            );

                            return 1;

                        })
        );

    }

}