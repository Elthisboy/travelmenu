package com.elthisboy.travelmenu.network;

import com.elthisboy.travelmenu.Travelmenu;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record OpenTravelMenuPayload() implements CustomPayload {

    public static final Id<OpenTravelMenuPayload> ID =
            new Id<>(Identifier.of(Travelmenu.MOD_ID, "open_travel_menu"));

    public static final PacketCodec<RegistryByteBuf, OpenTravelMenuPayload> CODEC =
            PacketCodec.unit(new OpenTravelMenuPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new OpenTravelMenuPayload());
    }
}