package speedcubing.lib.bukkit.event;

import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.entity.Player;

public class PlayInUseEntityEvent {
    public final PacketPlayInUseEntity packet;
    public final Player player;
    public boolean isCancelled = false;

    public PlayInUseEntityEvent(Player player, PacketPlayInUseEntity packet) {
        this.packet = packet;
        this.player = player;
    }
}
