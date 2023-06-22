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

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.JsonObject;

import iskallia.vault.util.EnchantmentCost;
import net.minecraft.world.item.ItemStack;
import tv.alterNERD.VaultModTweaks.Configuration;
import tv.alterNERD.VaultModTweaks.VaultModTweaks;

/**
 * Changes the {@link iskallia.vault.util.EnchantmentCost} class used by the
 * Vault Enchanter.
 * 
 * Specifically, it removes the Emerald cost of enchantments. Just because I
 * cannot be arsed to bring them over every time.
 */
@Mixin(EnchantmentCost.class)
public class MixinEnchantmentCost {
    @Shadow(remap = false)
    private List<ItemStack> items;

    /**
     * Removes the Emerald cost from all Vault Enchanter enchantments when
     * reading an existing JSON configuration.
     * 
     * @param json
     * @param ci
     */
    @Inject(
        method = "readJson",
        at = @At("RETURN"),
        remap = false
    )
    private void readJsonCallback(JsonObject json, CallbackInfo ci) {
        if (Configuration.ENCHANTS_FORFREE.get()) {
            VaultModTweaks.LOGGER.info("the_vault_tweaks.log.inject.enchanter.forfree");
            this.items.clear();
        }
    }

}
