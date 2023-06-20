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

@Mixin(VaultAltarBlock.class)
public class MixinVaultAltarBlock {
    @Shadow(remap = false)
    private VaultAltarTileEntity getAltarTileEntity(Level world, BlockPos pos) {
        throw new UnsupportedOperationException("Unimplemented method 'getAltarTileEntity'");
    }

    @Inject(
        method = "use",
        at = @At(
            value = "RETURN",
            ordinal = 0
        ),
        cancellable = true
    )
    private void onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> ci) {
        if (Configuration.ROUTER_VAULTAR_FIX.get() && !world.isClientSide && hand == InteractionHand.MAIN_HAND && player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)player;
            ItemStack heldItem = player.getMainHandItem();
            VaultAltarTileEntity altar = this.getAltarTileEntity(world, pos);
            if (altar.getAltarState() == VaultAltarTileEntity.AltarState.IDLE) {
                if (heldItem.getItem() == ModItems.VAULT_ROCK) {
                    ci.setReturnValue(altar.onAddVaultRock(serverPlayer, heldItem));
                }
            }
        }
    }
}
