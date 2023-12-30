package eu.reinalter.noah.useservername.mixin;

import eu.reinalter.noah.useservername.client.UseServerNameClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void clientPlayNetworkHandlerInit(MinecraftClient client, ClientConnection clientConnection, ClientConnectionState clientConnectionState, CallbackInfo ci) {
        ServerInfo serverInfo = ((ClientPlayNetworkHandler)(Object)this).getServerInfo();
        if (serverInfo != null) {
            UseServerNameClient.getInstance().setServerId(serverInfo.name);
        }
    }
}
