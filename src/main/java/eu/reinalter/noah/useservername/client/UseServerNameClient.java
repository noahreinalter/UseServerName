package eu.reinalter.noah.useservername.client;

import eu.reinalter.noah.useservername.UseServerName;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;


public class UseServerNameClient implements ClientModInitializer {
    static UseServerNameClient instance;
    private String serverId;
    private Logger logger;

    @Override
    public void onInitializeClient() {
        instance = this;
        this.logger = UseServerName.getInstance().logger();
    }

    public static UseServerNameClient getInstance() {
        return instance;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
        this.logger.info(String.format("Server id is: %s", this.serverId));
    }
}
