package top.speedcubing.lib.utils;

import java.io.*;

public class IOUtils {
    public static DataInputStream toDataInputStream(byte[] b) {
        return new DataInputStream(new ByteArrayInputStream(b));
    }

    public static void closeQuietly(Closeable... closeables) {
        for (Closeable c : closeables)
            if (c != null) {
                try {
                    c.close();
                } catch (IOException var2) {
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
