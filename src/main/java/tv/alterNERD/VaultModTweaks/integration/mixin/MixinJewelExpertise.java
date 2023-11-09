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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import iskallia.vault.skill.expertise.type.JewelExpertise;
import tv.alterNERD.VaultModTweaks.Configuration;
import tv.alterNERD.VaultModTweaks.VaultModTweaks;
import tv.alterNERD.VaultModTweaks.util.I18n;

@Mixin(JewelExpertise.class)
public abstract class MixinJewelExpertise {

    @Shadow
    private int numberOfFreeCuts;

    @Inject(
        method = "getNumberOfFreeCuts()I",
        at = @At("RETURN"),
        cancellable = true,
        remap = false
    )
    private void getNumberOfFreeCuts$return(CallbackInfoReturnable<Integer> ci) {
        if (Configuration.JEWELER_ENABLED.get()) {
            int cuts = Configuration.JEWELER_FREE_CUTS.get();
            VaultModTweaks.LOGGER.info(I18n.get("the_vault_tweaks.log.inject.jewelcutting.freecuts", cuts));
            ci.setReturnValue(cuts);
        }
     }
}
