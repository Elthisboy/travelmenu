package com.elthisboy.travelmenu.client.screen;

import com.elthisboy.travelmenu.network.TeleportRequestPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class TravelClientActions {

    public static void requestTeleport(String destinationId) {
        ClientPlayNetworking.send(new TeleportRequestPayload(destinationId));
    }

}