package eu.reinalter.noah.useservername.client;

import eu.reinalter.noah.useservername.EventManager;
import eu.reinalter.noah.useservername.UseServerName;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;

public class UseServerNameClient implements ClientModInitializer {
    static UseServerNameClient instance;
    private String serverId;
    private Logger logger;

    @Override
    public void onInitializeClient() {
        instance = this;
        this.logger = UseServerName.getInstance().logger();

        new EventManager();
    }

    public static UseServerNameClient getInstance() {
        return instance;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
        FabricLoader.getInstance().getObjectShare().put(String.format("%s:serverId", UseServerName.NAMESPACE), serverId);
        this.logger.info(String.format("Server id is: %s", this.serverId));
    }
}
