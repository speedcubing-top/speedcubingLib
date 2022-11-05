package top.speedcubing.lib.utils.sockets;

import java.io.*;

public class ByteUtils {
    public static DataInputStream byteToDataInputStream(byte[] b) {
        return new DataInputStream(new ByteArrayInputStream(b));
    }

    public static byte[] readInputStream(int bufferSize, InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[bufferSize];
            for (int s; (s = inputStream.read(buffer)) != -1; )
                byteArrayOutputStream.write(buffer, 0, s);
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DataInputStream inputStreamToDataInputStream(int bufferSize, InputStream inputStream) {
        return byteToDataInputStream(readInputStream(bufferSize, inputStream));
    }
}
