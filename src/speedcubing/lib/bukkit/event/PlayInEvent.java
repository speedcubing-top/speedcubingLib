package speedcubing.lib.bukkit.event;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import net.minecraft.server.v1_8_R3.PacketPlayInTabComplete;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.entity.Player;
import speedcubing.lib.eventbus.LibEventManager;

public class PlayInEvent {
    public boolean isCancelled;
    public final Player player;
    public final Packet<?> packet;

    public PlayInEvent(Player player, Packet<?> packet) {
        this.player = player;
        this.packet = packet;
        Object event;
        if (packet instanceof PacketPlayInChat) {
            event = new PlayInChatEvent(player, (PacketPlayInChat) packet);
            LibEventManager.callEvent(event);
            isCancelled = ((PlayInChatEvent) event).isCancelled;
        } else if (packet instanceof PacketPlayInTabComplete) {
            event = new PlayInTabCompleteEvent(player, (PacketPlayInTabComplete) packet);
            LibEventManager.callEvent(event);
            isCancelled = ((PlayInTabCompleteEvent) event).isCancelled;
        } else if (packet instanceof PacketPlayInUseEntity) {
            event = new PlayInUseEntityEvent(player, (PacketPlayInUseEntity) packet);
            LibEventManager.callEvent(event);
            isCancelled = ((PlayInUseEntityEvent) event).isCancelled;
        }
    }
}
