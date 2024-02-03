package eu.reinalter.noah.useservername.client;

import eu.reinalter.noah.useservername.EventManager;
import eu.reinalter.noah.useservername.UseServerName;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ObjectShare;
import org.slf4j.Logger;

public class UseServerNameClient implements ClientModInitializer {
    static UseServerNameClient instance;
    private static ObjectShare objectShare;
    private Logger logger;

    @Override
    public void onInitializeClient() {
        instance = this;
        this.logger = UseServerName.getInstance().logger();
        objectShare = FabricLoader.getInstance().getObjectShare();

        new EventManager();
    }

    public static UseServerNameClient getInstance() {
        return instance;
    }

    public String getServerId() {
        return (String) objectShare.get(String.format("%s:serverId", UseServerName.NAMESPACE));
    }

    public void setServerId(String serverId, boolean overwrite) {
        if (overwrite) {
            objectShare.put(String.format("%s:serverId", UseServerName.NAMESPACE), serverId);
        } else {
            objectShare.putIfAbsent(String.format("%s:serverId", UseServerName.NAMESPACE), serverId);
        }
        this.logger.info(String.format("Server id is: %s", serverId));
    }

    public void clearServerId() {
        objectShare.remove(String.format("%s:serverId", UseServerName.NAMESPACE));
    }
}
