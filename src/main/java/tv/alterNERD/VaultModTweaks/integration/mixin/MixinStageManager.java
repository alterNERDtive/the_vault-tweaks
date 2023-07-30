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
import org.spongepowered.asm.mixin.injection.Redirect;

import iskallia.vault.research.ResearchTree;
import iskallia.vault.research.StageManager;
import iskallia.vault.world.data.PlayerResearchesData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;
import tv.alterNERD.VaultModTweaks.Configuration;
import tv.alterNERD.VaultModTweaks.VaultModTweaks;
import tv.alterNERD.VaultModTweaks.util.I18n;

/**
 * Changes the {@link iskallia.vault.research.StageManager} class for managing
 * everything related to research locks.
 * 
 * Specifically, it actually returns a valid research tree for fake players
 * (e.g. Modular routers, AE2 Molecular Assemblers).
 */
@Mixin(StageManager.class)
public abstract class MixinStageManager {
    @Shadow(remap = false)
    public static ResearchTree RESEARCH_TREE;

    @Redirect(
        method = "onItemCrafted",
        at = @At(
            value = "INVOKE",
            target = "getResearchTree(Lnet/minecraft/world/entity/player/Player;)Liskallia/vault/research/ResearchTree;",
            remap = false
        ),
        remap = false
    )
    private static ResearchTree onItemCrafted$getResearchTree(Player player) {
        return getResearchTree$redirect(player);
    }

    @Redirect(
        method = "onBlockInteraction",
        at = @At(
            value = "INVOKE",
            target = "getResearchTree(Lnet/minecraft/world/entity/player/Player;)Liskallia/vault/research/ResearchTree;",
            remap = false
        ),
        remap = false
    )
    private static ResearchTree onBlockInteraction$getResearchTree(Player player) {
        return getResearchTree$redirect(player);
    }

    @Redirect(
        method = "onItemUse",
        at = @At(
            value = "INVOKE",
            target = "getResearchTree(Lnet/minecraft/world/entity/player/Player;)Liskallia/vault/research/ResearchTree;",
            remap = false
        ),
        remap = false
    )
    private static ResearchTree onItemUse$getResearchTree(Player player) {
        return getResearchTree$redirect(player);
    }

    @Redirect(
        method = "onEntityInteraction",
        at = @At(
            value = "INVOKE",
            target = "getResearchTree(Lnet/minecraft/world/entity/player/Player;)Liskallia/vault/research/ResearchTree;",
            remap = false
        ),
        remap = false
    )
    private static ResearchTree onEntityInteraction$getResearchTree(Player player) {
        return getResearchTree$redirect(player);
    }

    /**
     * Actually returns a working {@link iskallia.vault.research.ResearchTree}
     * for fake players.
     * @param player
     * @return
     */
    private static ResearchTree getResearchTree$redirect(Player player) {
        ResearchTree tree = ResearchTree.empty();
        if (player instanceof FakePlayer) {
            if (Configuration.FAKE_PLAYER_FIX.get()) {
                // Removed cause very spammy :)
                // VaultModTweaks.LOGGER.info(I18n.get("the_vault_tweaks.log.inject.research.fakeplayerfix", player.getUUID()));
                tree = PlayerResearchesData.get((ServerLevel) player.level).getResearches(player);
            }
        }
        else if (player.level.isClientSide) {
            tree = RESEARCH_TREE;
        }
        else {
            tree = PlayerResearchesData.get((ServerLevel)player.level).getResearches(player);
        }
        return tree;
    }
}
