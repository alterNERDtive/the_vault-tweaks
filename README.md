# Vault Mod Tweaks

This little companion mod for the Vault mod of the Vault Hunters mod pack fixes a few bugs and brings a couple QoL and balance changes.

All of these can be individually disabled in client & server config files.

If you do not disable the changes that affect expertises, I recommend a full expertise reset after installing this mod.

## Fixes

* Removed the “Andersite” “joke”
* ~~Removed call to a colour handling event that is called several thousand times per second; gives a noticeable performance boost, but turns jewels and unidentified items white~~ use <https://github.com/radimous/FastVaultGear>
* Fake players work with research again (e.g. Routers + Botany Pots, AE2 auto crafting)
* Fake players can put Vault Rocks on the Altar again
* Relic fragments \#5 are now equally as likely to drop as the others
* AE2 auto crafting no longer requires manually giving its fake player all research
* Altar Conduit now draws the correct amount of power when connected to AE2

## Changes

* Removed Emerald cost from Vault Enchanter.
* Vault Enchanters offer Fortune 5, Fortunate expertise removed
* Jewel cutting has a base 25% chance of failure, Jeweler expertise removed
* Budding Crystal (Sky Vaults only) growth time reduced
* Altar infusion time (time between redstone signal and the crystal popping off) reduced
* Vault Junk Upgrades (Junk Management) tier 1 and 2 buffed
* Jewel cutting buffed from 1–10 size reduction to 3–10
