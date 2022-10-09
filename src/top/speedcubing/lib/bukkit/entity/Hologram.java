package top.speedcubing.lib.bukkit.entity;

import com.google.common.collect.Sets;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import top.speedcubing.lib.bukkit.CubingLibPlayer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Hologram {

    Set<PlayerConnection> temp;

    public Hologram addListener(PlayerConnection... connections) {
        listener.addAll(Sets.newHashSet(connections));
        return this;
    }

    public Hologram addListener(Collection<PlayerConnection> connections) {
        listener.addAll(connections);
        return this;
    }

    public Hologram setListener(PlayerConnection... connections) {
        temp = listener;
        listener = Sets.newHashSet(connections);
        return this;
    }

    public Hologram undoSetListener() {
        listener = temp;
        temp = null;
        return this;
    }

    public void delete() {
        CubingLibPlayer.user.values().forEach(a -> a.outRangeHologram.remove(this));
        all.remove(this);
    }

    public Hologram world(String... world) {
        this.world.addAll(Sets.newHashSet(world));
        return this;
    }

    public static final Set<Hologram> all = new HashSet<>();
    public Set<PlayerConnection> listener = new HashSet<>();
    public final Set<String> world = new HashSet<>();
    public final boolean autoSpawn;
    public final boolean everyoneCanSee;
    public final EntityArmorStand armorStand;

    public Hologram(String name, boolean everyoneCanSee, boolean autoSpawn) {
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
