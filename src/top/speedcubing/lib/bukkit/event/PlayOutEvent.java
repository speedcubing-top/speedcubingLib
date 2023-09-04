package top.speedcubing.lib.bukkit.event;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import top.speedcubing.lib.eventbus.*;

//EVENT of listening PlayOutPACKETS
public class PlayOutEvent extends CubingEvent {
    public boolean isCancelled;
    public final Packet<?> packet;
    public final Player player;

    public PlayOutEvent(Player player, Packet<?> packet) {
        this.packet = packet;
        this.player = player;
    }
}
