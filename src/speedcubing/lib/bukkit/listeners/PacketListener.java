package speedcubing.lib.bukkit.listeners;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import speedcubing.lib.bukkit.entity.NPC;
import speedcubing.lib.bukkit.event.PlayInEvent;
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
            if (channel.pipeline().get("speedcubingLib-decoder") != null)
                return;
            channel.pipeline().addAfter("decoder", "speedcubingLib-decoder", new MessageToMessageDecoder<Packet<?>>() {
                @Override
                protected void decode(ChannelHandlerContext channel, Packet<?> packet, List<Object> arg) {
                    PlayInEvent event = new PlayInEvent(player, packet);
                    LibEventManager.callEvent(event);
                    if (!event.isCancelled) {
                        arg.add(packet);
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
            }).addAfter("encoder", "speedcubingLib-encoder", new MessageToMessageEncoder<Packet<?>>() {
                @Override
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
