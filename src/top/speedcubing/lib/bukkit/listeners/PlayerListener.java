package top.speedcubing.lib.bukkit.listeners;

import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import top.speedcubing.lib.bukkit.CubingLibPlayer;
import top.speedcubing.lib.bukkit.entity.Hologram;
import top.speedcubing.lib.bukkit.entity.NPC;
import top.speedcubing.lib.speedcubingLibBukkit;

public class PlayerListener implements Listener {
    @EventHandler
    public void PlayerChangedWorldEvent(PlayerChangedWorldEvent e) {
        if (speedcubingLibBukkit.is1_8_8) {
            Player player = e.getPlayer();
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            String from = e.getFrom().getName();
            String to = player.getWorld().getName();
            for (NPC npc : NPC.all) {
                if (npc.world.contains(from))
                    npc.listener.remove(connection);
                else if (npc.world.contains(to))
                    addNPC(connection, npc);
            }
            for (Hologram hologram : Hologram.all) {
                if (hologram.world.contains(from))
                    hologram.listener.remove(connection);
                else if (hologram.world.contains(to))
                    addHologram(connection, hologram);
            }
        }
    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        if (speedcubingLibBukkit.is1_8_8) {
            CubingLibPlayer.user.remove(e.getPlayer());
            PlayerConnection connection = ((CraftPlayer) e.getPlayer()).getHandle().playerConnection;
            NPC.all.forEach(a -> a.listener.remove(connection));
            Hologram.all.forEach(a -> a.listener.remove(connection));
        }
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        if (speedcubingLibBukkit.is1_8_8) {
            Player player = e.getPlayer();
            new CubingLibPlayer(player);
            String world = player.getWorld().getName();
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            for (NPC npc : NPC.all) {
                if (npc.world.contains(world))
                    addNPC(connection, npc);
            }
            for (Hologram hologram : Hologram.all) {
                if (hologram.world.contains(world))
                    addHologram(connection, hologram);
            }
        }
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location to = e.getTo();
        String world = to.getWorld().getName();
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        CubingLibPlayer cubingPlayer = CubingLibPlayer.get(player);
        double xDiff;
        double zDiff;
        for (NPC npc : NPC.all) {
            if (npc.world.contains(world)) {
                if (npc.getAutoListen() || npc.listener.contains(connection)) {
                    xDiff = npc.entityPlayer.locX - to.getX();
                    zDiff = npc.entityPlayer.locZ - to.getZ();
                    if (Math.sqrt(xDiff * xDiff + zDiff * zDiff) > 128) {
                        cubingPlayer.outRangeNPC.add(npc);
                        npc.despawn();
                    } else if (cubingPlayer.outRangeNPC.contains(npc)) {
                        cubingPlayer.outRangeNPC.remove(npc);
                        npc.spawn().hideFromTab(50);
                    }
                }
            }
        }
        for (Hologram hologram : Hologram.all) {
            if (hologram.world.contains(world)) {
                if (hologram.getAutoListen() || hologram.listener.contains(connection)) {
                    xDiff = hologram.armorStand.locX - to.getX();
                    zDiff = hologram.armorStand.locZ - to.getZ();
                    if (Math.sqrt(xDiff * xDiff + zDiff * zDiff) > 64) {
                        cubingPlayer.outRangeHologram.add(hologram);
                        hologram.despawn();
                    } else if (cubingPlayer.outRangeHologram.contains(hologram)) {
                        cubingPlayer.outRangeHologram.remove(hologram);
                        hologram.spawn();
                    }
                }
            }
        }
    }

    private void addHologram(PlayerConnection connection, Hologram hologram) {
        if (hologram.getAutoListen())
            hologram.listener.add(connection);
        if (hologram.getAutoSpawn())
            hologram.setListenerValues(connection).spawn().rollBackListenerValues();
    }

    private void addNPC(PlayerConnection connection, NPC npc) {
        if (npc.getAutoListen())
            npc.listener.add(connection);
        if (npc.getAutoSpawn())
            npc.setListenerValues(connection).spawn().rollBackListenerValues();
    }
}
