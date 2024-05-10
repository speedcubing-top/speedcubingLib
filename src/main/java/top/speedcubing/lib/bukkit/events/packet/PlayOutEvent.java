package top.speedcubing.lib.bukkit.events.packet;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import top.speedcubing.lib.eventbus.CubingEvent;

//EVENT of listening PlayOutPACKETS
public class PlayOutEvent extends CubingEvent implements Cancellable {
    private boolean cancel;
    private final Packet<?> packet;
    private final Player player;

    public PlayOutEvent(Player player, Packet<?> packet) {
        this.packet = packet;
        this.player = player;
    }


    public Player getPlayer() {
        return player;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
