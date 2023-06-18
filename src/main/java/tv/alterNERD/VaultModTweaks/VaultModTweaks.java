package tv.alterNERD.VaultModTweaks;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("the_vault_tweaks")
public class VaultModTweaks
{
    private static final Logger LOGGER = LogUtils.getLogger();

    public VaultModTweaks()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.CONFIG);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Vault Mod Tweaks by alterNERDtive");
    }
}
