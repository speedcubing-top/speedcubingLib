package top.speedcubing.lib.bukkit;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutExplosion;
import net.minecraft.server.v1_8_R3.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import top.speedcubing.lib.bukkit.entity.NPC;
import top.speedcubing.lib.bukkit.packetwrapper.OutPlayerListHeaderFooter;
import top.speedcubing.lib.speedcubingLibBukkit;

public class PlayerUtils {

    public static PlayerConnection getConnection(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }


    public static void crashAll(Player player) {
        explosionCrash(player);
        positionCrash(player);
        entityCrash(player);
    }

    public static void explosionCrash(Player player) {
        getConnection(player).sendPacket(new PacketPlayOutPosition(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Collections.emptySet()));
    }

    public static void positionCrash(Player player) {
        getConnection(player).sendPacket(new PacketPlayOutExplosion(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Float.MAX_VALUE, Collections.emptyList(), new Vec3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE)));
    }

    public static void entityCrash(Player player) {
        Location loc = player.getLocation();
        PlayerConnection c = getConnection(player);
        Bukkit.getScheduler().runTaskAsynchronously(speedcubingLibBukkit.getPlugin(speedcubingLibBukkit.class), () -> {
            for (int i = 0; i < 100000; i++) {
                try {
                    EntityEnderDragon entityEnderDragon = new EntityEnderDragon(((CraftWorld) loc.getWorld()).getHandle());
                    EntityPlayer entityPlayer = new EntityPlayer(((CraftWorld) loc.getWorld()).getHandle().getMinecraftServer(), ((CraftWorld) loc.getWorld()).getHandle(), new GameProfile(UUID.randomUUID(), ""), new PlayerInteractManager(((CraftWorld) loc.getWorld()).getHandle()));
                    PacketPlayOutSpawnEntityLiving living = new PacketPlayOutSpawnEntityLiving(entityEnderDragon);
                    PacketPlayOutSpawnEntityLiving living1 = new PacketPlayOutSpawnEntityLiving(entityPlayer);
                    c.sendPacket(living);
                    c.sendPacket(living1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
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

    public static int countItem(Player player, org.bukkit.Material material) {
        int i = 0;
        for (ItemStack s : player.getInventory().getContents())
            if (s != null && s.getType() == material)
                i += s.getAmount();
        return i;
    }

    public static void changeSkin(Player player, String value, String signature) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        World world = entityPlayer.getWorld();
        PlayerConnection connection = entityPlayer.playerConnection;
        Location l = player.getLocation();

        PropertyMap property = entityPlayer.getProfile().getProperties();
        property.removeAll("textures");
        property.put("textures", new Property("textures", value, signature));

        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));
        connection.sendPacket(new PacketPlayOutRespawn(world.getWorld().getEnvironment().getId(), world.getDifficulty(), world.getWorldData().getType(), entityPlayer.playerInteractManager.getGameMode()));
        connection.sendPacket(new PacketPlayOutPosition(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch(), new HashSet<>()));
        connection.sendPacket(new PacketPlayOutHeldItemSlot(player.getInventory().getHeldItemSlot()));

        player.updateInventory();
    }
}
