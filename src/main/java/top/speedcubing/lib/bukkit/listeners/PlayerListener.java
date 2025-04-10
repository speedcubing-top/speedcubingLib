package top.speedcubing.lib.bukkit.listeners;

import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import top.speedcubing.lib.bukkit.CubingLibPlayer;
import top.speedcubing.lib.bukkit.entity.Hologram;
import top.speedcubing.lib.bukkit.entity.NPC;
import top.speedcubing.lib.bukkit.inventory.InventoryListener;

public class PlayerListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void InventoryClickEvent(InventoryClickEvent e) {
        int slot = e.getRawSlot();
        if (slot == -999 || e.getClickedInventory() == null) {
            return;
        }
        InventoryListener.inventoryClickEvent(e);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void InventoryCloseEvent(InventoryCloseEvent e) {
        InventoryListener.inventoryCloseEvent(e);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerChangedWorldEvent(PlayerChangedWorldEvent e) {
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

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        CubingLibPlayer.user.remove(player);
        NPC.all.forEach(a -> a.removeListener(player));
        Hologram.all.forEach(a -> a.removeListener(player));
        InventoryListener.playerQuitEvent(e);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        new CubingLibPlayer(player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerJoinEvent2(PlayerJoinEvent e) {
        Player player = e.getPlayer();
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

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location to = e.getTo();
        Location from = e.getFrom();
        String world = to.getWorld().getName();
        CubingLibPlayer cubingPlayer = CubingLibPlayer.get(player);
        if (cubingPlayer.getBossbar() != null) {
            Location newLocation = to.clone().add(to.getDirection().multiply(100));
            CubingLibPlayer.sharedWither.setLocation(newLocation.getX(), newLocation.getY(), newLocation.getZ(), 0, 0);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(CubingLibPlayer.sharedWither));
        }
        double xDiffFrom;
        double zDiffFrom;
        double xDiffTo;
        double zDiffTo;
        double fromDiff;
        double toDiff;
        for (NPC npc : NPC.all) {
            if (npc.world.contains(world)) {
                if (npc.everyoneCanSee || npc.hasListener(player)) {
                    xDiffTo = npc.entityPlayer.locX - to.getX();
                    zDiffTo = npc.entityPlayer.locZ - to.getZ();
                    xDiffFrom = npc.entityPlayer.locX - from.getX();
                    zDiffFrom = npc.entityPlayer.locZ - from.getZ();
                    fromDiff = xDiffFrom * xDiffFrom + zDiffFrom * zDiffFrom;
                    toDiff = xDiffTo * xDiffTo + zDiffTo * zDiffTo;
                    if (toDiff > 128 * 128 && fromDiff < 128 * 128) {
                        npc.setListener(player).despawn().undoSetListener();
                    } else if (toDiff < 128 * 128 && fromDiff > 128 * 128) {
                        int p = npc.ms;
                        npc.setHideFromTabDelay(-1).setListener(player).spawn().hideFromTab(50).undoSetListener().setHideFromTabDelay(p);
                    }
                }
            }
        }
        for (Hologram hologram : Hologram.all) {
            if (hologram.world.contains(world)) {
                if (hologram.everyoneCanSee || hologram.hasListener(player)) {
                    xDiffTo = hologram.armorStand.locX - to.getX();
                    zDiffTo = hologram.armorStand.locZ - to.getZ();
                    xDiffFrom = hologram.armorStand.locX - from.getX();
                    zDiffFrom = hologram.armorStand.locZ - from.getZ();
                    fromDiff = xDiffFrom * xDiffFrom + zDiffFrom * zDiffFrom;
                    toDiff = xDiffTo * xDiffTo + zDiffTo * zDiffTo;
                    if (toDiff > 64 * 64 && fromDiff < 64 * 64) {
                        hologram.setListener(player).despawn().undoSetListener();
                    } else if (toDiff < 64 * 64 && fromDiff > 64 * 64) {
                        hologram.setListener(player).spawn().undoSetListener();
                    }
                }
            }
        }
//        System.out.println(t - System.currentTimeMillis());
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
