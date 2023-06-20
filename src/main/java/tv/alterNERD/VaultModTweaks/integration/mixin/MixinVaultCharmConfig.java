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

import java.util.HashMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import iskallia.vault.config.Config;
import iskallia.vault.config.VaultCharmConfig;
import tv.alterNERD.VaultModTweaks.Configuration;
import tv.alterNERD.VaultModTweaks.VaultModTweaks;
import tv.alterNERD.VaultModTweaks.util.I18n;

/**
 * Changes the {@link iskallia.vault.config.VaultCharmConfig} class for
 * managing the Junk Management configuration.
 * 
 * Specifically, it changes the multiplier for the Vault Junk Upgrades.
 */
@Mixin(VaultCharmConfig.class)
public abstract class MixinVaultCharmConfig extends Config {
    @Shadow(remap = false)
    private HashMap<Integer, Integer> tierMultipliers;

    /**
     * Overrides the Vault Junk Upgrade multipliers whenever they are loaded.
     * 
     * @param oldConfigInstance
     */
    @Override
    protected void onLoad(Config oldConfigInstance) {
        super.onLoad(oldConfigInstance);
        if (Configuration.JUNKMGMT_ENABLED.get()) {
            int t1 = Configuration.JUNKMGMT_T1.get();
            int t2 = Configuration.JUNKMGMT_T2.get();
            int t3 = Configuration.JUNKMGMT_T3.get();
            int t4 = Configuration.JUNKMGMT_T4.get();
            VaultModTweaks.LOGGER.info(I18n.get("the_vault_tweaks.log.inject.junkmgmt.upgrades", t1, t2, t3, t4));
            tierMultipliers = new HashMap<Integer, Integer>(4);
            tierMultipliers.put(1, t1);
            tierMultipliers.put(2, t2);
            tierMultipliers.put(3, t3);
            tierMultipliers.put(4, t4);
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
