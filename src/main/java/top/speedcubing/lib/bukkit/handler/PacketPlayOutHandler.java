package top.speedcubing.lib.bukkit.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import top.speedcubing.lib.bukkit.events.packet.PlayOutEvent;

public class PacketPlayOutHandler extends MessageToMessageEncoder<Packet<?>> {

    private final Player player;

    public PacketPlayOutHandler(Player player) {
        this.player = player;
    }

    @Override // Out Packet
    protected void encode(ChannelHandlerContext channel, Packet<?> packet, List<Object> arg) {
        if (packet == null)
            return;
        try {
            if (!((PlayOutEvent) new PlayOutEvent(player, packet).call()).isCancelled())
                arg.add(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}