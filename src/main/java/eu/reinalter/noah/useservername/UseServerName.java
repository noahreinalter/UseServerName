package eu.reinalter.noah.useservername;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UseServerName implements ModInitializer {
    private static Logger LOGGER;
    public static final String NAMESPACE = "useservername";
    public static final Identifier SERVERNAME_PACKET_ID = new Identifier(NAMESPACE, "servername");
    static UseServerName instance;

    @Override
    public void onInitialize() {
        instance = this;
        LOGGER = LoggerFactory.getLogger(NAMESPACE);

        LOGGER.info("Started Use Server Name mod");

        this.setupServerEvent();
    }

    public static UseServerName getInstance() {
        return instance;
    }

    public Logger logger() {
        if (LOGGER == null) {
            throw new IllegalStateException("Logger not yet available");
        }

        return LOGGER;
    }

    private void setupServerEvent() {
        int id = Random.create().nextBetween(1, 255);
        ServerPlayConnectionEvents.INIT.register(((handler, server) -> {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeString(String.format("Minecraft-Server-%d", id));
            ServerPlayNetworking.send(handler.player, SERVERNAME_PACKET_ID, buf);
        }));
    }
}
