package cubing.lib.bukkit;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BungeePluginMessage {
    public static void switchServer(Player player, String server,Plugin plugin) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        sendPluginMessage(player, "BungeeCord", out.toByteArray(),plugin);
    }

    public static void sendRawMessage(Player player, String target, String text,Plugin plugin) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("MessageRaw");
        out.writeUTF(target);
        out.writeUTF(text);
        sendPluginMessage(player, "BungeeCord", out.toByteArray(),plugin);
    }

    public static void msgPlayerCount(Player player, String server,Plugin plugin) {
        if (server == null)
            server = "ALL";
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);
        sendPluginMessage(player, "BungeeCord", out.toByteArray(),plugin);
    }

    public static void msgPlayerList(Player player, String server,Plugin plugin) {
        if (server == null)
            server = "ALL";
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerList");
        out.writeUTF(server);
        sendPluginMessage(player, "BungeeCord", out.toByteArray(),plugin);
    }

    public static void sendPluginMessage(Player player, String channel, byte[] out, Plugin plugin) {
        if (player == null)
            Bukkit.getServer().sendPluginMessage(plugin, channel, out);
        else
            player.sendPluginMessage(plugin, channel, out);
    }
}
