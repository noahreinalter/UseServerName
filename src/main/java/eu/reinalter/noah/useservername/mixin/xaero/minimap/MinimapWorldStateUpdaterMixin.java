package eu.reinalter.noah.useservername.mixin.xaero.minimap;

import eu.reinalter.noah.useservername.UseServerName;
import eu.reinalter.noah.useservername.UseServerNameConfig;
import eu.reinalter.noah.useservername.client.UseServerNameClient;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.hud.minimap.world.state.MinimapWorldStateUpdater;
import xaero.hud.path.XaeroPath;

@Mixin(MinimapWorldStateUpdater.class)
public abstract class MinimapWorldStateUpdaterMixin {
    @Inject(method = "getAutoRootContainerPath(I)Lxaero/hud/path/XaeroPath;", at = @At("RETURN"), cancellable = true, remap = false)
    private void getAutoRootContainerPath(int version, CallbackInfoReturnable<XaeroPath> cir) {
        if (UseServerNameConfig.HANDLER.instance().xaero_minimap) {
            XaeroPath oldReturnValue = cir.getReturnValue();
            Logger logger = UseServerName.getInstance().logger();

            if (oldReturnValue.toString().startsWith("Multiplayer_")) {
                String serverId = UseServerNameClient.getInstance().getServerId();

                if (serverId != null) {
                    cir.setReturnValue(XaeroPath.root(String.format("Multiplayer_%s", serverId)));
                } else {
                    logger.warn("Server id is not known Xaero's Minimap will not be redirected");
                    cir.setReturnValue(oldReturnValue);
                }
            }
        }
    }
}
