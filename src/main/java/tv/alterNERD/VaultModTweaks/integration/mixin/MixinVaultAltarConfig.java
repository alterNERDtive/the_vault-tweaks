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

import com.google.gson.annotations.Expose;

import iskallia.vault.config.Config;
import iskallia.vault.config.VaultAltarConfig;
import tv.alterNERD.VaultModTweaks.Configuration;
import tv.alterNERD.VaultModTweaks.VaultModTweaks;
import tv.alterNERD.VaultModTweaks.util.I18n;

/**
 * Changes the {@link iskallia.vault.config.VaultAltarConfig} class for
 * managing the Vault Altar configuration.
 * 
 * Specifically, it changes the infusion time.
 */
@Mixin(VaultAltarConfig.class)
public abstract class MixinVaultAltarConfig extends Config {
    @Shadow(remap = false)
    @Expose
    public int INFUSION_TIME;

    /**
     * Overrides the default Vault Altar infusion time whenever the config is
     * loaded.
     * 
     * @param oldConfigInstance
     */
    @Override
    protected void onLoad(Config oldConfigInstance) {
        super.onLoad(oldConfigInstance);
        if (Configuration.VAULTAR_ENABLED.get()) {
            int time = Configuration.VAULTAR_INFUSION_TIME.get();
            VaultModTweaks.LOGGER.info(I18n.get("the_vault_tweaks.log.inject.vaultar.infusion", time));
            this.INFUSION_TIME = time;
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
