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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import iskallia.vault.util.EnchantmentEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import tv.alterNERD.VaultModTweaks.Configuration;
import tv.alterNERD.VaultModTweaks.VaultModTweaks;
import tv.alterNERD.VaultModTweaks.util.I18n;

/**
 * Changes the {@link iskallia.vault.util.EnchantmentEntry} class used by the
 * Vault Enchanter.
 * 
 * Specifically, allows for it to enchant Fortune V.
 */
@Mixin(EnchantmentEntry.class)
public abstract class MixinEnchantmentEntry
 {
    @Shadow(remap = false)
    private int level;
    @Shadow(remap = false)
    private Enchantment enchantment;
    
    /** 
     * Sets the level of the Fortune enchantment to 5.
     */
    @Inject(
        method = "<init>",
        at = @At("RETURN"),
        remap = false
    )
    private void initCallback(Enchantment enchantment, int level, CallbackInfo ci) {
        if (Configuration.FORTUNE_ENABLED.get()) {
            ResourceLocation registryName = this.enchantment.getRegistryName();
            if (registryName != null && registryName.toString().equals("minecraft:fortune")) {
                maxFortune = Configuration.FORTUNE_LEVEL.get();
                VaultModTweaks.LOGGER.info(I18n.get("the_vault_tweaks.log.inject.fortune.level", maxFortune));
                this.level = maxFortune;
            }
        }
    }

    /** 
     * Makes sure that Fortune level 5 is considered a valid enchantment.
     */
    @Inject(
        method = "isValid()Z",
        at = @At("RETURN"),
        cancellable = true,
        remap = false
    )
    private void isValidCallback(CallbackInfoReturnable<Boolean> ci) {
        if (Configuration.FORTUNE_ENABLED.get()) {
            ResourceLocation registryName = this.enchantment.getRegistryName();
            if (registryName != null && registryName.toString().equals("minecraft:fortune")
                    && this.level <= Configuration.FORTUNE_LEVEL.get()) {
                VaultModTweaks.LOGGER.info(I18n.get("the_vault_tweaks.log.inject.fortune.valid"));
                ci.setReturnValue(true);
            }
        }
    }
}
