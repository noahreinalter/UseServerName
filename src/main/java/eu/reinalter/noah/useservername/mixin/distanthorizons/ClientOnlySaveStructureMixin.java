package eu.reinalter.noah.useservername.mixin.distanthorizons;

import com.seibel.distanthorizons.core.file.structure.ClientOnlySaveStructure;
import com.seibel.distanthorizons.core.wrapperInterfaces.minecraft.IMinecraftClientWrapper;
import eu.reinalter.noah.useservername.UseServerName;
import eu.reinalter.noah.useservername.UseServerNameConfig;
import eu.reinalter.noah.useservername.client.UseServerNameClient;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientOnlySaveStructure.class)
public abstract class ClientOnlySaveStructureMixin {
    @Final
    @Shadow(remap = false)
    private static IMinecraftClientWrapper MC_CLIENT;

    @Inject(method = "getServerFolderName", at = @At("HEAD"), cancellable = true, remap = false)
    private static void changedServerFolderName(CallbackInfoReturnable<String> cir) {
        if (!MC_CLIENT.connectedToReplay() && UseServerNameConfig.HANDLER.instance().distanthorizons) {
            Logger logger = UseServerName.getInstance().logger();
            String serverId = UseServerNameClient.getInstance().getServerId();

            if (serverId != null) {
                cir.setReturnValue(serverId);
            } else {
                logger.warn("Server id is not known Distant Horizons will not be redirected");
            }
        }
    }
}
