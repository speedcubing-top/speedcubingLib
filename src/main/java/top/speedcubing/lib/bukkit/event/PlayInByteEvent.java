package top.speedcubing.lib.bukkit.event;

import io.netty.buffer.ByteBuf;
import org.bukkit.entity.Player;
import top.speedcubing.lib.eventbus.CubingEvent;

//EVENT of listening PlayInBytes
public class PlayInByteEvent extends CubingEvent {
    public boolean isCancelled;
    public final Player player;
    public final ByteBuf byteBuf;

    public PlayInByteEvent(Player player, ByteBuf byteBuf) {
        this.player = player;
        this.byteBuf = byteBuf;
    }
}
