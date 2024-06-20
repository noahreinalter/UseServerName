package eu.reinalter.noah.useservername.client;

import eu.reinalter.noah.useservername.ServerNamePacket;
import eu.reinalter.noah.useservername.UseServerName;
import eu.reinalter.noah.useservername.UseServerNameConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ObjectShare;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public class UseServerNameClient implements ClientModInitializer {
    static UseServerNameClient instance;
    private static ObjectShare objectShare;
    private static final String BASE_DIRECTORY = FabricLoader.getInstance().getGameDir().toAbsolutePath().toString();
    private Logger logger;

    @Override
    public void onInitializeClient() {
        instance = this;
        this.logger = UseServerName.getInstance().logger();
        objectShare = FabricLoader.getInstance().getObjectShare();

        new EventManagerClient();
        this.registerReceiver();
    }

    public static UseServerNameClient getInstance() {
        return instance;
    }

    public String getServerId() {
        return (String) objectShare.get(String.format("%s:serverId", UseServerName.NAMESPACE));
    }

    public void setServerId(String serverId, boolean localName) {
        if (!checkIfPathIsSafe(serverId)) {
            this.logger.warn("Tried to set serverId to String with Path Traversal or invalid characters");
            return;
        }

        if (UseServerNameConfig.HANDLER.instance().preferLocalName == localName) {
            objectShare.put(String.format("%s:serverId", UseServerName.NAMESPACE), serverId);
        } else {
            objectShare.putIfAbsent(String.format("%s:serverId", UseServerName.NAMESPACE), serverId);
        }
        this.logger.info(String.format("Server id is: %s", objectShare.get(String.format("%s:serverId", UseServerName.NAMESPACE))));
    }

    public void clearServerId() {
        objectShare.remove(String.format("%s:serverId", UseServerName.NAMESPACE));
    }

    private void registerReceiver() {
        ClientConfigurationNetworking.registerGlobalReceiver(ServerNamePacket.PACKET_ID, (payload, context) -> {
            UseServerNameClient.getInstance().setServerId(payload.ServerName(), false);
        });
    }

    private boolean checkIfPathIsSafe(String path) {
        File file = new File(BASE_DIRECTORY, path);

        try {
            return file.getCanonicalPath().startsWith(BASE_DIRECTORY);
        } catch (IOException e) {
            return false;
        }
    }
}
