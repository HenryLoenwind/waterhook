package info.loenwind.waterhooks;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class WaterFormEvent extends FluidEvent {

	public WaterFormEvent(World world, BlockPos pos) {
		super(new FluidStack(FluidRegistry.WATER, 1000), world, pos);
	}
	
}