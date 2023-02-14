package top.speedcubing.lib.bukkit;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import top.speedcubing.lib.bukkit.packetwrapper.OutPlayerListHeaderFooter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PlayerUtils {

    public static PlayerConnection getConnection(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }

    public static void explosionCrash(Player player) {
        getConnection(player).sendPacket(new PacketPlayOutExplosion(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Float.MAX_VALUE, new ArrayList<>(), new Vec3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE)));
    }

    public static void removeArrows(Player player) {
        ((CraftPlayer) player).getHandle().getDataWatcher().watch(9, (byte) 0);
    }

    public static void sendTabListHeaderFooter(Player player, String header, String footer) {
        getConnection(player).sendPacket(new OutPlayerListHeaderFooter().a(header).b(footer).packet);
    }

    public static void sendActionBar(Player player, String message) {
        getConnection(player).sendPacket(new PacketPlayOutChat(new ChatComponentText(message), (byte) 2));
    }

    public static void teleportSilence(Player player, double x, double y, double z, float yaw, float pitch) {
        getConnection(player).teleport(new Location(player.getWorld(), x, y, z, yaw, pitch));
    }

    public static void setTitleTime(Player player, int fadeIn, int stay, int fadeOut) {
        getConnection(player).sendPacket(new PacketPlayOutTitle(fadeIn, stay, fadeOut));
    }

    public static void sendTitle(Player player, TitleType type, String text) {
        PacketPlayOutTitle.EnumTitleAction action;
        switch (type) {
            case TITLE:
                action = PacketPlayOutTitle.EnumTitleAction.TITLE;
                break;
            case SUBTITLE:
                action = PacketPlayOutTitle.EnumTitleAction.SUBTITLE;
                break;
            default:
                return;
        }
        getConnection(player).sendPacket(new PacketPlayOutTitle(action, new ChatComponentText(text)));
    }

    public static void sendTitle(Player player, TitleType type, String text, int fadeIn, int stay, int fadeOut) {
        setTitleTime(player, fadeIn, stay, fadeOut);
        sendTitle(player, type, text);
    }

    public static List<Packet<?>>[] changeSkin(Player player, String[] skin) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        World world = entityPlayer.getWorld();
        PropertyMap property = entityPlayer.getProfile().getProperties();
        property.removeAll("textures");
        property.put("textures", new Property("textures", skin[0], skin[1]));
        PacketPlayOutPlayerInfo removePlayerPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer);
        PacketPlayOutPlayerInfo addPlayerPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
        PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(entityPlayer.getId());
        PlayerInventory inventory = player.getInventory();
        Location l = player.getLocation();
        return new List[]{
                Arrays.asList(
                        removePlayerPacket,
                        addPlayerPacket,
                        new PacketPlayOutRespawn(world.getWorld().getEnvironment().getId(), world.getDifficulty(), world.getWorldData().getType(), entityPlayer.playerInteractManager.getGameMode()),
                        new PacketPlayOutPosition(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch(), new HashSet<>()),
                        new PacketPlayOutHeldItemSlot(player.getInventory().getHeldItemSlot())),
                Arrays.asList(
                        removePlayerPacket,
                        addPlayerPacket,
                        destroyPacket,
                        new PacketPlayOutNamedEntitySpawn(entityPlayer),
                        new PacketPlayOutEntity.PacketPlayOutEntityLook(entityPlayer.getId(), (byte) ((int) (l.getYaw() * 256F / 360F)), (byte) ((int) (l.getPitch() * 256F / 360F)), true),
                        new PacketPlayOutEntityHeadRotation(entityPlayer, (byte) ((int) (l.getYaw() * 256F / 360F))),
                        new PacketPlayOutEntityEquipment(entityPlayer.getId(), 0, CraftItemStack.asNMSCopy(player.getItemInHand())),
                        new PacketPlayOutEntityEquipment(entityPlayer.getId(), 1, CraftItemStack.asNMSCopy(inventory.getBoots())),
                        new PacketPlayOutEntityEquipment(entityPlayer.getId(), 2, CraftItemStack.asNMSCopy(inventory.getLeggings())),
                        new PacketPlayOutEntityEquipment(entityPlayer.getId(), 3, CraftItemStack.asNMSCopy(inventory.getChestplate())),
                        new PacketPlayOutEntityEquipment(entityPlayer.getId(), 4, CraftItemStack.asNMSCopy(inventory.getHelmet()))),
                Arrays.asList(
                        removePlayerPacket,
                        addPlayerPacket,
                        destroyPacket)
        };
    }
}
