package tv.alterNERD.VaultModTweaks.integration.mixin;

import java.util.HashMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import iskallia.vault.config.Config;
import iskallia.vault.config.ExpertisesGUIConfig;
import iskallia.vault.config.entry.SkillStyle;
import tv.alterNERD.VaultModTweaks.Configuration;

@Mixin(ExpertisesGUIConfig.class)
public abstract class MixinExpertisesGuiConfig extends Config {
    @Shadow
    private HashMap<String, SkillStyle> styles;

    @Override
    protected void onLoad(Config oldConfigInstance) {
        super.onLoad(oldConfigInstance);
        if (Configuration.FORTUNE_ENABLED.get()) {
            this.styles.remove("Fortunate");
        }
        if (Configuration.JEWELER_ENABLED.get()) {
            this.styles.remove("Jeweler");
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
