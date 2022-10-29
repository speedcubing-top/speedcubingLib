package top.speedcubing.lib.bukkit.pluginMessage;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;

class PluginMessageBuilder {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();

    public PluginMessageBuilder UTF(String... s) {
        for (String s1 : s) {
            out.writeUTF(s1);
        }
        return this;
    }

    public void send(Player player, String channel) {
        BungeePluginMessage.sendPluginMessage(player, channel, out.toByteArray());
    }

    public void send(Player player) {
        send(player, "BungeeCord");
    }

    public void send() {
        send(null, null);
    }

    public void send(String channel) {
        send(null, channel);
    }
}
