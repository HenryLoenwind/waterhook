package info.loenwind.waterhooks.config;

import info.loenwind.waterhooks.WaterHooksMod;
import io.netty.buffer.ByteBuf;

import java.io.File;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ConfigHandler {

  static {
    loadAll();
  }

  //	****************************************************************************************

  public static Configuration configuration;
  public static File configDirectory;
  public static boolean configLockedByServer = false;
  public static boolean iAmTheServer = false;

  public ConfigHandler() {
  }

  public void init(FMLPreInitializationEvent event) {
    PacketHandler.INSTANCE.registerMessage(PacketConfigSync.class, PacketConfigSync.class, PacketHandler.nextID(), Side.CLIENT);
    MinecraftForge.EVENT_BUS.register(new ConfigHandler());
    configDirectory = new File(event.getModConfigurationDirectory(), WaterHooksMod.MODID.toLowerCase());
    if (!configDirectory.exists()) {
      configDirectory.mkdir();
    }

    File configFile = new File(configDirectory, WaterHooksMod.MODID + ".cfg");
    configuration = new Configuration(configFile);
    syncConfig(false);
  }

  private static void syncConfig(boolean load) {
    try {
      if (load) {
        configuration.load();
      }
      processConfig();
    } catch (Exception e) {
      Log.error(WaterHooksMod.MODID + " has a problem loading it's configuration");
      e.printStackTrace();
    } finally {
      if (configuration.hasChanged()) {
        configuration.save();
      }
    }
  }

  @SuppressWarnings("static-method")
  @SubscribeEvent
  public void onConfigChanged(OnConfigChangedEvent event) {
    if (event.getModID().equals(WaterHooksMod.MODID)) {
      Log.info("Updating config...");
      syncConfig(false);
    }
  }

  // TODO: Need higher Forge version
//  @SuppressWarnings("static-method")
//  @SubscribeEvent
//  public void onConfigFileChanged(ConfigFileChangedEvent event) {
//    if (event.modID.equals(WaterHooksMod.MODID)) {
//      Log.info("Updating config...");
//      syncConfig(true);
//      event.setSuccessful();
//    }
//  }

  private static void processConfig() {
    loadAll(configuration);
    if (configuration.hasChanged()) {
      configuration.save();
    }
  }

  public static void toBytes(ByteBuf buf) {
    for (Config value : Config.values()) {
      if (value.getSection().sync) {
        value.store(buf);
      }
    }
  }

  public static void fromBytes(ByteBuf buf) {
    if (iAmTheServer) {
      return;
    }
    for (Config value : Config.values()) {
      if (value.getSection().sync) {
        value.read(buf);
      }
    }
    configLockedByServer = true;
  }

  @SuppressWarnings("static-method")
  @SubscribeEvent
  public void onPlayerLoggon(PlayerLoggedInEvent evt) {
    iAmTheServer = true;
    PacketHandler.INSTANCE.sendTo(new PacketConfigSync(), (EntityPlayerMP) evt.player);
  }

  @SuppressWarnings("static-method")
  @SubscribeEvent
  public void onPlayerLogout(ClientDisconnectionFromServerEvent event) {
    syncConfig(false);
    configLockedByServer = false;
  }

  public static void loadAll(Configuration config) {
    for (Config value : Config.values()) {
      value.load(config);
    }
  }

  public static void loadAll() {
    for (Config value : Config.values()) {
      value.resetToDefault();
    }
  }

}
