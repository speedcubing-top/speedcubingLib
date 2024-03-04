package top.speedcubing.lib.utils;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {

    public static String readString(DataInputStream input) throws IOException {
        int length = IOUtils.readVarInt(input);
        byte[] target = new byte[length];
        input.readFully(target);
        return new String(target);
    }

    public static void writeString(DataOutputStream out, String s) throws IOException {
        byte[] b = s.getBytes();
        writeVarInt(out, b.length);
        out.write(b);
    }

    public static void writeVarInt(DataOutputStream out, int i) throws IOException {
        int part;
        do {
            part = i & 0x7F;
            i >>>= 7;
            if (i != 0)
                part |= 0x80;
            out.writeByte(part);
        } while (i != 0);
    }

    public static int readVarInt(DataInputStream input) throws IOException {
        int out = 0;
        int bytes = 0;
        int in;
        do {
            in = input.readByte();
            out |= (in & 0x7F) << bytes++ * 7;
            if (bytes > 5)
                throw new RuntimeException("VarInt too big");
        } while ((in & 0x80) == 0x80);
        return out;
    }

    public static DataInputStream toDataInputStream(byte[] b) {
        return new DataInputStream(new ByteArrayInputStream(b));
    }

    public static void closeQuietly(Closeable... closeables) {
        for (Closeable c : closeables)
            if (c != null) {
                try {
                    c.close();
                } catch (IOException ignored) {
                }
            }
    }

    public static byte[] readOnce(InputStream in, int bufferSize) {
        try {
            byte[] b = new byte[bufferSize];
            in.read(b);
            return b;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
