package speedcubing.lib.bukkit.event;

import io.netty.buffer.ByteBuf;
import org.bukkit.entity.Player;

public class PlayOutByteEvent {
    public boolean isCancelled;
    public final Player player;
    public final ByteBuf byteBuf;

    public PlayOutByteEvent(Player player, ByteBuf byteBuf) {
        this.player = player;
        this.byteBuf = byteBuf;
    }
}
