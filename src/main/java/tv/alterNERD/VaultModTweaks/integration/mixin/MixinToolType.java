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

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.item.tool.ToolType;
import net.minecraft.world.item.ItemStack;

@Mixin(ToolType.class)
public abstract class MixinToolType {
    /**
     * Accessor mixin for the
     * {@link iskallia.vault.item.tool.ToolType.PACKED_TO_TYPE} variable.
     * 
     * @return nothing; this should never be actually called
     */
    @Accessor
    static Map<Integer, ToolType> getPACKED_TO_TYPE() {
        throw new AssertionError();
    }

    /**
     * Makes sure that items with both hammering _and_ reaping are given a
     * model.
     * 
     * Sadly there is no way to inject new values into the
     * {@link iskallia.vault.item.tool.ToolType} Enum, so they will render as
     * if they did not have reaping.
     * 
     * @param stack the item in question
     * @param ci CallBackInfo
     * @param data local variable
     * @param packed local variable
     */
    @Inject(
        method = "of",
        at = @At("RETURN"),
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILHARD,
        remap = false
    )
    private static void of$return(ItemStack stack, CallbackInfoReturnable<ToolType> ci, VaultGearData data, int packed)  {
        // hammering: 8, reaping: 16
        if ((packed & 24) == 24) {
            ci.setReturnValue(getPACKED_TO_TYPE().get(packed - 16));
        }
    }
}
