package cubing.bukkit;

import cubing.bukkit.Event.ServerEventManager;
import cubing.bukkit.Event.events.PlayInChatEvent;
import cubing.bukkit.Event.events.PlayInUseEntityEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class PacketListner implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        if (channel.pipeline().get("InUseEntity") == null) {
            channel.pipeline().addAfter("decoder", "InUseEntity", new MessageToMessageDecoder<PacketPlayInUseEntity>() {
                @Override
                protected void decode(ChannelHandlerContext channel, PacketPlayInUseEntity packet, List<Object> arg) {
                    arg.add(packet);
                    ServerEventManager.callEvent(new PlayInUseEntityEvent(player, channel, packet, arg));
                }
            });
        }
        if (channel.pipeline().get("InChat") == null) {
            channel.pipeline().addAfter("decoder", "InChat", new MessageToMessageDecoder<PacketPlayInChat>() {
                @Override
                protected void decode(ChannelHandlerContext channel, PacketPlayInChat packet, List<Object> arg) {
                    arg.add(packet);
                    ServerEventManager.callEvent(new PlayInChatEvent(player, channel, packet, arg));
                }
            });
        }
    }
}
