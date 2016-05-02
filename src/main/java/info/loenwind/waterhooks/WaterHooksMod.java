package info.loenwind.waterhooks;

import info.loenwind.waterhooks.config.Config;
import info.loenwind.waterhooks.config.ConfigHandler;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = WaterHooksMod.MODID, version = WaterHooksMod.VERSION, guiFactory = "info.loenwind.waterhooks.config.gui.ConfigFactory")
public class WaterHooksMod
{
	public static final String MODID = "waterhooks";
    public static final String VERSION = "1.2.1";

    private static final ConfigHandler CONFIG_HANDLER = new ConfigHandler();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(this);
    	CONFIG_HANDLER.init(event);
    }

    @SubscribeEvent
    public void onWaterForming(WaterFormEvent event)
    {
        if (Config.disableInfiniteWaterGlobally.getBoolean()) {
            event.setCanceled(true);
          }
    }
}
