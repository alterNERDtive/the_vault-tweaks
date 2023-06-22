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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import iskallia.vaultintegrations.altar.AltarConduitNode;
import iskallia.vaultintegrations.init.VIConfigs;
import tv.alterNERD.VaultModTweaks.Configuration;
import tv.alterNERD.VaultModTweaks.VaultModTweaks;
import tv.alterNERD.VaultModTweaks.util.I18n;

/**
 * Changes the {@link iskallia.vaultintegrations.altar.AltarConduitNode} class for
 * managing the Altar Conduit as a node in RS/AE networks.
 * 
 * Specifically, fixes the energy consumption when part of an AE2 grid.
 */
@Mixin(AltarConduitNode.class)
public class MixinAltarConduitNode {
    /**
     * Redirects the
     * {@link iskallia.vaultintegrations.altar.AltarConduitNode#getEnergyUsage()}
     * method to return a value in AE/t instead of RF/t. 1 AE = 2.5 RF.
     * 
     * @return Energy usage in AE/t instead of RF/t
     */
    @Redirect(
        method = "setupAE",
        at = @At(
            value = "INVOKE",
            target = "Liskallia/vaultintegrations/altar/AltarConduitNode;getEnergyUsage()I"
        ),
        remap = false
    )
    private int getEnergyUsageRedirect(AltarConduitNode that) {
        int rfPerTick = (int)VIConfigs.ALTAR_CONDUIT.getRFUsagePerTick();
        if (Configuration.VAULTAR_CONDUIT_FIX.get()) {
            VaultModTweaks.LOGGER.info(I18n.get("the_vault_tweaks.log.inject.conduit.energy"));
            rfPerTick = rfPerTick*2/5;
        }
        return rfPerTick;
    }
}
