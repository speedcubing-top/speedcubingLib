package top.speedcubing.lib.bukkit.entity;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import top.speedcubing.lib.events.entity.ClickEvent;
import top.speedcubing.lib.utils.collection.Sets;

public class Hologram {
    public Hologram setClickEvent(Consumer<ClickEvent> e) {
        armorStand.n(false);
        this.event = e;
        return this;
    }

    public Consumer<ClickEvent> getClickEvent() {
        return event;
    }

    Set<PlayerConnection> temp = new java.util.HashSet<>();

    public Hologram addListener(Player... players) {
        for (Player p : players) {
            listener.add(((CraftPlayer) p).getHandle().playerConnection);
        }
        return this;
    }

    public Hologram addListener(Collection<Player> players) {
        players.forEach(p -> listener.add(((CraftPlayer) p).getHandle().playerConnection));
        return this;
    }


    public Hologram setListener(Player... players) {
        temp = listener;
        listener = new java.util.HashSet<>();
        for (Player p : players) {
            listener.add(((CraftPlayer) p).getHandle().playerConnection);
        }
        return this;
    }

    public Hologram setListener(Collection<Player> players) {
        temp = listener;
        listener = new java.util.HashSet<>();
        for (Player p : players) {
            listener.add(((CraftPlayer) p).getHandle().playerConnection);
        }
        return this;
    }

    public boolean hasListener(Player player) {
        return listener.contains(((CraftPlayer) player).getHandle().playerConnection);
    }

    public Hologram removeListener(Player... players) {
        for (Player p : players) {
            listener.remove(((CraftPlayer) p).getHandle().playerConnection);
        }
        return this;
    }

    public Hologram undoSetListener() {
        listener = temp;
        temp = null;
        return this;
    }

    public void delete() {
        all.remove(this);
    }

    public Hologram world(String... world) {
        this.world.addAll(Sets.hashSet(world));
        return this;
    }

    public static final Set<Hologram> all = new java.util.HashSet<>();
    public Set<PlayerConnection> listener = new java.util.HashSet<>();
    public final Set<String> world = new java.util.HashSet<>();
    Consumer<ClickEvent> event;
    public final boolean autoSpawn;
    public final boolean everyoneCanSee;
    public final EntityArmorStand armorStand;

    public Hologram(String name, boolean everyoneCanSee, boolean autoSpawn) {
        if (everyoneCanSee)
            Bukkit.getOnlinePlayers().forEach(a -> listener.add(((CraftPlayer) a).getHandle().playerConnection));
        this.everyoneCanSee = everyoneCanSee;
        this.autoSpawn = autoSpawn;
        armorStand = new EntityArmorStand(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle());
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(true);
        armorStand.setInvisible(true);
        armorStand.n(true);
        armorStand.setCustomName(name);
        all.add(this);
    }

    public Hologram setLocation(double x, double y, double z, float yaw, float pitch) {
        armorStand.setLocation(x, y, z, yaw, pitch);
        return this;
    }

    public Hologram setLocation(Location location) {
        world.add(location.getWorld().getName());
        return setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public Hologram spawn() {
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armorStand);
        listener.forEach(a -> a.sendPacket(packet));
        return this;
    }

    public Hologram despawn() {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(armorStand.getId());
        listener.forEach(a -> a.sendPacket(packet));
        return this;
    }


    public Hologram setName(String name) {
        armorStand.setCustomName(name);
        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(armorStand.getId(), armorStand.getDataWatcher(), true);
        listener.forEach(a -> a.sendPacket(packet));
        return this;
    }
}
