package speedcubing.lib.bukkit.entity;

import com.google.common.collect.Sets;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Hologram {
    public interface ClickEvent {
        void run(Player player);
    }

    public static final Set<Hologram> all = new HashSet<>();
    public Set<PlayerConnection> listener = new HashSet<>();
    public EntityArmorStand armorStand;
    public final Set<String> world = new HashSet<>();
    public ClickEvent event;
    public boolean autoSpawn;

    public Hologram(String name, double x, double y, double z, float yaw, float pitch) {
        armorStand = new EntityArmorStand(((CraftWorld) Bukkit.getWorld("world")).getHandle());
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(true);
        armorStand.setInvisible(true);
        armorStand.n(true);
        armorStand.setCustomName(name);
        armorStand.setLocation(x, y, z, yaw, pitch);
        all.add(this);
    }

    public void delete() {
        all.remove(this);
    }

    public Hologram setClickEvent(ClickEvent e) {
        this.event = e;
        return this;
    }

    public Hologram setAutoSpawn(boolean autoSpawn) {
        this.autoSpawn = autoSpawn;
        return this;
    }

    public Hologram setLocation(double x, double y, double z, float yaw, float pitch) {
        armorStand.setLocation(x, y, z, yaw, pitch);
        return this;
    }

    public Hologram setLocation(Location location) {
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

    private Set<PlayerConnection> temp = new HashSet<>();

    public Hologram setListenerValues(PlayerConnection... connections) {
        temp.addAll(listener);
        listener.clear();
        listener.addAll(Sets.newHashSet(connections));
        return this;
    }

    public Hologram rollBackListenerValues() {
        listener.clear();
        listener.addAll(temp);
        temp.clear();
        return this;
    }
}
