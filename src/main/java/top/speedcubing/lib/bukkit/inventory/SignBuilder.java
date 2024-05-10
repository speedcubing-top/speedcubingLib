package top.speedcubing.lib.bukkit.inventory;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_8_R3.TileEntitySign;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import top.speedcubing.lib.api.events.SignUpdateEvent;
import top.speedcubing.lib.speedcubingLibBukkit;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class SignBuilder {
    public static Set<SignBuilder> signs = new HashSet<>();

    public static SignBuilder openSign(Player player, int x, int y, int z, String... lines) {
        return new SignBuilder(player, x, y, z, lines);
    }


    private long timeout = 10000L;
    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
    private final long generatedTime = System.currentTimeMillis();

    public long getGeneratedTime() {
        return generatedTime;
    }

    private Consumer<SignUpdateEvent> event = (r) -> {
    };

    public void onFinish(Consumer<SignUpdateEvent> event) {
        this.event = event;
    }

    public Consumer<SignUpdateEvent> getEvent() {
        return event;
    }

    private final Player player;

    public Player getPlayer() {
        return player;
    }

    SignBuilder(Player player, int x, int y, int z, String... lines) {
        this.player = player;
        FixedMetadataValue metadataValue = new FixedMetadataValue(speedcubingLibBukkit.getPlugin(speedcubingLibBukkit.class), "");
        World world = player.getWorld();
        world.getBlockAt(x, y, z).setType(Material.SIGN_POST);
        Sign sign = (Sign) world.getBlockAt(x, y, z).getState();
        for (int i = 0; i < lines.length; i++) {
            sign.setLine(i, lines[i]);
        }
        sign.setMetadata("", metadataValue);
        sign.update();
        BlockPosition position = new BlockPosition(sign.getX(), sign.getY(), sign.getZ());
        TileEntitySign tileEntitySign = (TileEntitySign) ((CraftWorld) sign.getWorld()).getHandle().getTileEntity(position);
        tileEntitySign.isEditable = true;

        PacketPlayOutOpenSignEditor packetPlayOutOpenSignEditor = new PacketPlayOutOpenSignEditor(position);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutOpenSignEditor);
        signs.add(this);
    }
}
