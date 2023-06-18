package tv.alterNERD.VaultModTweaks.integration.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import iskallia.vault.config.Config;
import iskallia.vault.config.UnidentifiedRelicFragmentsConfig;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.resources.ResourceLocation;
import tv.alterNERD.VaultModTweaks.Configuration;

@Mixin(UnidentifiedRelicFragmentsConfig.class)
public abstract class MixinUnidentifiedRelicFragmentsConfig extends Config {
    @Shadow
    private WeightedList<ResourceLocation> fragments;

    @Override
    public void onLoad(Config oldConfigInstance) {
        super.onLoad(oldConfigInstance);
        if (Configuration.FRAGMENT_WEIGHT_FIX.get()) {
            for (WeightedList.Entry<ResourceLocation> item : fragments) {
                item.weight = 2;
            }
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
