package speedcubing.lib.bukkit.event;

import io.netty.buffer.ByteBuf;
import org.bukkit.entity.Player;

public class PlayInByteEvent {
    public boolean isCancelled;
    public final Player player;
    public final ByteBuf byteBuf;

    public PlayInByteEvent(Player player, ByteBuf byteBuf) {
        this.player = player;
        this.byteBuf = byteBuf;
    }
}
