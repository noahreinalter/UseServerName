package eu.reinalter.noah.useservername.mixin;

import eu.reinalter.noah.useservername.client.UseServerNameClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Duration;
import java.util.function.Consumer;

@Mixin(ClientLoginNetworkHandler.class)
public abstract class ClientLoginNetworkHandlerMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void clientLoginNetworkHandlerInit(ClientConnection connection, MinecraftClient client, ServerInfo serverInfo, Screen parentScreen, boolean newWorld, Duration worldLoadTime, Consumer<?> statusConsumer, CallbackInfo ci) {
        if (serverInfo != null) {
            UseServerNameClient.getInstance().setServerId(serverInfo.name, false);
        }
    }
}
