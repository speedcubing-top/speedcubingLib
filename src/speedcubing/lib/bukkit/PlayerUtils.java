package speedcubing.lib.bukkit;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PlayerUtils {
    public static void explosionCrash(PlayerConnection connection) {
        connection.sendPacket(new PacketPlayOutExplosion(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Float.MAX_VALUE, new ArrayList<>(), new Vec3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE)));
    }

    public static void removeArrows(DataWatcher watcher) {
        watcher.watch(9, (byte) 0);
    }

    public static void sendActionBar(PlayerConnection connection, String message) {
        connection.sendPacket(new PacketPlayOutChat(new ChatComponentText(message), (byte) 2));
    }

    public static void teleport(PlayerConnection connection, double x, double y, double z, float yaw, float pitch) {
        connection.a(x, y, z, yaw, pitch);
    }

    public static List<Packet<?>>[] changeSkin(EntityPlayer entityPlayer, String[] skin) {
        org.bukkit.entity.Player player = entityPlayer.getBukkitEntity().getPlayer();
        World world = entityPlayer.getWorld();
        GameProfile gameProfile = entityPlayer.getProfile();
        gameProfile.getProperties().removeAll("textures");
        gameProfile.getProperties().put("textures", new Property("textures", skin[0], skin[1]));
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
