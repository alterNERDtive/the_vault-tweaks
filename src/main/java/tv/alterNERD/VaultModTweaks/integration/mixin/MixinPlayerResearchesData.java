package tv.alterNERD.VaultModTweaks.integration.mixin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import iskallia.vault.init.ModConfigs;
import iskallia.vault.research.ResearchTree;
import iskallia.vault.research.type.Research;
import iskallia.vault.world.data.PlayerResearchesData;
import tv.alterNERD.VaultModTweaks.Configuration;

@Mixin(PlayerResearchesData.class)
public class MixinPlayerResearchesData {
    @Shadow
    private final Map<UUID, ResearchTree> playerMap = new HashMap<UUID, ResearchTree>();

    @Overwrite(remap = false)
    public ResearchTree getResearches(UUID uuid) {
        return this.playerMap.computeIfAbsent(uuid, id -> {
            ResearchTree tree = ResearchTree.empty();
            if (Configuration.FAKE_PLAYER_FIX.get() && id.equals(UUID.fromString("41c82c87-7afb-4024-ba57-13d2c99cae77"))) {
                for (Research research : ModConfigs.RESEARCHES.getAll()) {
                    tree.research(research);
                }
            }
            return tree;
        });
    }
}
