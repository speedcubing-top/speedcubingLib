package top.speedcubing.lib.utils.bytes;

import io.netty.buffer.ByteBuf;

public class ByteBufUtils {

    public static int readVarInt(ByteBuf input) {
        int out = 0;
        int bytes = 0;
        byte in;
        do {
            in = input.readByte();
            out |= (in & 0x7F) << (bytes++ * 7);
            if (bytes > 5)
                throw new RuntimeException("VarInt too big");
        } while ((in & 0x80) == 0x80);
        return out;
    }

    public static void writeVarInt(ByteBuf out, int i) {
        int part;
        do {
            part = i & 0x7F;
            i >>>= 7;
            if (i != 0)
                part |= 0x80;
            out.writeByte(part);
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

    public static void writeVarShort(ByteBuf buf, int i) {
        int low = i & 0x7FFF;
        int high = (i & 0x7F8000) >> 15;
        if (high != 0)
            low = low | 0x8000;
        buf.writeShort(low);
        if (high != 0)
            buf.writeByte(high);
    }
}
