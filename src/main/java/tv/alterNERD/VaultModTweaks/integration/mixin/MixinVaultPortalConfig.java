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

import java.util.ArrayList;
import java.util.Arrays;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.google.gson.annotations.Expose;

import iskallia.vault.config.Config;
import iskallia.vault.config.VaultPortalConfig;
import tv.alterNERD.VaultModTweaks.Configuration;

/**
 * Changes the {@link iskallia.vault.config.VaultPortalConfig} class for
 * managing the Vault Portal configuration.
 * 
 * Specifically, it allows making a portal out of Modular Routers’ Template
 * Frame Blocks.
 */
@Mixin(VaultPortalConfig.class)
public abstract class MixinVaultPortalConfig extends Config {
    @Shadow(remap = false)
    @Expose
    public String[] VALID_BLOCKS;

    /**
     * Adds Template Frame Blocks to the list of valid Vault Portal blocks
     * whenever it is loaded.
     * 
     * @param oldConfigInstance
     */
    @Override
    protected void onLoad(Config oldConfigInstance) {
        super.onLoad(oldConfigInstance);
        if (Configuration.PORTAL_TEMPLATE_ENABLED.get()) {
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(VALID_BLOCKS));
            list.add("modularrouters:template_frame");
            VALID_BLOCKS = list.toArray(VALID_BLOCKS);
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
