package eu.reinalter.noah.useservername.mixin.xaero.worldmap;

import eu.reinalter.noah.useservername.UseServerName;
import eu.reinalter.noah.useservername.UseServerNameConfig;
import eu.reinalter.noah.useservername.client.UseServerNameClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.map.MapProcessor;

@Mixin(MapProcessor.class)
public abstract class MapProcessorMixin {
    @Inject(method = "getMainId(ILnet/minecraft/client/network/ClientPlayNetworkHandler;)Ljava/lang/String;",
            at = @At("RETURN"), cancellable = true)
    private void changedMainId(int version, ClientPlayNetworkHandler connection, CallbackInfoReturnable<String> cir) {
        if (UseServerNameConfig.HANDLER.instance().xaero_worldmap) {
            String oldReturnValue = cir.getReturnValue();
            Logger logger = UseServerName.getInstance().logger();

            if (oldReturnValue.startsWith("Multiplayer_")) {
                String serverId = UseServerNameClient.getInstance().getServerId();

                if (serverId != null) {
                    cir.setReturnValue(String.format("Multiplayer_%s", serverId));
                } else {
                    logger.warn("Server id is not known Xaero's Worldmap will not be redirected");
                    cir.setReturnValue(oldReturnValue);
                }
            }
        }
    }
}
