package speedcubing.lib.bukkit.entity;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.util.HashSet;
import java.util.Set;

public class Hologram {
    public Set<PlayerConnection> listener = new HashSet<>();
    public EntityArmorStand armorStand;

    public Hologram(String name, double x, double y, double z, float yaw, float pitch) {
        armorStand = new EntityArmorStand(((CraftWorld) Bukkit.getWorld("world")).getHandle());
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(true);
        armorStand.setInvisible(true);
        armorStand.n(true);
        armorStand.setCustomName(name);
        armorStand.setLocation(x, y, z, yaw, pitch);
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


    public Hologram setName(String name, boolean update) {
        armorStand.setCustomName(name);
        if (update) {
            PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(armorStand.getId(), armorStand.getDataWatcher(), true);
            listener.forEach(a -> a.sendPacket(packet));
        }
        return this;
    }
}
