package top.speedcubing.lib.bukkit;

import io.netty.channel.ChannelPipeline;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import top.speedcubing.lib.bukkit.handler.PacketPlayInHandler;
import top.speedcubing.lib.bukkit.handler.PacketPlayOutHandler;

public class CubingLibPlayer {
    public static void init() {
        wither = new EntityWither(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle());
        wither.setInvisible(true);
    }

    public static EntityWither wither;

    public static Map<Player, CubingLibPlayer> user = new HashMap<>();

    public static CubingLibPlayer get(Player player) {
        return user.get(player);
    }

    public static Collection<CubingLibPlayer> getPlayers() {
        return user.values();
    }

    SideBar sideBar;
    Player player;

    String bossbar;

    public CubingLibPlayer(Player player) {
        this.player = player;

        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();

        if (pipeline.get("decoder") != null)
            pipeline.addAfter("decoder", "speedcubingLib-packetplayin", new PacketPlayInHandler(player));
        if (pipeline.get("encoder") != null)
            pipeline.addAfter("encoder", "speedcubingLib-packetplayout", new PacketPlayOutHandler(player));

        user.put(player, this);
    }

    public SideBar getSideBar() {
        return sideBar;
    }

    public void setSideBar(SideBar sideBar) {
        this.sideBar = sideBar;
    }

    public String getBossbar() {
        return bossbar;
    }

    public void setBossbar(String bossbar) {
        this.bossbar = bossbar;
        Location location = player.getLocation();
        Location newLocation = location.clone().add(location.getDirection().multiply(100));
        wither.setCustomName(bossbar);
        wither.setLocation(newLocation.getX(), newLocation.getY(), newLocation.getZ(), 0, 0);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(wither));
    }
}
