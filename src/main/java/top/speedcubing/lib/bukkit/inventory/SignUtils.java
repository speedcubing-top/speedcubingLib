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
import org.bukkit.plugin.Plugin;

public class SignUtils {
    public static void openSignGUI(Plugin owningPlugin, Player player, int x, int y, int z, String metadata, String[] lines) {
        FixedMetadataValue metadataValue = new FixedMetadataValue(owningPlugin, "");
        World world = player.getWorld();
        world.getBlockAt(x, y, z).setType(Material.SIGN_POST);
        Sign sign = (Sign) world.getBlockAt(x, y, z).getState();
        for (int i = 0; i < lines.length; i++) {
            sign.setLine(i, lines[i]);
        }
        sign.setMetadata(metadata, metadataValue);
        sign.update();
        BlockPosition position = new BlockPosition(sign.getX(), sign.getY(), sign.getZ());
        TileEntitySign tileEntitySign = (TileEntitySign) ((CraftWorld) sign.getWorld()).getHandle().getTileEntity(position);
        tileEntitySign.isEditable = true;

        PacketPlayOutOpenSignEditor packetPlayOutOpenSignEditor = new PacketPlayOutOpenSignEditor(position);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutOpenSignEditor);
    }
}
