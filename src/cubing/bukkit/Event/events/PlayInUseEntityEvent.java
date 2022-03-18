package cubing.bukkit.Event.events;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayInUseEntityEvent {
    public ChannelHandlerContext channel;
    public PacketPlayInUseEntity packet;
    public Player player;
    private final List<Object> arg;
    private boolean isCancelled;

    public PlayInUseEntityEvent(Player player, ChannelHandlerContext channel, PacketPlayInUseEntity packet, List<Object> arg) {
        this.channel = channel;
        this.packet = packet;
        this.arg = arg;
        this.player = player;
    }

    public ChannelHandlerContext getChannel() {
        return channel;
    }

    public PacketPlayInUseEntity getPacket() {
        return packet;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean b) {
        if (b && !isCancelled) {
            isCancelled = true;
            arg.remove(packet);
        } else if (!b && isCancelled) {
            isCancelled = false;
            arg.add(packet);
        }
    }
}
