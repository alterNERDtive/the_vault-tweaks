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
package tv.alterNERD.VaultModTweaks.util;

import net.minecraft.network.chat.TranslatableComponent;

/**
 * Provides a shorthand for getting localized {@link java.lang.String}s from
 * Minecraft’s {@link net.minecraft.network.chat.TranslatableComponent}s.
 */
public class I18n {
    /**
     * Provides the localized {@link java.lang.String} to a given key.
     * 
     * @param key The key
     * @return The localized {@link java.lang.String}
     */
    public static String get(String key) {
        return new TranslatableComponent(key).getString();
    }

    /**
     * Provides the localized {@link java.lang.String} to a given key and
     * arguments.
     * 
     * @param key The key
     * @param args The arguments used for String formatting
     * @return The localized {@link java.lang.String}
     */
    public static String get(String key, Object... args) {
        return new TranslatableComponent(key, args).getString();
    }
}