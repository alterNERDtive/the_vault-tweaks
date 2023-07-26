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
package tv.alterNERD.VaultModTweaks;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class Configuration {
    public static final ForgeConfigSpec CONFIG;
    public static final ForgeConfigSpec CLIENTCONFIG;

    public static BooleanValue FORTUNE_ENABLED;
    public static IntValue FORTUNE_LEVEL;

    public static BooleanValue ENCHANTS_FORFREE;

    public static BooleanValue JEWELER_ENABLED;
    public static DoubleValue JEWELER_CHANCE;

    public static BooleanValue JEWELS_ENABLED;
    public static IntValue JEWELS_SIZE;
    public static IntValue JEWELS_MAX;
    public static IntValue JEWELS_MIN;

    public static BooleanValue BUDDING_ENABLED;
    public static IntValue BUDDING_MAX;
    public static IntValue BUDDING_MIN;

    public static BooleanValue VAULTAR_ENABLED;
    public static IntValue VAULTAR_INFUSION_TIME;
    
    public static BooleanValue PORTAL_TEMPLATE_ENABLED;
    
    public static BooleanValue FAKE_PLAYER_FIX;
    public static BooleanValue ROUTER_VAULTAR_FIX;
    public static BooleanValue FRAGMENT_WEIGHT_FIX;
    
    public static BooleanValue JUNKMGMT_ENABLED;
    public static ConfigValue<Integer> JUNKMGMT_T1;
    public static ConfigValue<Integer> JUNKMGMT_T2;
    public static ConfigValue<Integer> JUNKMGMT_T3;
    public static ConfigValue<Integer> JUNKMGMT_T4;

    public static ConfigValue<List<? extends String>> GOBLINS;
    public static ConfigValue<List<? extends String>> CHAMPIONS;

    static {
        Builder builder = new Builder();

        // Fortune changes
        builder.push("Fortune");
        FORTUNE_ENABLED = builder
            .comment("Replace the maximum Fortune level for the Vault Enchanter and remove the Fortunate Expertise")
            .define("enableOverride", true);
        FORTUNE_LEVEL = builder
            .comment("Maximum Fortune level")
            .defineInRange("maxLevel", 5, 3, 5);
        builder.pop();

        // Enchantment Cost
        builder.push("VaultEnchanter");
        ENCHANTS_FORFREE = builder
            .comment("Remove the Emerald cost from the Vault Enchanter")
            .define("removeEmeraldCost", true);
        builder.pop();

        // Jeweler
        builder.push("Jeweler");
        JEWELER_ENABLED = builder
            .comment("Remove the Jeweler Expertise and change the default cutting chance accordingly")
            .define("disableJeweler", true);
        JEWELER_CHANCE = builder
            .comment("Chance to break the jewel / remove a modifier")
            .defineInRange("breakChance", 0.25d, 0d, 0.5d);
        builder.pop();

        // Budding Crystal
        builder.push("BuddingCrystal");
        BUDDING_ENABLED = builder
            .comment("Change the Budding Crystal growth times (Sky Vaults only)")
            .define("enableOverride", true);
        BUDDING_MAX = builder
            .comment("Maximum time between growth stages (pack default: 400)")
            .defineInRange("maxTime", 300, 300, 500);
        BUDDING_MIN = builder
            .comment("Minimum time between growth stages (pack default: 280)")
            .defineInRange("minTime", 200, 100, 299);
        builder.pop();

        // Jewels
        builder.push("Jewels");
        JEWELS_ENABLED = builder
            .comment("Change the max size for jewels and the size range for jewel cutting")
            .define("enableOverride", true);
        JEWELS_SIZE = builder
            .comment("Maximum Jewel size (pack default: 90) (CURRENTLY NON-FUNCTIONAL)")
            .defineInRange("maxSize", 40, 10, 100);
        JEWELS_MAX = builder
            .comment("Maximum size reduction when cutting (pack default: 10)")
            .defineInRange("maxCut", 10, 5, 20);
        JEWELS_MIN = builder
            .comment("Minimum size reduction when cutting (pack default: 1)")
            .defineInRange("minCut", 3, 1, 4);
        builder.pop();

        // Vault Altar
        builder.push("VaultAltar");
        VAULTAR_ENABLED = builder
            .comment("Enable Vaultar config overrides")
            .define("enableOverride", true);
        VAULTAR_INFUSION_TIME = builder
            .comment("The time it takes to infuse a crystal after giving a redstone signal (pack default: 5)")
            .defineInRange("infusionTime", 1, 1, 10);
        builder.pop();

        // Vault Charm / Junk Management
        builder.push("JunkManagement");
        JUNKMGMT_ENABLED = builder
            .comment("Override Junk Charm multipliers for the Junk Management slots (pack defaults: 3/18/114/228)")
            .define("enableOverride", true);
        builder.comment("These values will be multiplied by the default junk list size (9)");
        JUNKMGMT_T1 = builder.define("tier1Multiplier", 28);
        JUNKMGMT_T2 = builder.define("tier2Multiplier", 57);
        JUNKMGMT_T3 = builder.define("tier3Multiplier", 114);
        JUNKMGMT_T4 = builder.define("tier4Multiplier", 228);
        builder.pop();

        // Vault Portal
        builder.push("VaultPortal");
        PORTAL_TEMPLATE_ENABLED = builder
            .comment("Allow Template Frame Blocks extruded by Modular Routers’ Extruder Mk2 module as Vault Portal Frame blocks")
            .define("allowTemplateFrames", true);
        builder.pop();

        // Bug fixes
        builder.push("Fixes");
        FAKE_PLAYER_FIX = builder
            .comment("Fix fake player research (e.g. Router + Botany Pots interaction, AE2 auto crafting, …)")
            .define("fakePlayerResearchFix", true);
        ROUTER_VAULTAR_FIX = builder
            .comment("Fix Routers unable to place Vault Rocks on your Vaultar")
            .define("routerVaultarFix", true);
        FRAGMENT_WEIGHT_FIX = builder
            .comment("Fix №5 fragments of all relics having half the weight")
            .define("fragmentFix", true);
        builder.pop();

        // Transmogs
        builder.push("Transmogs");
        GOBLINS = builder
            .comment("Additional “Goblin” tier Patrons")
            .defineList("goblins", new ArrayList<String>(), entry -> true);
        CHAMPIONS = builder
            .comment("Additional “Champion” tier Patrons")
            .defineList("champions", new ArrayList<String>(), entry -> true);
        builder.pop();

        CONFIG = builder.build();

        builder = new Builder();

        CLIENTCONFIG = builder.build();
    }
}
