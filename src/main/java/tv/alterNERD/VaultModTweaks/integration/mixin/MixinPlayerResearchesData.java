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
import java.util.Map;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import iskallia.vault.init.ModConfigs;
import iskallia.vault.research.ResearchTree;
import iskallia.vault.research.type.Research;
import iskallia.vault.world.data.PlayerResearchesData;
import tv.alterNERD.VaultModTweaks.Configuration;
import tv.alterNERD.VaultModTweaks.VaultModTweaks;
import tv.alterNERD.VaultModTweaks.util.I18n;

/**
 * Changes the {@link iskallia.vault.world.data.PlayerResearchesData} class for
 * managing the Research data.
 * 
 * Specifically, it makes the fake player used by Applied Energistics for auto
 * crafting have all researches available.
 */
@Mixin(PlayerResearchesData.class)
public class MixinPlayerResearchesData {
    @Shadow(remap = false)
    private final Map<UUID, ResearchTree> playerMap = new HashMap<UUID, ResearchTree>();

    private boolean ae2ResearchInjected = false;
    private static UUID ae2Uuid = UUID.fromString("41c82c87-7afb-4024-ba57-13d2c99cae77");

    /**
     * Generates a {@link iskallia.vault.research.ResearchTree} for the AE2 fake
     * player with every research unlocked.
     */
    @Inject(
        method = "getResearches(Ljava/util/UUID;)Liskallia/vault/research/ResearchTree;",
        at = @At("RETURN"),
        cancellable = true,
        remap = false
    )
    private void getResearchesCallback(UUID uuid, CallbackInfoReturnable<ResearchTree> ci) {
        if (Configuration.FAKE_PLAYER_FIX.get() && uuid.equals(ae2Uuid) && !this.ae2ResearchInjected) {
            VaultModTweaks.LOGGER.info(I18n.get("the_vault_tweaks.log.inject.ae2research"));
            this.playerMap.remove(ae2Uuid);
            ResearchTree tree = ResearchTree.empty();
            for (Research research : ModConfigs.RESEARCHES.getAll()) {
                tree.research(research);
            }
            this.playerMap.put(ae2Uuid, tree);
            this.ae2ResearchInjected = true;
            ci.setReturnValue(tree);
        }
    }
}
