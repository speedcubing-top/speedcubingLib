package speedcubing.lib.bukkit.event;

import net.minecraft.server.v1_8_R3.PacketPlayInTabComplete;
import org.bukkit.entity.Player;

public class PlayInTabCompleteEvent {
    public final PacketPlayInTabComplete packet;
    public final Player player;
    public boolean isCancelled = false;

    public PlayInTabCompleteEvent(Player player, PacketPlayInTabComplete packet) {
        this.player = player;
        this.packet = packet;
    }
}