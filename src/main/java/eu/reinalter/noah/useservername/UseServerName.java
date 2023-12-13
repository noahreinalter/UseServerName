package eu.reinalter.noah.useservername;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UseServerName implements ModInitializer {
    private static Logger LOGGER;
    public static final String NAMESPACE = "useservername";
    static UseServerName instance;

    @Override
    public void onInitialize() {
        instance = this;
        LOGGER = LoggerFactory.getLogger(NAMESPACE);

        LOGGER.info("Started Use Server Name mod");
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
}
