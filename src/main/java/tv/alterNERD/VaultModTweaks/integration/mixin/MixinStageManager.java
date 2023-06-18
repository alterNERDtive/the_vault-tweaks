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

@Mixin(StageManager.class)
public abstract class MixinStageManager {
    @Shadow
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
    private static ResearchTree overrideOnItemCrafted(Player player) {
        return overrideGetResearchTree(player);
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
    private static ResearchTree overrideOnBlockInteraction(Player player) {
        return overrideGetResearchTree(player);
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
    private static ResearchTree overrideOnItemUse(Player player) {
        return overrideGetResearchTree(player);
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
    private static ResearchTree overrideOnEntityInteraction(Player player) {
        return overrideGetResearchTree(player);
    }

    private static ResearchTree overrideGetResearchTree(Player player) {
        if (player.level.isClientSide) {
            return RESEARCH_TREE;
        }
        return PlayerResearchesData.get((ServerLevel)player.level).getResearches(player);
    }
}
