package top.speedcubing.lib.events;

import org.bukkit.entity.Player;

import java.util.List;

public class SignUpdateEvent {
    private final Player player;

    public Player getPlayer() {
        return player;
    }

    private final List<String> lines;

    public List<String> getLines() {
        return lines;
    }

    public SignUpdateEvent(Player player, List<String> lines) {
        this.player = player;
        this.lines = lines;
    }
}
