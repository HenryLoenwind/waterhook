package info.loenwind.waterhooks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class Hooks {

	public static boolean allowFormWaterSourceBlock(World world, BlockPos pos) {
        WaterFormEvent e = new WaterFormEvent(world, pos);
        return !MinecraftForge.EVENT_BUS.post(e);
	}
	
}
