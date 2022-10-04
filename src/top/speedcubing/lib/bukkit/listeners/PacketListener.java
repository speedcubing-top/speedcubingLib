package top.speedcubing.lib.bukkit.listeners;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunkBulk;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.NumberConversions;
import top.speedcubing.lib.bukkit.entity.NPC;
import top.speedcubing.lib.bukkit.event.PlayInByteEvent;
import top.speedcubing.lib.bukkit.event.PlayInEvent;
import top.speedcubing.lib.bukkit.event.PlayOutByteEvent;
import top.speedcubing.lib.bukkit.event.PlayOutEvent;
import top.speedcubing.lib.speedcubingLibBukkit;
import top.speedcubing.lib.utils.Reflections;

import java.util.List;

public class PacketListener implements Listener {
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        if (speedcubingLibBukkit.is1_8_8) {
            Player player = e.getPlayer();
            ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
            ByteToMessageDecoder byteToMessageDecoder = new ByteToMessageDecoder() {
                @Override // In Byte
                protected void decode(ChannelHandlerContext channel, ByteBuf byteBuf, List<Object> arg) {
                    if (!((PlayInByteEvent) new PlayInByteEvent(player, byteBuf).call()).isCancelled)
                        arg.add(byteBuf.readBytes(byteBuf.readableBytes()));
                    else
                        byteBuf.skipBytes(byteBuf.readableBytes());
                }
            };
            if (pipeline.get("decompress") != null)
                pipeline.addAfter("decompress", "speedcubingLib-ByteDecode", byteToMessageDecoder);
            else if (pipeline.get("splitter") != null)
                pipeline.addAfter("splitter", "speedcubingLib-ByteDecode", byteToMessageDecoder);
            if (pipeline.get("decoder") != null)
                pipeline.addAfter("decoder", "speedcubingLib-channel", new ChannelDuplexHandler() {
                    @Override // Out Byte
                    public void write(ChannelHandlerContext channel, Object byteBuf, ChannelPromise promise) throws Exception {
                        if (!((PlayOutByteEvent) new PlayOutByteEvent(player, (ByteBuf) byteBuf).call()).isCancelled)
                            super.write(channel, byteBuf, promise);
                    }

                    @Override // In Packet
                    public void channelRead(ChannelHandlerContext channel, Object packet) throws Exception {
                        if (!((PlayInEvent) new PlayInEvent(player, (Packet<?>) packet).call()).isCancelled) {
                            super.channelRead(channel, packet);
                            if (packet instanceof PacketPlayInUseEntity) {
                                int id = (int) Reflections.getField(packet, "a");
                                for (NPC npc : NPC.all) {
                                    if (npc.entityPlayer.getId() == id && npc.event != null) {
                                        npc.event.run(player, ((PacketPlayInUseEntity) packet).a());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });

            if (pipeline.get("encoder") != null)
                pipeline.addAfter("encoder", "speedcubingLib-PacketEncode", new MessageToMessageEncoder<Packet<?>>() {
                    @Override // Out Packet
                    protected void encode(ChannelHandlerContext channel, Packet<?> packet, List<Object> arg) {
                        if (!((PlayOutEvent) new PlayOutEvent(player, packet).call()).isCancelled) {
                            arg.add(packet);
                            if (packet instanceof PacketPlayOutMapChunk) {
                                PacketPlayOutMapChunk chunk = (PacketPlayOutMapChunk) packet;
                                for (NPC npc : NPC.all) {
                                    if ((int) Reflections.getField(chunk, "a") == NumberConversions.floor(npc.entityPlayer.locX)>>4 ||
                                            (int) Reflections.getField(chunk, "b") == NumberConversions.floor(npc.entityPlayer.locZ)>>4) {
                                        System.out.println("B");
                                        if (npc.autoSpawn || npc.autoListen || npc.listener.contains(((CraftPlayer) player).getHandle().playerConnection)) {
                                            System.out.println("C");
                                            npc.setListenerValues(((CraftPlayer) player).getHandle().playerConnection).spawn().rollBackListenerValues();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
        }
    }
}
