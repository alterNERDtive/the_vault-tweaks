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
import iskallia.vault.config.VaultJewelCuttingConfig;
import iskallia.vault.config.VaultJewelCuttingConfig.JewelCuttingRange;
import tv.alterNERD.VaultModTweaks.Configuration;
import tv.alterNERD.VaultModTweaks.VaultModTweaks;
import tv.alterNERD.VaultModTweaks.util.I18n;

/**
 * Changes the {@link iskallia.vault.config.VaultJewelCuttingConfig} class for
 * managing the Jewel Cutting Station configuration.
 * 
 * Specifically, it changes the minimum and maximum size change and the chance
 * of failure.
 */
@Mixin(VaultJewelCuttingConfig.class)
public abstract class MixinVaultJewelCuttingConfig extends Config {
    @Shadow(remap = false)
    private float jewelCuttingModifierRemovalChance;

    @Shadow(remap = false)
    private JewelCuttingRange jewelCuttingRange;

    /**
     * Changes the minimum and maximum size change and the chance of failure
     * whenever the configuration is loaded.
     * 
     * @param oldConfigInstance
     */
    @Override
    protected void onLoad(Config oldConfigInstance) {
        super.onLoad(oldConfigInstance);
        if (Configuration.JEWELER_ENABLED.get()) {
            float chance = Configuration.JEWELER_CHANCE.get().floatValue();
            VaultModTweaks.LOGGER.info(I18n.get("the_vault_tweaks.log.inject.jewelcutting.failurechance", chance));
            this.jewelCuttingModifierRemovalChance = chance;
            int min = Configuration.JEWELS_MIN.get();
            int max = Configuration.JEWELS_MAX.get();
            VaultModTweaks.LOGGER.info(I18n.get("the_vault_tweaks.log.inject.jewelcutting.sizes", min, max));
            this.jewelCuttingRange = new JewelCuttingRange(min, max);
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
