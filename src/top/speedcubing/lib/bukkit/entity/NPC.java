package top.speedcubing.lib.bukkit.entity;

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
import top.speedcubing.lib.api.MojangAPI;
import top.speedcubing.lib.api.mojang.ProfileSkin;
import top.speedcubing.lib.bukkit.CubingLibPlayer;
import top.speedcubing.lib.bukkit.packetwrapper.OutScoreboardTeam;
import top.speedcubing.lib.utils.Reflections;

import java.util.*;

public class NPC {

    public static final Set<NPC> all = new HashSet<>();
    public Set<PlayerConnection> listener = new HashSet<>();
    public final Set<String> world = new HashSet<>();
    boolean autoSpawn;
    boolean autoListen = true;
    public final EntityPlayer entityPlayer;
    boolean nameTagHidden;
    float spawnBodyYaw;
    ItemStack itemInHand;
    ClickEvent event;
    int ms = 4000;

    public interface ClickEvent {
        void run(Player player, PacketPlayInUseEntity.EnumEntityUseAction action);
    }

    public NPC setClickEvent(ClickEvent e) {
        this.event = e;
        return this;
    }

    public ClickEvent getClickEvent() {
        return event;
    }

    public NPC(String name, UUID uuid, boolean enableOuterLayerSkin) {
        WorldServer world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
        entityPlayer = new EntityPlayer(((CraftServer) Bukkit.getServer()).getServer(), world, new GameProfile(uuid == null ? UUID.randomUUID() : uuid, name), new PlayerInteractManager(world));
        if (enableOuterLayerSkin)
            entityPlayer.getDataWatcher().watch(10, (byte) 127);
        all.add(this);
    }

    public NPC spawn() {
        float yaw = entityPlayer.yaw;
        entityPlayer.yaw = spawnBodyYaw;
        PacketPlayOutPlayerInfo p1 = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
        PacketPlayOutNamedEntitySpawn p2 = new PacketPlayOutNamedEntitySpawn(entityPlayer);
        entityPlayer.yaw = yaw;
        PacketPlayOutEntityHeadRotation p3 = new PacketPlayOutEntityHeadRotation(entityPlayer, (byte) ((int) (entityPlayer.yaw * 256 / 360)));
        listener.forEach(a -> {
                    a.sendPacket(p1);
                    a.sendPacket(p2);
                    a.sendPacket(p3);
                }
        );
        if (nameTagHidden)
            hideNametag();
        if (nameTagHidden)
            hideNametag();
        if (itemInHand != null)
            setItemInHand(itemInHand);
        if (ms != -1)
            hideFromTab(ms);
        return this;
    }


    //AFTER
    public void delete() {
        for (CubingLibPlayer p : CubingLibPlayer.user.values()) {
            p.outRangeNPC.remove(this);
        }
        all.remove(this);
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

    public NPC setSneaking(boolean sneaking) {
        entityPlayer.setSneaking(sneaking);
        PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(entityPlayer.getId(), entityPlayer.getDataWatcher(), true);
        listener.forEach(a -> a.sendPacket(metadata));
        return this;
    }

    //ALWAYS
    public NPC world(String... world) {
        this.world.addAll(Sets.newHashSet(world));
        return this;
    }

    public NPC setAutoSpawn(boolean autoSpawn) {
        this.autoSpawn = autoSpawn;
        return this;
    }

    public boolean getAutoSpawn() {
        return autoSpawn;
    }

    public NPC setAutoListen(boolean autoListen) {
        this.autoListen = autoListen;
        return this;
    }

    public boolean getAutoListen() {
        return autoSpawn;
    }

    public NPC setSpawnBodyYaw(float spawnBodyYaw) {
        this.spawnBodyYaw = spawnBodyYaw;
        return this;
    }

    public NPC setItemInHand(ItemStack itemInHand) {
        this.itemInHand = itemInHand == null || itemInHand.getType().equals(Material.AIR) ? null : itemInHand;
        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(entityPlayer.getId(), 0, CraftItemStack.asNMSCopy(itemInHand));
        listener.forEach(a -> a.sendPacket(packet));
        return this;
    }

    public NPC hideNametag() {
        this.nameTagHidden = true;
        PacketPlayOutScoreboardTeam p1 = new OutScoreboardTeam().a("~").e("never").h(1).packet;
        PacketPlayOutScoreboardTeam p2 = new OutScoreboardTeam().a("~").e("never").packet;
        PacketPlayOutScoreboardTeam p3 = new OutScoreboardTeam().a("~").g(Collections.singletonList(entityPlayer.getName())).h(3).packet;
        listener.forEach(a -> {
            a.sendPacket(p1);
            a.sendPacket(p2);
            a.sendPacket(p3);
        });
        return this;
    }


    public NPC tempHideFromTab(int ms) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer);
        Set<PlayerConnection> copy = new HashSet<>(listener);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                copy.forEach(a -> a.sendPacket(packet));
            }
        }, ms);
        return this;
    }

    public NPC hideFromTab(int ms) {
        this.ms = ms;
        return tempHideFromTab(ms);
    }

    public NPC setSkin(UUID uuid) {
        try {
            ProfileSkin skin = MojangAPI.getSkinByUUID(uuid);
            return setSkin(skin.getValue(), skin.getSignature());
        } catch (Exception e) {
            return this;
        }
    }

    public NPC setSkin(String name) {
        try {
            ProfileSkin skin = MojangAPI.getSkinByName(name);
            return setSkin(skin.getValue(), skin.getSignature());
        } catch (Exception e) {
            return this;
        }
    }

    public NPC setSkin(String value, String signature) {
        GameProfile gameProfile = entityPlayer.getProfile();
        gameProfile.getProperties().removeAll("textures");
        gameProfile.getProperties().put("textures", new Property("textures", value, signature));
        despawn();
        spawn();
        updateNpcLocation();
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

    public void aim(Location location) {
        double x = location.getX();
        double z = location.getZ();
        double xDiff = x - entityPlayer.locX;
        double zDiff = z - entityPlayer.locZ;
        float yaw;
        if (z == entityPlayer.locZ)
            yaw = xDiff > 0 ? 270 : 90;
        else if (x == entityPlayer.locX)
            yaw = zDiff > 0 ? 0 : 180;
        else {
            float s = (float) Math.toDegrees(Math.atan(Math.abs(xDiff) / Math.abs(zDiff)));
            yaw = zDiff > 0 ? (xDiff > 0 ? 360 - s : s) : 180 + (xDiff > 0 ? s : -s);
        }
        double y = location.getY() - entityPlayer.locY;
        double l = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        setLocation(
                entityPlayer.locX,
                entityPlayer.locY,
                entityPlayer.locZ,
                yaw,
                (l == 0) ? 270 : (y == 0 ? 0 : 360 - (float) Math.toDegrees(Math.atan(y / l)))).updateNpcLocation();
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
