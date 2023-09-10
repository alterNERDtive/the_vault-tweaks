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
import java.util.Set;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import iskallia.vault.block.TransmogTableBlock;
import iskallia.vault.dynamodel.model.armor.ArmorPieceModel;
import iskallia.vault.init.ModDynamicModels;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import tv.alterNERD.VaultModTweaks.Configuration;
import tv.alterNERD.VaultModTweaks.VaultModTweaks;

/**
 * Changes the {@link iskallia.vault.block.TransmogTableBlock} class for
 * managing the Transmog Table.
 * 
 * Specifically, unlocks Patreon transmogs.
 */
@Mixin(TransmogTableBlock.class)
public abstract class MixinTransmogTableBlock {
    @Shadow(remap = false)
    private static Set<Long> CHAMPION_LIST;
    
    @Shadow(remap = false)
    private static Set<Long> GOBLIN_LIST;

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
        return ModDynamicModels.Armor.PIECE_REGISTRY.get(modelId).map(ArmorPieceModel::getArmorModel).map(armorModel -> {
            VaultModTweaks.LOGGER.debug(player.getName().getString());
            if (armorModel.equals(ModDynamicModels.Armor.CHAMPION)) {
                return CHAMPION_LIST.contains(id) || Configuration.CHAMPIONS.get().contains(name);
            }
            if (armorModel.equals(ModDynamicModels.Armor.GOBLIN)) {
                return GOBLIN_LIST.contains(id) || CHAMPION_LIST.contains(id) || Configuration.GOBLINS.get().contains(name) || Configuration.CHAMPIONS.get().contains(name);
            }
            return null;
        }).or(() -> ModDynamicModels.Swords.REGISTRY.get(modelId).map(model -> {
            if (model.equals(ModDynamicModels.Swords.GODSWORD)) {
                return CHAMPION_LIST.contains(id) || Configuration.CHAMPIONS.get().contains(name);
            }
            return null;
        })).or(() -> ModDynamicModels.Axes.REGISTRY.get(modelId).map(model -> {
            if (model.equals(ModDynamicModels.Axes.GODAXE)) {
                return CHAMPION_LIST.contains(id) || Configuration.CHAMPIONS.get().contains(name);
            }
            return null;
        })).orElse(discoveredModelIds.contains(modelId));
    }
}
