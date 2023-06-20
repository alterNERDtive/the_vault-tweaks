/**
 * Copyright 2023 alterNERDtive.
 * 
 * This file is part of Vault Mod Tweaks.
 * 
 * Vault Mod Tweaks is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Vault Mod Tweaks is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with Vault Mod Tweaks.  If not, see <https://www.gnu.org/licenses/>.
 */
package tv.alterNERD.VaultModTweaks.integration.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import iskallia.vault.config.Config;
import iskallia.vault.config.UnidentifiedRelicFragmentsConfig;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.resources.ResourceLocation;
import tv.alterNERD.VaultModTweaks.Configuration;
import tv.alterNERD.VaultModTweaks.VaultModTweaks;
import tv.alterNERD.VaultModTweaks.util.I18n;

/**
 * Changes the {@link iskallia.vault.config.UnidentifiedRelicFragmentsConfig}
 * class for managing the relic fragment configuration.
 * 
 * Specifically, fixes the weight for rolling #5 fragments.
 */
@Mixin(UnidentifiedRelicFragmentsConfig.class)
public abstract class MixinUnidentifiedRelicFragmentsConfig extends Config {
    @Shadow(remap = false)
    private WeightedList<ResourceLocation> fragments;

    /**
     * Dynamically sets all fragment weights to the same (2) when the
     * configuration is loaded.
     * 
     * @param oldConfigInstance
     */
    @Override
    public void onLoad(Config oldConfigInstance) {
        super.onLoad(oldConfigInstance);
        if (Configuration.FRAGMENT_WEIGHT_FIX.get()) {
            VaultModTweaks.LOGGER.info(I18n.get("the_vault_tweaks.log.inject.relicfragments"));
            for (WeightedList.Entry<ResourceLocation> item : fragments) {
                item.weight = 2;
            }
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
