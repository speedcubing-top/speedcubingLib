package top.speedcubing.lib.bukkit.event;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;

public class PlayInEvent {
    public boolean isCancelled;
    public final Player player;
    public final Packet<?> packet;

    public PlayInEvent(Player player, Packet<?> packet) {
        this.player = player;
        this.packet = packet;
    }
}
