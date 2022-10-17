package top.speedcubing.lib.bukkit.listeners;

import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
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
            String from = e.getFrom().getName();
            String to = player.getWorld().getName();
            for (NPC npc : NPC.all) {
                if (npc.world.contains(from))
                    npc.removeListener(player);
                else if (npc.world.contains(to))
                    addNPC(player, npc);
            }
            for (Hologram hologram : Hologram.all) {
                if (hologram.world.contains(from))
                    hologram.removeListener(player);
                else if (hologram.world.contains(to))
                    addHologram(player, hologram);
            }
        }
    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        if (speedcubingLibBukkit.is1_8_8) {
            Player player = e.getPlayer();
            CubingLibPlayer.user.remove(player);
            NPC.all.forEach(a -> a.removeListener(player));
            Hologram.all.forEach(a -> a.removeListener(player));
        }
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        if (speedcubingLibBukkit.is1_8_8) {
            Player player = e.getPlayer();
            new CubingLibPlayer(player);
            String world = player.getWorld().getName();
            for (NPC npc : NPC.all) {
                if (npc.world.contains(world))
                    addNPC(player, npc);
            }
            for (Hologram hologram : Hologram.all) {
                if (hologram.world.contains(world))
                    addHologram(player, hologram);
            }
        }
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location to = e.getTo();
        String world = to.getWorld().getName();
        CubingLibPlayer cubingPlayer = CubingLibPlayer.get(player);
        if (cubingPlayer.getBossbar() != null) {
            Location newLocation = to.clone().add(to.getDirection().multiply(100));
            CubingLibPlayer.wither.setLocation(newLocation.getX(), newLocation.getY(), newLocation.getZ(), 0, 0);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(CubingLibPlayer.wither));
        }
        double xDiff;
        double zDiff;
        for (NPC npc : NPC.all) {
            if (npc.world.contains(world)) {
                if (npc.everyoneCanSee || npc.hasListener(player)) {
                    xDiff = npc.entityPlayer.locX - to.getX();
                    zDiff = npc.entityPlayer.locZ - to.getZ();
                    if (Math.sqrt(xDiff * xDiff + zDiff * zDiff) > 128) {
                        cubingPlayer.outRangeNPC.add(npc);
                        npc.setListener(player).despawn().undoSetListener();
                    } else if (cubingPlayer.outRangeNPC.contains(npc)) {
                        cubingPlayer.outRangeNPC.remove(npc);
                        int p = npc.ms;
                        npc.setHideFromTabDelay(-1).setListener(player).spawn().hideFromTab(50).undoSetListener().setHideFromTabDelay(p);
                    }
                }
            }
        }
        for (Hologram hologram : Hologram.all) {
            if (hologram.world.contains(world)) {
                if (hologram.everyoneCanSee || hologram.hasListener(player)) {
                    xDiff = hologram.armorStand.locX - to.getX();
                    zDiff = hologram.armorStand.locZ - to.getZ();
                    if (Math.sqrt(xDiff * xDiff + zDiff * zDiff) > 64) {
                        cubingPlayer.outRangeHologram.add(hologram);
                        hologram.setListener(player).despawn().undoSetListener();
                    } else if (cubingPlayer.outRangeHologram.contains(hologram)) {
                        cubingPlayer.outRangeHologram.remove(hologram);
                        hologram.setListener(player).spawn().undoSetListener();
                    }
                }
            }
        }

    }

    private void addHologram(Player player, Hologram hologram) {
        if (hologram.everyoneCanSee)
            hologram.addListener(player);
        if (hologram.autoSpawn && (hologram.everyoneCanSee || hologram.hasListener(player)))
            hologram.setListener(player).spawn().undoSetListener();
    }

    private void addNPC(Player player, NPC npc) {
        if (npc.everyoneCanSee)
            npc.addListener(player);
        if (npc.autoSpawn && (npc.everyoneCanSee || npc.hasListener(player)))
            npc.setListener(player).spawn().undoSetListener();
    }
}
