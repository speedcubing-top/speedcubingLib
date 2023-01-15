package top.speedcubing.lib.bukkit.event;

import io.netty.buffer.ByteBuf;
import org.bukkit.entity.Player;
import top.speedcubing.lib.eventbus.LibEventManager;

//EVENT of listening PlayOutBytes
public class PlayOutByteEvent extends LibEventManager {
    public boolean isCancelled;
    public final Player player;
    public final ByteBuf byteBuf;

    public PlayOutByteEvent(Player player, ByteBuf byteBuf) {
        this.player = player;
        this.byteBuf = byteBuf;
    }
}
