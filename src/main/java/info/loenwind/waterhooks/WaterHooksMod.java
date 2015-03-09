package info.loenwind.waterhooks;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = WaterHooksMod.MODID, version = WaterHooksMod.VERSION)
public class WaterHooksMod
{
    public static final String MODID = "waterhooks";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
//    	MinecraftForge.EVENT_BUS.register(this);
    }

//    @SubscribeEvent
//    public void onWaterForming(info.loenwind.waterhooks.Hooks.WaterFormEvent event)
//    {
//    	event.setCanceled(true);
//    }
}
