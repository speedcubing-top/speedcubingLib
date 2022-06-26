package speedcubing.lib.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import speedcubing.lib.bukkit.SideBar;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        SideBar.perPlayerSidebar.remove(e.getPlayer());
    }
}
