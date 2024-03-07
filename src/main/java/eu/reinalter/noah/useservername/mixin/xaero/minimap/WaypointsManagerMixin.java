package eu.reinalter.noah.useservername.mixin.xaero.minimap;

import eu.reinalter.noah.useservername.UseServerName;
import eu.reinalter.noah.useservername.client.UseServerNameClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.common.minimap.waypoints.WaypointsManager;

@Mixin(WaypointsManager.class)
public abstract class WaypointsManagerMixin {
    @Inject(method = "getMainContainer", at = @At("RETURN"), cancellable = true)
    private void changedMainContainer(boolean preIP6Fix, ClientPlayNetworkHandler connection, CallbackInfoReturnable<String> cir) {
        String oldReturnValue = cir.getReturnValue();
        Logger logger = UseServerName.getInstance().logger();

        if (oldReturnValue.startsWith("Multiplayer_")) {
            String serverId = UseServerNameClient.getInstance().getServerId();

            if (serverId != null) {
                cir.setReturnValue(String.format("Multiplayer_%s", serverId));
            } else {
                logger.warn("Server id is not known Xaero's Minimap will not be redirected");
                cir.setReturnValue(oldReturnValue);
            }
        }
    }
}
