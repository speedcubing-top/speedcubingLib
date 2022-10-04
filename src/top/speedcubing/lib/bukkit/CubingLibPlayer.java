package top.speedcubing.lib.bukkit;

import org.bukkit.entity.Player;
import top.speedcubing.lib.bukkit.entity.NPC;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CubingLibPlayer {

    public static Map<Player, CubingLibPlayer> user = new HashMap<>();

    public static CubingLibPlayer get(Player player) {
        return user.get(player);
    }

    public final Set<NPC> outRange = new HashSet<>();

    SideBar sideBar;

    public CubingLibPlayer(Player player) {
        user.put(player, this);
    }

    public SideBar getSideBar() {
        return sideBar;
    }

    public void setSideBar(SideBar sideBar) {
        this.sideBar = sideBar;
    }
}
