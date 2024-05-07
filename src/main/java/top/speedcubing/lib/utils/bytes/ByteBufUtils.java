package top.speedcubing.lib.utils.bytes;

import io.netty.buffer.ByteBuf;

public class ByteBufUtils {

    //https://wiki.vg/Protocol#Type:VarInt
    public static int readVarInt(ByteBuf buf) {
        int out = 0;
        int bytes = 0;
        int in;
        do {
            in = buf.readByte();
            out |= (in & 0x7F) << bytes++ * 7;
            if (bytes > 5)
                throw new RuntimeException("VarInt too big");
        } while ((in & 0x80) == 0x80);
        return out;
    }

    public static void writeVarInt(ByteBuf buf, int i) {
        int part;
        do {
            part = i & 0x7F;
            i >>>= 7;
            if (i != 0)
                part |= 0x80;
            buf.writeByte(part);
        } while (i != 0);
    }

    public static String readString(ByteBuf buf) {
        int len = readVarInt(buf);
        byte[] b = new byte[len];
        buf.readBytes(b);
        return new String(b);
    }

    public static void writeString(ByteBuf buf, String s) {
        byte[] b = s.getBytes();
        writeVarInt(buf, b.length);
        buf.writeBytes(b);
    }
}
