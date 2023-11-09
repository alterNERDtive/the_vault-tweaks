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

import java.util.Collection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import iskallia.vault.block.TransmogTableBlock;
import iskallia.vault.dynamodel.model.armor.ArmorPieceModel;
import iskallia.vault.init.ModDynamicModels.Armor;
import iskallia.vault.init.ModDynamicModels.Axes;
import iskallia.vault.init.ModDynamicModels.Swords;
import iskallia.vault.patreon.PatreonManager;
import iskallia.vault.patreon.PatreonPlayerData;
import iskallia.vault.patreon.PatreonTier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import tv.alterNERD.VaultModTweaks.Configuration;

/**
 * Changes the {@link iskallia.vault.block.TransmogTableBlock} class for
 * managing the Transmog Table.
 * 
 * Specifically, unlocks Patreon transmogs.
 */
@Mixin(TransmogTableBlock.class)
public abstract class MixinTransmogTableBlock {
    /***
     * Injects some new Goblin / Champion tier patrons into the transmogajigga
     * thing.
     * 
     * @param player
     * @param discoveredModelIds
     * @param modelId
     */
    @Overwrite(remap = false)
    public static boolean canTransmogModel(Player player, Collection<ResourceLocation> discoveredModelIds, ResourceLocation modelId)  {
        long id = player.getUUID().getMostSignificantBits() ^ player.getUUID().getLeastSignificantBits();
        String name = player.getName().getString();

        PatreonPlayerData data = PatreonManager.getInstance().getPlayerData(player.getUUID());
        return (Boolean)Armor.PIECE_REGISTRY.get(modelId).map(ArmorPieceModel::getArmorModel).map((armorModel) -> {
            if (armorModel.equals(Armor.CHAMPION)) {
                return data.isAtLeastTier(PatreonTier.CHAMPION) || Configuration.CHAMPIONS.get().contains(name);
            } else if (armorModel.equals(Armor.GOBLIN)) {
                return data.isAtLeastTier(PatreonTier.GOBLIN) || Configuration.GOBLINS.get().contains(name) || Configuration.CHAMPIONS.get().contains(name);
            } else {
                return null;
            }
        }).or(() -> {
            return Swords.REGISTRY.get(modelId).map((model) -> {
                return model.equals(Swords.GODSWORD) ? data.isAtLeastTier(PatreonTier.CHAMPION) || Configuration.CHAMPIONS.get().contains(name) : null;
            });
        }).or(() -> {
            return Axes.REGISTRY.get(modelId).map((model) -> {
                return model.equals(Axes.GODAXE) ? data.isAtLeastTier(PatreonTier.CHAMPION) || Configuration.CHAMPIONS.get().contains(name) : null;
            });
        }).orElseGet(() -> {
            return discoveredModelIds.contains(modelId);
        });
    }
}
