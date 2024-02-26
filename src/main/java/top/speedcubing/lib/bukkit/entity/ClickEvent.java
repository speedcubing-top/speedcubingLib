package top.speedcubing.lib.bukkit.entity;

import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.entity.Player;

public interface ClickEvent {
    void run(Player player, PacketPlayInUseEntity.EnumEntityUseAction action);
}
