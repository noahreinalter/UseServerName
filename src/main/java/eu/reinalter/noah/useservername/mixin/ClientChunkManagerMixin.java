package eu.reinalter.noah.useservername.mixin;

import eu.reinalter.noah.useservername.client.UseServerNameClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientChunkManager.class, priority = 500)
public class ClientChunkManagerMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void useServerNameInit(ClientWorld world, int loadDistance, CallbackInfo ci) {
        ClientPlayNetworkHandler clientPlayNetworkHandler = ((ClientWorldAccessor) world).getNetworkHandler();

        if (clientPlayNetworkHandler != null) {
            ServerInfo serverInfo = clientPlayNetworkHandler.getServerInfo();
            if (serverInfo != null) {
                UseServerNameClient.getInstance().setServerId(serverInfo.name);
            }
        }
    }
}
