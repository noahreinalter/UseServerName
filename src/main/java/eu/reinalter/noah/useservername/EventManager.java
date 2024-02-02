package eu.reinalter.noah.useservername;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public class EventManager {
    public EventManager() {
        ClientPlayConnectionEvents.DISCONNECT.register(((handler, client) -> {
            FabricLoader.getInstance().getObjectShare().remove(String.format("%s:serverId", UseServerName.NAMESPACE));
        }));
    }
}
