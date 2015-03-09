package info.loenwind.waterhooks;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class Hooks {

	public static boolean allowFormWaterSourceBlock(World world, int x, int y, int z) {
        WaterFormEvent e = new WaterFormEvent(world, x, y, z);
        return !MinecraftForge.EVENT_BUS.post(e);
	}

	@Cancelable
	public static class WaterFormEvent extends FluidEvent {

		public WaterFormEvent(World world, int x, int y, int z) {
			super(new FluidStack(FluidRegistry.WATER, 1000), world, x, y, z);
		}
		
	}
	
}
