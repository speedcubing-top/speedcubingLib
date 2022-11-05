package top.speedcubing.lib.bukkit.pluginMessage;

import org.bukkit.entity.Player;
import top.speedcubing.lib.utils.ByteArrayDataBuilder;

class PluginMessageBuilder {
    private final ByteArrayDataBuilder byteArrayDataBuilder = new ByteArrayDataBuilder();
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
        BungeePluginMessage.sendPluginMessage(player, channel, byteArrayDataBuilder.toByteArray());
    }

    public PluginMessageBuilder write(int b) {
        byteArrayDataBuilder.write(b);
        return this;
    }

    public PluginMessageBuilder write(byte[] b, int off, int len) {
        byteArrayDataBuilder.write(b, off, len);
        return this;
    }

    public PluginMessageBuilder write(byte[] b) {
        byteArrayDataBuilder.write(b);
        return this;
    }

    public PluginMessageBuilder writeInt(int v) {
        byteArrayDataBuilder.writeInt(v);
        return this;
    }

    public PluginMessageBuilder writeUTF(String str) {
        byteArrayDataBuilder.writeUTF(str);
        return this;
    }

    public PluginMessageBuilder writeChar(int v) {
        byteArrayDataBuilder.writeChar(v);
        return this;
    }

    public PluginMessageBuilder writeByte(int v) {
        byteArrayDataBuilder.writeByte(v);
        return this;
    }

    public PluginMessageBuilder writeBytes(String s) {
        byteArrayDataBuilder.writeBytes(s);
        return this;
    }

    public PluginMessageBuilder writeChars(String s) {
        byteArrayDataBuilder.writeChars(s);
        return this;
    }

    public PluginMessageBuilder writeLong(long v) {
        byteArrayDataBuilder.writeLong(v);
        return this;
    }

    public PluginMessageBuilder writeFloat(float v) {
        byteArrayDataBuilder.writeFloat(v);
        return this;
    }

    public PluginMessageBuilder writeDouble(double v) {
        byteArrayDataBuilder.writeDouble(v);
        return this;
    }

    public PluginMessageBuilder writeShort(short v) {
        byteArrayDataBuilder.writeShort(v);
        return this;
    }

    public PluginMessageBuilder writeBoolean(boolean v) {
        byteArrayDataBuilder.writeBoolean(v);
        return this;
    }
}
