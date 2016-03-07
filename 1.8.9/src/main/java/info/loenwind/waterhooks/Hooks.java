package info.loenwind.waterhooks;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;

public class Hooks {

	public static boolean allowFormWaterSourceBlock(World world, BlockPos pos) {
        WaterFormEvent e = new WaterFormEvent(world, pos);
        return !MinecraftForge.EVENT_BUS.post(e);
	}
	
}
