package speedcubing.lib.bukkit.entity;

import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import speedcubing.lib.api.MojangAPI;
import speedcubing.lib.api.SessionServer;
import speedcubing.lib.bukkit.packetwrapper.OutScoreboardTeam;
import speedcubing.lib.utils.Reflections;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NPC {
    public interface ClickEvent {
        void run(Player player, PacketPlayInUseEntity.EnumEntityUseAction action);
    }

    public static final Set<NPC> all = new HashSet<>();
    public final Set<PlayerConnection> listener = new HashSet<>();
    public final Set<String> world = new HashSet<>();
    public EntityPlayer entityPlayer;
    public ClickEvent event;

    public boolean autoSpawn;
    public boolean autoListen = true;

    private boolean nameTagHidden;
    private float spawnBodyYaw;
    private ItemStack itemInHand;

    public NPC(String name, UUID uuid, boolean enableOuterLayerSkin) {
        WorldServer world = ((CraftWorld) Bukkit.getWorld("world")).getHandle();
        entityPlayer = new EntityPlayer(((CraftServer) Bukkit.getServer()).getServer(), world, new GameProfile(uuid == null ? UUID.randomUUID() : uuid, name), new PlayerInteractManager(world));
        if (enableOuterLayerSkin)
            entityPlayer.getDataWatcher().watch(10, (byte) 127);
        all.add(this);
    }

    public void delete() {
        all.remove(this);
    }

    public NPC setClickEvent(ClickEvent e) {
        this.event = e;
        return this;
    }

    public NPC setAutoSpawn(boolean autoSpawn) {
        this.autoSpawn = autoSpawn;
        return this;
    }

    public NPC setAutoListen(boolean autoListen) {
        this.autoListen = autoListen;
        listener.clear();
        return this;
    }

    public NPC setSpawnBodyYaw(float spawnBodyYaw) {
        this.spawnBodyYaw = spawnBodyYaw;
        return this;
    }

    public NPC animation(int animation) {
        PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
        Reflections.setField(packet, "a", entityPlayer.getId());
        Reflections.setField(packet, "b", (byte) animation);
        listener.forEach(a -> a.sendPacket(packet));
        return this;
    }

    public NPC status(int status) {
        PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus();
        Reflections.setField(packet, "a", entityPlayer.getId());
        Reflections.setField(packet, "b", (byte) status);
        listener.forEach(a -> a.sendPacket(packet));
        return this;
    }

    public NPC setItemInHand(ItemStack itemInHand) {
        this.itemInHand = itemInHand == null || itemInHand.getType().equals(Material.AIR) ? null : itemInHand;
        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(entityPlayer.getId(), 0, CraftItemStack.asNMSCopy(itemInHand));
        listener.forEach(a -> a.sendPacket(packet));
        return this;
    }

    public NPC spawn() {
        float yaw = entityPlayer.yaw;
        entityPlayer.yaw = spawnBodyYaw;
        PacketPlayOutPlayerInfo p1 = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
        PacketPlayOutNamedEntitySpawn p2 = new PacketPlayOutNamedEntitySpawn(entityPlayer);
        PacketPlayOutEntityHeadRotation p3 = new PacketPlayOutEntityHeadRotation(entityPlayer, (byte) ((int) (entityPlayer.yaw * 256 / 360)));
        listener.forEach(a -> {
                    a.sendPacket(p1);
                    a.sendPacket(p2);
                    a.sendPacket(p3);
                }
        );
        if (nameTagHidden)
            hideNametag();
        if (itemInHand != null)
            setItemInHand(itemInHand);
        entityPlayer.yaw = yaw;
        return this;
    }

    public NPC setSkin(UUID uuid) {
        String[] skin = SessionServer.getSkin(uuid);
        return setSkin(skin[0], skin[1]);
    }

    public NPC setSkin(String name) {
        return setSkin(UUID.fromString(MojangAPI.getUUID(name)));
    }

    public NPC setSkin(String value, String signature) {
        GameProfile gameProfile = entityPlayer.getProfile();
        gameProfile.getProperties().removeAll("textures");
        gameProfile.getProperties().put("textures", new Property("textures", value, signature));
        if (!listener.isEmpty()) {
            despawn();
            spawn();
            updateNpcLocation();
        }
        return this;
    }

    public NPC despawn() {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(entityPlayer.getId());
        listener.forEach(a -> a.sendPacket(packet));
        return this;
    }

    public NPC setLocation(double x, double y, double z, float headRotation, float pitch) {
        entityPlayer.setLocation(x, y, z, headRotation, pitch);
        return this;
    }

    public NPC setLocation(Location location) {
        world.add(location.getWorld().getName());
        return setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public NPC hideNametag() {
        this.nameTagHidden = true;
        PacketPlayOutScoreboardTeam p1 = new OutScoreboardTeam().a("").e("never").h(1).packet;
        PacketPlayOutScoreboardTeam p2 = new OutScoreboardTeam().a("").e("never").packet;
        PacketPlayOutScoreboardTeam p3 = new OutScoreboardTeam().a("").g(Collections.singletonList(entityPlayer.getName())).h(3).packet;
        listener.forEach(a -> {
            a.sendPacket(p1);
            a.sendPacket(p2);
            a.sendPacket(p3);
        });
        return this;
    }

    public NPC updateNpcLocation() {
        PacketPlayOutEntityTeleport p1 = new PacketPlayOutEntityTeleport(entityPlayer.getId(), MathHelper.floor(entityPlayer.locX * 32), MathHelper.floor(entityPlayer.locY * 32), MathHelper.floor(entityPlayer.locZ * 32), (byte) ((int) (entityPlayer.yaw * 256 / 360)), (byte) ((int) (entityPlayer.pitch * 256 / 360)), true);
        PacketPlayOutEntity.PacketPlayOutEntityLook p2 = new PacketPlayOutEntity.PacketPlayOutEntityLook(entityPlayer.getId(), (byte) ((int) (entityPlayer.yaw * 256F / 360F)), (byte) ((int) (entityPlayer.pitch * 256F / 360F)), true);
        PacketPlayOutEntityHeadRotation p3 = new PacketPlayOutEntityHeadRotation(entityPlayer, (byte) ((int) (entityPlayer.yaw * 256F / 360F)));
        listener.forEach(a -> {
                    a.sendPacket(p1);
                    a.sendPacket(p2);
                    a.sendPacket(p3);
                }
        );
        return this;
    }

    private final Set<PlayerConnection> temp = new HashSet<>();

    public NPC setListenerValues(PlayerConnection... connections) {
        temp.addAll(listener);
        listener.clear();
        listener.addAll(Sets.newHashSet(connections));
        return this;
    }

    public NPC rollBackListenerValues() {
        listener.clear();
        listener.addAll(temp);
        temp.clear();
        return this;
    }
}
