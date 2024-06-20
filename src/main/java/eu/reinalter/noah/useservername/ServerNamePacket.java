package eu.reinalter.noah.useservername;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ServerNamePacket(String ServerName) implements CustomPayload {
    public static final CustomPayload.Id<ServerNamePacket> PACKET_ID = new CustomPayload.Id<>(Identifier.of(UseServerName.NAMESPACE, "servername"));
    public static final PacketCodec<PacketByteBuf, ServerNamePacket> PACKET_CODEC = PacketCodec.tuple(PacketCodecs.STRING, ServerNamePacket::ServerName, ServerNamePacket::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

}
