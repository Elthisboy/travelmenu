package com.elthisboy.travelmenu;

import com.elthisboy.travelmenu.config.TravelConfigManager;
import com.elthisboy.travelmenu.item.ModItems;
import com.elthisboy.travelmenu.network.OpenTravelMenuPayload;
import com.elthisboy.travelmenu.network.TeleportRequestPayload;
import com.elthisboy.travelmenu.network.TravelServerNetworking;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class Travelmenu implements ModInitializer {

	public static final String MOD_ID = "travelmenu";

	@Override
	public void onInitialize() {

		ModItems.load();

		TravelConfigManager.load();

		PayloadTypeRegistry.playS2C().register(
				OpenTravelMenuPayload.ID,
				OpenTravelMenuPayload.CODEC
		);

		PayloadTypeRegistry.playC2S().register(
				TeleportRequestPayload.ID,
				TeleportRequestPayload.CODEC
		);

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			com.elthisboy.travelmenu.command.TravelCommands.register(dispatcher);
		});

		TravelServerNetworking.register();
	}
}