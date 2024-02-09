package eu.reinalter.noah.useservername;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.CustomDescription;
import dev.isxander.yacl3.config.v2.api.autogen.StringField;
import dev.isxander.yacl3.config.v2.api.autogen.TickBox;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.util.Random;

public class UseServerNameConfig {
    private static final String CATEGORY = "useservername";
    @AutoGen(category = CATEGORY)
    @CustomDescription("yacl3.config.useservername:config.preferLocalName.description")
    @TickBox
    @SerialEntry(comment = "Client only")
    public boolean preferLocalName = false;

    @AutoGen(category = CATEGORY)
    @CustomDescription("yacl3.config.useservername:config.serverName.description")
    @StringField
    @SerialEntry(comment = "Servername that is send from the server")
    public String serverName = generateServerName();
    public static ConfigClassHandler<UseServerNameConfig> HANDLER = ConfigClassHandler.createBuilder(UseServerNameConfig.class)
            .id(Identifier.of(UseServerName.NAMESPACE, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("useservername.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    private String generateServerName() {
        Random random = new Random();
        int number = random.nextInt(255);

        return String.format("Minecraft-Server-%03d", number + 1);
    }
}
