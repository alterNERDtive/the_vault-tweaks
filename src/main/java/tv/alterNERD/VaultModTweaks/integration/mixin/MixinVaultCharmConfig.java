package tv.alterNERD.VaultModTweaks.integration.mixin;

import java.util.HashMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import iskallia.vault.config.Config;
import iskallia.vault.config.VaultCharmConfig;
import tv.alterNERD.VaultModTweaks.Configuration;

@Mixin(VaultCharmConfig.class)
public abstract class MixinVaultCharmConfig extends Config {
    @Shadow(remap = false)
    private HashMap<Integer, Integer> tierMultipliers;

    @Override
    protected void onLoad(Config oldConfigInstance) {
        super.onLoad(oldConfigInstance);
        if (Configuration.JUNKMGMT_ENABLED.get()) {
            tierMultipliers = new HashMap<Integer, Integer>(4);
            tierMultipliers.put(1, Configuration.JUNKMGMT_T1.get());
            tierMultipliers.put(2, Configuration.JUNKMGMT_T2.get());
            tierMultipliers.put(3, Configuration.JUNKMGMT_T3.get());
            tierMultipliers.put(4, Configuration.JUNKMGMT_T4.get());
        }
    }

    @Shadow(remap = false)
    @Override
    public String getName() {
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    @Shadow(remap = false)
    @Override
    protected void reset() {
        throw new UnsupportedOperationException("Unimplemented method 'reset'");
    }
    
}
