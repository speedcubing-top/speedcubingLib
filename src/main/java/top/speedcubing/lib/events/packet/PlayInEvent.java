package top.speedcubing.lib.events.packet;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import top.speedcubing.lib.eventbus.CubingEvent;

//EVENT of listening PlayInPACKETS
public class PlayInEvent extends CubingEvent {
    public boolean isCancelled;
    public final Player player;
    public final Packet<?> packet;

    public PlayInEvent(Player player, Packet<?> packet) {
        this.player = player;
        this.packet = packet;
    }
}
