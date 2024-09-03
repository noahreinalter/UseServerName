package eu.reinalter.noah.useservername.mixin.xaero.minimap;

import eu.reinalter.noah.useservername.UseServerName;
import eu.reinalter.noah.useservername.UseServerNameConfig;
import eu.reinalter.noah.useservername.client.UseServerNameClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.minimap.world.state.MinimapWorldStateUpdater;
import xaero.hud.path.XaeroPath;

@Mixin(MinimapWorldStateUpdater.class)
public abstract class MinimapWorldStateUpdaterMixin {
    @Inject(method = "getAutoRootContainerPath(ILnet/minecraft/client/network/ClientPlayNetworkHandler;Lxaero/hud/minimap/module/MinimapSession;)Lxaero/hud/path/XaeroPath;", at = @At("RETURN"), cancellable = true)
    private void convertWorldDimFilesToFolders(int version, ClientPlayNetworkHandler connection, MinimapSession session, CallbackInfoReturnable<XaeroPath> cir) {
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
