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
package tv.alterNERD.VaultModTweaks.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jetbrains.annotations.Nullable;

import iskallia.vault.config.altar.VaultAltarIngredientsConfig;
import iskallia.vault.config.altar.entry.AltarIngredientEntry;
import iskallia.vault.config.entry.LevelEntryMap;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import tv.alterNERD.VaultModTweaks.integration.mixin.IMixinVaultAltarIngredientsConfig;

/**
 * Adds custom Tags.
 */
public class TagManager
extends ItemTagsProvider
{
    public TagManager(DataGenerator generator, BlockTagsProvider blockTagsProvider, String modId,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, blockTagsProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        LevelEntryMap<Map<String, WeightedList<AltarIngredientEntry>>> levelIngredientsMap = ((IMixinVaultAltarIngredientsConfig) (VaultAltarIngredientsConfig) new VaultAltarIngredientsConfig().readConfig()).getLEVELS();
        
        Map<String, SortedMap<Integer, List<Item>>> poolMap = new HashMap<String, SortedMap<Integer, List<Item>>>();

        // Convert the level -> pool → list<ingrediententry> format to what we need:
        // pool -> level -> list<item>
        levelIngredientsMap.forEach((level, ingredients) -> {
            ingredients.forEach((pool, ingredientEntryList) -> {
                SortedMap<Integer, List<Item>> poolItems = poolMap.getOrDefault(pool, new TreeMap<Integer, List<Item>>());
                List<Item> itemList = poolItems.getOrDefault(level, new ArrayList<Item>());

                ingredientEntryList.forEach((ingredientEntry, weight) -> {
                    ingredientEntry.getItems().forEach(itemStack -> {
                        itemList.add(itemStack.getItem());
                    });
                });

                poolItems.putIfAbsent(level, itemList);
                poolMap.putIfAbsent(pool, poolItems);
            });
        });

        // Create (and populate) the actual tags
        TagKey<Item> altarTag = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("the_vault:altar_requirements"));
        poolMap.forEach((pool, levelItemMap) -> {
            TagKey<Item> poolTag = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("the_vault:altar_requirements/%s".formatted(pool)));
            List<Item> itemsToRemove = new ArrayList<Item>();
            levelItemMap.forEach((level, items) -> {

                // Lets make sure to _only_ tag items with the current level that are _not_ included in previous levels
                items.removeAll(itemsToRemove);

                if (!items.isEmpty()) {
                    TagKey<Item> levelTag = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("the_vault:altar_requirements/%s".formatted(level)));
                    TagKey<Item> poolLevelTag = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("the_vault:altar_requirements/%s/%s".formatted(pool, level)));

                    items.forEach(item -> {
                        tag(poolLevelTag).add(item);
                    });

                    itemsToRemove.addAll(items);

                    tag(levelTag).addTag(poolLevelTag);
                    tag(poolTag).addTag(poolLevelTag);
                }
            });
            tag(altarTag).addTag(poolTag);
        });
    }
}
