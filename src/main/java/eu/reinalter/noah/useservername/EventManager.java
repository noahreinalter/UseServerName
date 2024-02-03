package eu.reinalter.noah.useservername;

import eu.reinalter.noah.useservername.client.UseServerNameClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

@Environment(EnvType.CLIENT)
public class EventManager {
    public EventManager() {
        ClientPlayConnectionEvents.DISCONNECT.register(((handler, client) -> UseServerNameClient.getInstance().clearServerId()));
    }
}
