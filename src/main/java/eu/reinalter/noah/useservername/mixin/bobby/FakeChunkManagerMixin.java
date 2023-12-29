package eu.reinalter.noah.useservername.mixin.bobby;

import de.johni0702.minecraft.bobby.FakeChunkManager;

import eu.reinalter.noah.useservername.UseServerName;
import eu.reinalter.noah.useservername.client.UseServerNameClient;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FakeChunkManager.class)
public class FakeChunkManagerMixin {
    @Inject(method = "getCurrentWorldOrServerName", at = @At("RETURN"), cancellable = true)
    private static void changedCurrentWorldOrServerName(CallbackInfoReturnable<String> cir) {
        String oldReturnValue = cir.getReturnValue();
        Logger logger = UseServerName.getInstance().logger();

        switch (oldReturnValue) {
            case "realms":
            case "unknown":
                logger.warn("Server id is not known Bobby will not be redirected");
                cir.setReturnValue(oldReturnValue);
            default:
                String serverId = UseServerNameClient.getInstance().getServerId();

                if (serverId != null) {
                    // logger.info(String.format("Bobby save data will be redirected to: %s",
                    // serverId));
                    cir.setReturnValue(serverId);
                } else {
                    logger.warn("Server id is not known Bobby will not be redirected");
                    cir.setReturnValue(oldReturnValue);
                }
        }

    }
}
