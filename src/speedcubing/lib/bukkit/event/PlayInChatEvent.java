package speedcubing.lib.bukkit.event;

import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import org.bukkit.entity.Player;

public class PlayInChatEvent {
    public final PacketPlayInChat packet;
    public final Player player;
    public boolean isCancelled = false;

    public PlayInChatEvent(Player player, PacketPlayInChat packet) {
        this.packet = packet;
        this.player = player;
    }
}