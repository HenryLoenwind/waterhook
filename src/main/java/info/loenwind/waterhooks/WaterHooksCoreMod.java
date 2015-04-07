package info.loenwind.waterhooks;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.SortingIndex;

//-Dfml.coreMods.load=info.loenwind.waterhooks.WaterHooksCoreMod
@Name("WaterHooksCoreMod")
@MCVersion("1.7.10")
@SortingIndex(1001)
public class WaterHooksCoreMod implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[] { "info.loenwind.waterhooks.BlockDynamicLiquidVisitor" };
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
