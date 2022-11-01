package top.speedcubing.lib.bukkit.pluginMessage;

import org.bukkit.entity.Player;
import top.speedcubing.lib.utils.ByteArrayDataBuilder;

import java.io.IOException;

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

    public PluginMessageBuilder write(int... b) {
        try {
            for (int a : b)
                byteArrayDataBuilder.dataOutputStream.write(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginMessageBuilder write(byte[] b, int off, int len) {
        try {
            byteArrayDataBuilder.dataOutputStream.write(b, off, len);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginMessageBuilder write(byte[]... b) {
        try {
            for (byte[] a : b)
                byteArrayDataBuilder.dataOutputStream.write(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginMessageBuilder writeInt(int... v) {
        try {
            for (int a : v)
                byteArrayDataBuilder.dataOutputStream.writeInt(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginMessageBuilder writeUTF(String... str) {
        try {
            for (String a : str)
                byteArrayDataBuilder.dataOutputStream.writeUTF(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginMessageBuilder writeChar(int... v) {
        try {
            for (int a : v)
                byteArrayDataBuilder.dataOutputStream.writeChar(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginMessageBuilder writeByte(int... v) {
        try {
            for (int a : v)
                byteArrayDataBuilder.dataOutputStream.writeByte(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginMessageBuilder writeBytes(String... s) {
        try {
            for (String a : s)
                byteArrayDataBuilder.dataOutputStream.writeBytes(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginMessageBuilder writeChars(String... s) {
        try {
            for (String a : s)
                byteArrayDataBuilder.dataOutputStream.writeChars(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginMessageBuilder writeLong(long... v) {
        try {
            for (long a : v)
                byteArrayDataBuilder.dataOutputStream.writeLong(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginMessageBuilder writeFloat(float... v) {
        try {
            for (float a : v)
                byteArrayDataBuilder.dataOutputStream.writeFloat(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginMessageBuilder writeDouble(double... v) {
        try {
            for (double a : v)
                byteArrayDataBuilder.dataOutputStream.writeDouble(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginMessageBuilder writeShort(short... v) {
        try {
            for (short a : v)
                byteArrayDataBuilder.dataOutputStream.writeShort(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginMessageBuilder writeBoolean(boolean... v) {
        try {
            for (boolean a : v)
                byteArrayDataBuilder.dataOutputStream.writeBoolean(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
