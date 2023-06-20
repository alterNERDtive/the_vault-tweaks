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
import iskallia.vault.config.ExpertisesGUIConfig;
import iskallia.vault.config.entry.SkillStyle;
import tv.alterNERD.VaultModTweaks.Configuration;
import tv.alterNERD.VaultModTweaks.VaultModTweaks;
import tv.alterNERD.VaultModTweaks.util.I18n;

/**
 * Changes the {@link iskallia.vault.config.ExpertisesGUIConfig} class for
 * managing the configuration for the Expertises GUI.
 * 
 * Specifically, it removes the Jeweler and Fortunate expertises.
 */
@Mixin(ExpertisesGUIConfig.class)
public abstract class MixinExpertisesGuiConfig extends Config {
    @Shadow(remap = false)
    private HashMap<String, SkillStyle> styles;

    /**
     * Removes the Jeweler and Fortunate expertises on loading the configuration
     * file.
     */
    @Override
    protected void onLoad(Config oldConfigInstance) {
        super.onLoad(oldConfigInstance);
        if (Configuration.FORTUNE_ENABLED.get()) {
            VaultModTweaks.LOGGER.info(I18n.get("the_vault_tweaks.log.inject.expertise.fortunate"));
            this.styles.remove("Fortunate");
        }
        if (Configuration.JEWELER_ENABLED.get()) {
            VaultModTweaks.LOGGER.info(I18n.get("the_vault_tweaks.log.inject.expertise.jeweler"));
            this.styles.remove("Jeweler");
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
