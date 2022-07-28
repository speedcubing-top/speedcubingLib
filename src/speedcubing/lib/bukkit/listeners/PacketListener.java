package speedcubing.lib.bukkit.listeners;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import speedcubing.lib.bukkit.entity.NPC;
import speedcubing.lib.bukkit.event.PlayInByteEvent;
import speedcubing.lib.bukkit.event.PlayInEvent;
import speedcubing.lib.bukkit.event.PlayOutByteEvent;
import speedcubing.lib.bukkit.event.PlayOutEvent;
import speedcubing.lib.eventbus.LibEventManager;
import speedcubing.lib.speedcubingLib;
import speedcubing.lib.utils.Reflections;

import java.util.List;

public class PacketListener implements Listener {
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        if (speedcubingLib.is1_8_8) {
            Player player = e.getPlayer();
            Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
            channel.pipeline().addAfter("decompress", "speedcubingLib-ByteDecode", new ByteToMessageDecoder() {
                @Override // In Byte
                protected void decode(ChannelHandlerContext channel, ByteBuf byteBuf, List<Object> arg) {
                    PlayInByteEvent event = new PlayInByteEvent(player, byteBuf);
                    LibEventManager.callEvent(event);
                    if (!event.isCancelled)
                        arg.add(byteBuf.readBytes(byteBuf.readableBytes()));
                }
            }).addAfter("decoder", "speedcubingLib-channel", new ChannelDuplexHandler() {
                @Override // Out Byte
                public void write(ChannelHandlerContext channel, Object byteBuf, ChannelPromise promise) throws Exception {
                    PlayOutByteEvent event = new PlayOutByteEvent(player, (ByteBuf) byteBuf);
                    LibEventManager.callEvent(event);
                    if (!event.isCancelled)
                        super.write(channel, byteBuf, promise);
                }

                @Override // In Packet
                public void channelRead(ChannelHandlerContext channel, Object packet) throws Exception {
                    PlayInEvent event = new PlayInEvent(player, (Packet<?>) packet);
                    LibEventManager.callEvent(event);
                    if (!event.isCancelled) {
                        super.channelRead(channel, packet);
                        if (packet instanceof PacketPlayInUseEntity) {
                            int id = (int) Reflections.getField(packet, "a");
                            for (NPC npc : NPC.all) {
                                if (npc.entityPlayer.getId() == id) {
                                    npc.event.run(player, ((PacketPlayInUseEntity) packet).a());
                                    break;
                                }
                            }
                        }
                    }
                }
            }).addAfter("encoder", "speedcubingLib-PacketEncode", new MessageToMessageEncoder<Packet<?>>() {
                @Override // Out Packet
                protected void encode(ChannelHandlerContext channel, Packet<?> packet, List<Object> arg) {
                    PlayOutEvent event = new PlayOutEvent(player, packet);
                    LibEventManager.callEvent(event);
                    if (!event.isCancelled)
                        arg.add(packet);
                }
            });
        }
    }
}
