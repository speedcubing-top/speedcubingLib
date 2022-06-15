package speedcubing.lib.bukkit.listeners;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import net.minecraft.server.v1_8_R3.PacketPlayInTabComplete;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import speedcubing.lib.bukkit.event.PlayInChatEvent;
import speedcubing.lib.bukkit.event.PlayInTabCompleteEvent;
import speedcubing.lib.bukkit.event.PlayInUseEntityEvent;
import speedcubing.lib.eventbus.LibEventManager;

import java.util.List;

public class PacketListener implements Listener {
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        if (channel.pipeline().get("speedcubingLib-PacketPlayOutInUseEntity") != null)
            return;
        channel.pipeline().addAfter("decoder", "speedcubingLib-PacketPlayInUseEntity", new MessageToMessageDecoder<PacketPlayInUseEntity>() {
            @Override
            protected void decode(ChannelHandlerContext channel, PacketPlayInUseEntity packet, List<Object> arg) {
                PlayInUseEntityEvent event = new PlayInUseEntityEvent(player, packet);
                LibEventManager.callEvent(event);
                if (!event.isCancelled)
                    arg.add(packet);
            }
        }).addAfter("decoder", "speedcubingLib-PacketPlayInChat", new MessageToMessageDecoder<PacketPlayInChat>() {
            @Override
            protected void decode(ChannelHandlerContext channel, PacketPlayInChat packet, List<Object> arg) {
                PlayInChatEvent event = new PlayInChatEvent(player, packet);
                LibEventManager.callEvent(event);
                if (!event.isCancelled)
                    arg.add(packet);
            }
        }).addAfter("decoder", "speedcubingLib-PacketPlayInTabComplete", new MessageToMessageDecoder<PacketPlayInTabComplete>() {
            @Override
            protected void decode(ChannelHandlerContext channel, PacketPlayInTabComplete packet, List<Object> arg) {
                PlayInTabCompleteEvent event = new PlayInTabCompleteEvent(player, packet);
                LibEventManager.callEvent(event);
                if (!event.isCancelled)
                    arg.add(packet);
            }
        });
    }
}
