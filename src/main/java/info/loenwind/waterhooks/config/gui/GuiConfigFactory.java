package info.loenwind.waterhooks.config.gui;

import info.loenwind.waterhooks.WaterHooksMod;
import info.loenwind.waterhooks.config.ConfigHandler;
import info.loenwind.waterhooks.config.Section;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

@SuppressWarnings({ "rawtypes" })
public class GuiConfigFactory extends GuiConfig {

  public GuiConfigFactory(GuiScreen parentScreen) {
    super(parentScreen, getConfigElements(parentScreen), WaterHooksMod.MODID, false, false, "Waterhook Configuration");
  }

  private static List<IConfigElement> getConfigElements(GuiScreen parent) {
    List<IConfigElement> list = new ArrayList<IConfigElement>();
    String prefix = "config.";

    for (Section section : Section.values()) {
      if (!section.sync || !ConfigHandler.configLockedByServer) {
        list.add(new ConfigElement<ConfigCategory>(ConfigHandler.configuration.getCategory(section.name).setLanguageKey(prefix)));
      }
    }

    return list;
  }
}
