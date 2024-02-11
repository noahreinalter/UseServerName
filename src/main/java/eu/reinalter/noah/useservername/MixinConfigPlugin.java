package eu.reinalter.noah.useservername;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinConfigPlugin implements IMixinConfigPlugin {

    private final boolean hasBobby = FabricLoader.getInstance().isModLoaded("bobby");
    private final boolean hasXaeroMinimap = FabricLoader.getInstance().isModLoaded("xaerominimap");
    private final boolean hasXaeroWorldmap = FabricLoader.getInstance().isModLoaded("xaeroworldmap");

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains(".bobby.")) {
            return hasBobby;
        }
        if (mixinClassName.contains(".xaero.minimap.")) {
            return hasXaeroMinimap;
        }
        if (mixinClassName.contains(".xaero.worldmap.")) {
            return hasXaeroWorldmap;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
