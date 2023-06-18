package tv.alterNERD.VaultModTweaks.integration.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import iskallia.vault.config.Config;
import iskallia.vault.config.CrystalBuddingConfig;
import tv.alterNERD.VaultModTweaks.Configuration;

@Mixin(CrystalBuddingConfig.class)
public abstract class MixinCrystalBuddingConfig extends Config {
    @Shadow
    private float maxSecondsBetweenGrowthUpdates;

    @Shadow
    private float minSecondsBetweenGrowthUpdates;

    @Override
    protected void onLoad(Config oldConfigInstance) {
        super.onLoad(oldConfigInstance);
        if (Configuration.BUDDING_ENABLED.get()) {
            this.maxSecondsBetweenGrowthUpdates = Configuration.BUDDING_MAX.get();
            this.minSecondsBetweenGrowthUpdates = Configuration.BUDDING_MIN.get();
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
