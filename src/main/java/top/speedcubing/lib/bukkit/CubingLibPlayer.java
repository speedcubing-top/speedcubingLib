package top.speedcubing.lib.bukkit;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import top.speedcubing.lib.bukkit.entity.Hologram;
import top.speedcubing.lib.bukkit.entity.NPC;
import top.speedcubing.lib.bukkit.event.PlayInByteEvent;
import top.speedcubing.lib.bukkit.event.PlayInEvent;
import top.speedcubing.lib.bukkit.event.PlayOutByteEvent;
import top.speedcubing.lib.bukkit.event.PlayOutEvent;
import top.speedcubing.lib.utils.Reflections;

public class CubingLibPlayer {
    public static void init() {
        wither = new EntityWither(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle());
        wither.setInvisible(true);
    }

    public static EntityWither wither;

    public static Map<Player, CubingLibPlayer> user = new HashMap<>();

    public static CubingLibPlayer get(Player player) {
        return user.get(player);
    }

    public static Collection<CubingLibPlayer> getPlayers() {
        return user.values();
    }

    SideBar sideBar;
    Player player;

    String bossbar;

    public CubingLibPlayer(Player player) {
        this.player = player;
        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
        ByteToMessageDecoder byteToMessageDecoder = new ByteToMessageDecoder() {
            @Override // In Byte
            protected void decode(ChannelHandlerContext channel, ByteBuf byteBuf, List<Object> arg) {
                if (byteBuf == null)
                    return;
                try {
                    if (!((PlayInByteEvent) new PlayInByteEvent(player, byteBuf).call()).isCancelled)
                        arg.add(byteBuf.readBytes(byteBuf.readableBytes()));
                    else
                        byteBuf.skipBytes(byteBuf.readableBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        if (pipeline.get("decompress") != null)
            pipeline.addAfter("decompress", "speedcubingLib-ByteDecode", byteToMessageDecoder);
        else if (pipeline.get("splitter") != null)
            pipeline.addAfter("splitter", "speedcubingLib-ByteDecode", byteToMessageDecoder);
        if (pipeline.get("decoder") != null)
            pipeline.addAfter("decoder", "speedcubingLib-channel", new ChannelDuplexHandler() {
                @Override // Out Byte
                public void write(ChannelHandlerContext channel, Object byteBuf, ChannelPromise promise) {
                    if (byteBuf == null)
                        return;
                    try {
                        if (!((PlayOutByteEvent) new PlayOutByteEvent(player, (ByteBuf) byteBuf).call()).isCancelled)
                            super.write(channel, byteBuf, promise);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // In Packet
                public void channelRead(ChannelHandlerContext channel, Object packet) {
                    if (packet == null)
                        return;
                    try {
                        if (!((PlayInEvent) new PlayInEvent(player, (Packet<?>) packet).call()).isCancelled) {
                            super.channelRead(channel, packet);
                            if (packet instanceof PacketPlayInUseEntity) {
                                int id = (int) Reflections.getField(packet, "a");
                                for (NPC npc : NPC.all) {
                                    if (npc.entityPlayer.getId() == id && npc.getClickEvent() != null) {
                                        npc.getClickEvent().run(player, ((PacketPlayInUseEntity) packet).a());
                                        break;
                                    }
                                }
                                for (Hologram hologram : Hologram.all) {
                                    if (hologram.armorStand.getId() == id && hologram.getClickEvent() != null) {
                                        hologram.getClickEvent().run(player, ((PacketPlayInUseEntity) packet).a());
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        if (pipeline.get("encoder") != null)
            pipeline.addAfter("encoder", "speedcubingLib-PacketEncode", new MessageToMessageEncoder<Packet<?>>() {
                @Override // Out Packet
                protected void encode(ChannelHandlerContext channel, Packet<?> packet, List<Object> arg) {
                    if (packet == null)
                        return;
                    try {
                        if (!((PlayOutEvent) new PlayOutEvent(player, packet).call()).isCancelled)
                            arg.add(packet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        user.put(player, this);
    }

    public SideBar getSideBar() {
        return sideBar;
    }

    public void setSideBar(SideBar sideBar) {
        this.sideBar = sideBar;
    }

    public String getBossbar() {
        return bossbar;
    }

    public void setBossbar(String bossbar) {
        this.bossbar = bossbar;
        Location location = player.getLocation();
        Location newLocation = location.clone().add(location.getDirection().multiply(100));
        wither.setCustomName(bossbar);
        wither.setLocation(newLocation.getX(), newLocation.getY(), newLocation.getZ(), 0, 0);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(wither));
    }
}
