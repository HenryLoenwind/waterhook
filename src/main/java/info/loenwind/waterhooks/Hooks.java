package info.loenwind.waterhooks;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;

public class Hooks {

	public static boolean allowFormWaterSourceBlock(World world, int x, int y, int z) {
        WaterFormEvent e = new WaterFormEvent(world, x, y, z);
        return !MinecraftForge.EVENT_BUS.post(e);
	}
	
}
