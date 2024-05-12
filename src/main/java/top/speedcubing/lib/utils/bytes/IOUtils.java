package top.speedcubing.lib.utils.bytes;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

    public static int readVarInt(InputStream input) throws IOException {
        int out = 0;
        int bytes = 0;
        int in;
        do {
            in = input.read();
            out |= (in & 0x7F) << bytes++ * 7;
            if (bytes > 5)
                throw new RuntimeException("VarInt too big");
        } while ((in & 0x80) == 0x80);
        return out;
    }

    public static void writeVarInt(OutputStream out, int i) throws IOException {
        int part;
        do {
            part = i & 0x7F;
            i >>>= 7;
            if (i != 0)
                part |= 0x80;
            out.write(part);
        } while (i != 0);
    }


    public static String readString(InputStream in) throws IOException {
        int len = readVarInt(in);
        byte[] b = readFully(in, len);
        return new String(b);
    }

    public static byte[] readFully(InputStream in, int len) throws IOException {
        if (len < 0)
            throw new IndexOutOfBoundsException();
        byte[] b = new byte[len];
        readFully(in, b, 0, len);
        return b;
    }

    public static void readFully(InputStream in, byte[] b, int off, int len) throws IOException {
        if (len < 0)
            throw new IndexOutOfBoundsException();
        int n = 0;
        while (n < len) {
            int count = in.read(b, off + n, len - n);
            if (count < 0)
                throw new EOFException();
            n += count;
        }
    }

    public static DataInputStream toDataInputStream(byte[] b) {
        return new DataInputStream(new ByteArrayInputStream(b));
    }

    public static void closeQuietly(Closeable... closeables) {
        for (Closeable c : closeables) {
            if (c != null) {
                try {
                    c.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
