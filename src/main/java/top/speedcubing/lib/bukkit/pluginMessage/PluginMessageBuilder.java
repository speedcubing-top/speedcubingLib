package top.speedcubing.lib.bukkit.pluginMessage;

import org.bukkit.entity.Player;
import top.speedcubing.lib.utils.bytes.ByteArrayBuffer;

//new PluginMessageBuilder(...).write...write...send();
class PluginMessageBuilder {
    private final ByteArrayBuffer bytes = new ByteArrayBuffer();
    private final Player player;
    private final String channel;

    public PluginMessageBuilder(Player player, String channel) {
        this.player = player;
        this.channel = channel;
    }

    public PluginMessageBuilder(Player player) {
        this(player, "BungeeCord");
    }

    public PluginMessageBuilder(String channel) {
        this(null, channel);
    }

    public PluginMessageBuilder() {
        this(null, "BungeeCord");
    }

    public void send() {
        BungeePluginMessage.sendPluginMessage(player, channel, bytes.toByteArray());
    }

    public PluginMessageBuilder write(int b) {
        bytes.write(b);
        return this;
    }

    public PluginMessageBuilder write(byte[] b, int off, int len) {
        bytes.write(b, off, len);
        return this;
    }

    public PluginMessageBuilder write(byte[] b) {
        bytes.write(b);
        return this;
    }

    public PluginMessageBuilder writeInt(int v) {
        bytes.writeInt(v);
        return this;
    }

    public PluginMessageBuilder writeUTF(String str) {
        bytes.writeUTF(str);
        return this;
    }

    public PluginMessageBuilder writeChar(int v) {
        bytes.writeChar(v);
        return this;
    }

    public PluginMessageBuilder writeByte(int v) {
        bytes.writeByte(v);
        return this;
    }

    public PluginMessageBuilder writeBytes(String s) {
        bytes.writeBytes(s);
        return this;
    }

    public PluginMessageBuilder writeChars(String s) {
        bytes.writeChars(s);
        return this;
    }

    public PluginMessageBuilder writeLong(long v) {
        bytes.writeLong(v);
        return this;
    }

    public PluginMessageBuilder writeFloat(float v) {
        bytes.writeFloat(v);
        return this;
    }

    public PluginMessageBuilder writeDouble(double v) {
        bytes.writeDouble(v);
        return this;
    }

    public PluginMessageBuilder writeShort(short v) {
        bytes.writeShort(v);
        return this;
    }

    public PluginMessageBuilder writeBoolean(boolean v) {
        bytes.writeBoolean(v);
        return this;
    }
}
