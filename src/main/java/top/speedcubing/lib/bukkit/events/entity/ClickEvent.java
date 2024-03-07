package top.speedcubing.lib.bukkit.events.entity;

import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.entity.Player;

public class ClickEvent {

    private final Player player;

    public Player getPlayer() {
        return player;
    }

    private final PacketPlayInUseEntity.EnumEntityUseAction action;

    public PacketPlayInUseEntity.EnumEntityUseAction getAction() {
        return action;
    }

    public ClickEvent(Player player, PacketPlayInUseEntity.EnumEntityUseAction action) {
        this.player = player;
        this.action = action;
    }
}