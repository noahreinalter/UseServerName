package eu.reinalter.noah.useservername;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
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

        UseServerNameConfig.HANDLER.load();
        UseServerNameConfig.HANDLER.save();

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
        ServerPlayConnectionEvents.INIT.register(((handler, server) -> {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeString(UseServerNameConfig.HANDLER.instance().serverName);
            ServerPlayNetworking.send(handler.player, SERVERNAME_PACKET_ID, buf);
        }));
    }
}
