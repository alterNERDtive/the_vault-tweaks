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

import iskallia.vault.block.VaultAltarBlock;
import iskallia.vault.block.entity.VaultAltarTileEntity;
import iskallia.vault.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import tv.alterNERD.VaultModTweaks.Configuration;
import tv.alterNERD.VaultModTweaks.VaultModTweaks;
import tv.alterNERD.VaultModTweaks.util.I18n;

/**
 * Changes the {@link iskallia.vault.block.VaultAltarBlock} class.
 * 
 * Specifically, allows fake players to place Vault Rocks on it.
 */
@Mixin(VaultAltarBlock.class)
public class MixinVaultAltarBlock {
    @Shadow(remap = false)
    private VaultAltarTileEntity getAltarTileEntity(Level world, BlockPos pos) {
        throw new UnsupportedOperationException("Unimplemented method 'getAltarTileEntity'");
    }

    /**
     * Actually allows fake players to interact with the Vault Altar block if
     * they are holding a Vault Rock in their main hand.
     * 
     * @param state
     * @param world
     * @param pos
     * @param player
     * @param hand
     * @param hit
     * @param ci
     */
    @Inject(
        method = "use",
        at = @At(
            value = "RETURN",
            ordinal = 0
        ),
        cancellable = true
    )
    private void use$return(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> ci) {
        if (Configuration.ROUTER_VAULTAR_FIX.get() && !world.isClientSide && hand == InteractionHand.MAIN_HAND && player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)player;
            ItemStack heldItem = player.getMainHandItem();
            VaultAltarTileEntity altar = this.getAltarTileEntity(world, pos);
            if (altar.getAltarState() == VaultAltarTileEntity.AltarState.IDLE) {
                if (heldItem.getItem() == ModItems.VAULT_ROCK) {
                    VaultModTweaks.LOGGER.info(I18n.get("the_vault_tweaks.log.inject.vaultar.rock"));
                    ci.setReturnValue(altar.onAddVaultRock(serverPlayer, heldItem));
                }
            }
        }
    }
}
