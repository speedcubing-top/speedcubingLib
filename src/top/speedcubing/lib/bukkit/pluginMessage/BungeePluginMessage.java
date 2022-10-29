package top.speedcubing.lib.bukkit.pluginMessage;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import top.speedcubing.lib.speedcubingLibBukkit;

public class BungeePluginMessage {

    public static void switchServer(Player player, String server) {
        new PluginMessageBuilder().UTF("Connect", server).send(player);
    }

    public static void sendRawMessage(Player player, String target, String text) {
        new PluginMessageBuilder().UTF("MessageRaw", target, text).send(player);
    }

    public static void msgPlayerCount(Player player, String server) {
        new PluginMessageBuilder().UTF("PlayerCount", server == null ? "ALL" : server).send(player);
    }

    public static void msgPlayerList(Player player, String server) {
        new PluginMessageBuilder().UTF("PlayerList", server == null ? "ALL" : server).send(player);
    }

    public static void sendPluginMessage(Player player, String channel, byte[] out) {
        if (player == null)
            Bukkit.getServer().sendPluginMessage(speedcubingLibBukkit.getPlugin(speedcubingLibBukkit.class), channel, out);
        else
            player.sendPluginMessage(speedcubingLibBukkit.getPlugin(speedcubingLibBukkit.class), channel, out);
    }
}
