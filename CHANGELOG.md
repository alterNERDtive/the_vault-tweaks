# devel

Many thanks to @xyzminecraftserver for sponsoring the project!

* 

# 3.12.4.0 (2023-11-19)

* Updated dependencies for Vault Hunters 3.12.4.

# 3.12.2.0 (2023-11-14)

* Updated dependencies for Vault Hunters 3.12.2.

# 3.12.1.1 (2023-11-09)

* Added support for overriding the new Jeweler Expertise; disabling it now grants 3 free cuts by default.

# 3.12.1.0 (2023-11-09)

* Updated dependencies for Vault Hunters 3.12.1.
* Fixed Transmog Table mixin for 3.12.1.
* Updated Vault Altar tags to reflect the Lvl 40 → Lvl 50 pool change.

# 3.11.4.0 (2023-09-10)

* Updated dependencies for Vault Hunters 3.11.4.
* Added God Axe transmog to the super secret feature™.

# 3.11.3.0 (2023-09-03)

* Updated dependencies for Vault Hunters 3.11.3.
* Added Livingrock Bricks to the `the_vault:trash` tag, now that it has soul value.

# 3.11.2.2 (2023-08-20)

* Updated dependencies for Vault Hunters 3.11.2.2.
* Fixed jewel cutting tweaks being en-/disabled via the Jeweller expertise switch, the dedicated option now takes effect.
* Fixed race condition between Vault Mod config loading and Tweaks Mod config loading; the Vault Mod config is now reloaded whenever the Tweaks Mod config is (re-)loaded. (fixes #9)

# 3.11.2.1 (2023-08-07)

* Updated dependencies for Vault Hunters 3.11.2.1.
* Added tag for the new trash loot in Vaults (`the_vault:trash`).

# 3.11.2.0 (2023-08-06)

* Updated dependencies for Vault Hunters 3.11.2.
* Updated tags for Vault Hunters 3.11.2.
* Removed spammy logging for the fake player research fix.

# 3.11.1.1 (2023-07-26)

* Balance changes are now disabled by default, bug fixes enabled. Will not affect exsting worlds/configs.
* Modularized Portal block configuration; you can now define your own blocks to build Vault Portals out of.
* Altar requirement tags! Generated from the actual config file.

# 3.11.1.0 (2023-07-25)

* Updated dependencies for Vault Hunters 3.11.1.

# 3.11.0.3 (2023-07-13)

* Re-release because Curse sucks.
* Removed the Altar Conduit fix toggle from the configuration file.

# 3.11.0.2 (2023-07-13)

* Removed Altar Conduit power draw fix for AE2, fixed in the mod itself.
* Fixed (= removed) Vault Integrations dependency.

# 3.11.0.1 (2023-07-13)

* Updated dependencies for Vault Hunters 3.11.
* Added support for Reaping Jewels (you still cannot obtain those without commands or config changes!).

# 3.10.1.5 (2023-06-27)

* Fixed `removeEmeraldCost` option not being applied correctly on servers (#2).
* Added en_US strings for Clean/Dirty Chromatic Iron Slurry.

# 3.10.1.4 (2023-06-23)

* Fixed Vault Altar Conduit energy usage when used in an AE2 grid.

# 3.10.1.3 (2023-06-21)

* Removed Emerald cost from Vault Enchanter.
* Lots of refactoring work behind the scenes.
* Added i18n support and lots of logging.
* Fixed AE2 fake player in existing worlds not getting new research when it is added to the pack.

# 3.10.1.1 (2023-06-20)

* Fixed AE2 auto crafting needing NBT editing.
* Removed colour handler fix; install <https://github.com/radimous/FastVaultGear> instead.

# 3.10.1.0 (2023-06-19)

Initial release for The Vault 3.10.1.
