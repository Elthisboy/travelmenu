package com.elthisboy.travelmenu;

import com.elthisboy.travelmenu.client.screen.TravelMenuScreen;
import com.elthisboy.travelmenu.network.OpenTravelMenuPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;

public class TravelmenuClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(OpenTravelMenuPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				MinecraftClient client = MinecraftClient.getInstance();
				client.setScreen(new TravelMenuScreen());
			});
		});
	}
}