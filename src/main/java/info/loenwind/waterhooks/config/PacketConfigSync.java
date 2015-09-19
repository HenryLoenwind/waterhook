package info.loenwind.waterhooks.config;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketConfigSync implements IMessage, IMessageHandler<PacketConfigSync, IMessage> {

  @Override
  public IMessage onMessage(PacketConfigSync message, MessageContext ctx) {
    return null;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    ConfigHandler.fromBytes(buf);
  }

  @Override
  public void toBytes(ByteBuf buf) {
    ConfigHandler.toBytes(buf);
  }

}
