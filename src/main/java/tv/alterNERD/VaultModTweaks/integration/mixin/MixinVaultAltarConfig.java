package tv.alterNERD.VaultModTweaks.integration.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.google.gson.annotations.Expose;

import iskallia.vault.config.Config;
import iskallia.vault.config.VaultAltarConfig;
import tv.alterNERD.VaultModTweaks.Configuration;

@Mixin(VaultAltarConfig.class)
public abstract class MixinVaultAltarConfig extends Config {
    @Shadow
    @Expose
    public int INFUSION_TIME;

    @Override
    protected void onLoad(Config oldConfigInstance) {
        super.onLoad(oldConfigInstance);
        if (Configuration.VAULTAR_ENABLED.get()) {
            this.INFUSION_TIME = Configuration.VAULTAR_INFUSION_TIME.get();
        }
    }

    @Shadow
    @Override
    public String getName() {
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    @Shadow
    @Override
    protected void reset() {
        throw new UnsupportedOperationException("Unimplemented method 'reset'");
    }
    
}
