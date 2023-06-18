package tv.alterNERD.VaultModTweaks.integration.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import iskallia.vault.event.ClientInitEvents;
import iskallia.vault.init.ModModels;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tv.alterNERD.VaultModTweaks.Configuration;

@Mixin(ClientInitEvents.class)
public class MixinClientInitEvents {
    @Overwrite(remap = false)
    @SubscribeEvent
    public static void onColorHandlerRegister(ColorHandlerEvent.Item event) {
        if (!Configuration.COLOURLESS.get()) {
            ModModels.registerItemColors(event.getItemColors());
        }
    }
}
