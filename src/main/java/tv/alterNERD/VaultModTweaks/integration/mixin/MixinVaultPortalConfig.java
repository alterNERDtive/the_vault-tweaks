package tv.alterNERD.VaultModTweaks.integration.mixin;

import java.util.ArrayList;
import java.util.Arrays;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.google.gson.annotations.Expose;

import iskallia.vault.config.Config;
import iskallia.vault.config.VaultPortalConfig;

@Mixin(VaultPortalConfig.class)
public abstract class MixinVaultPortalConfig extends Config {
    @Shadow
    @Expose
    public String[] VALID_BLOCKS;

    @Override
    protected void onLoad(Config oldConfigInstance) {
        super.onLoad(oldConfigInstance);
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(VALID_BLOCKS));
        list.add("modularrouters:template_frame");
        VALID_BLOCKS = list.toArray(VALID_BLOCKS);
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
