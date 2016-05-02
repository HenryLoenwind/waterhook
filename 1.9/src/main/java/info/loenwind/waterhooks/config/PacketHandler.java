package info.loenwind.waterhooks.config;

import info.loenwind.waterhooks.WaterHooksMod;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(WaterHooksMod.MODID);

    private static int ID = 0;

    public static int nextID()
    {
        return ID++;
    }

    public static void sendToAllAround(IMessage message, TileEntity te, int range) {
    INSTANCE
        .sendToAllAround(message, new TargetPoint(te.getWorld().provider.getDimension(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), range));
    }

    public static void sendToAllAround(IMessage message, TileEntity te) {
        sendToAllAround(message, te, 64);
    }

    public static void sendTo(IMessage message, EntityPlayerMP player) {
      INSTANCE.sendTo(message, player);
    }
}
