package tv.alterNERD.VaultModTweaks.integration.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import iskallia.vault.util.EnchantmentEntry;
import net.minecraft.world.item.enchantment.Enchantment;
import tv.alterNERD.VaultModTweaks.Configuration;

@Mixin(EnchantmentEntry.class)
public abstract class MixinEnchantmentEntry
 {
    @Shadow(remap = false)
    private int level;
    @Shadow(remap = false)
    private Enchantment enchantment;
    
    @Overwrite(remap = false)
    public int getLevel() {
        if (Configuration.FORTUNE_ENABLED.get()) {
            if (this.enchantment.getRegistryName().toString().equals("minecraft:fortune")) {
                this.level = Configuration.FORTUNE_LEVEL.get();
            }
        }
        return this.level;
    }

    @Overwrite(remap = false)
    public boolean isValid() {
        if (Configuration.FORTUNE_ENABLED.get()) {
            if (this.enchantment.getRegistryName().toString().equals("minecraft:fortune") && this.level <= Configuration.FORTUNE_LEVEL.get()) {
                return true;
            }
        }
        return this.enchantment != null && this.level > 0 && this.level <= this.enchantment.getMaxLevel();
    }
}
