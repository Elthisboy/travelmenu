package com.elthisboy.travelmenu.network;

import com.elthisboy.travelmenu.Travelmenu;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record TeleportRequestPayload(String destinationId) implements CustomPayload {

    public static final Id<TeleportRequestPayload> ID =
            new Id<>(Identifier.of(Travelmenu.MOD_ID, "teleport_request"));

    public static final PacketCodec<RegistryByteBuf, TeleportRequestPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING, TeleportRequestPayload::destinationId,
                    TeleportRequestPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}