package top.speedcubing.lib.bukkit.event;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;

public class PlayOutEvent {
    public boolean isCancelled;
    public final Packet<?> packet;
    public final Player player;

    public PlayOutEvent(Player player, Packet<?> packet) {
        this.packet = packet;
        this.player = player;
    }
}
