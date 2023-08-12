package top.speedcubing.lib.utils.sockets;

import java.io.*;

public class ByteUtils {
    public static DataInputStream byteToDataInputStream(byte[] b) {
        return new DataInputStream(new ByteArrayInputStream(b));
    }
//
//    public static byte[] readInputStream(InputStream inputStream, int bufferSize) {
//        try {
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            byte[] buffer = new byte[bufferSize];
//            int i;
//            while ((i = inputStream.read(buffer)) != -1)
//                out.write(buffer, 0, i);
//            return out.toByteArray();
//        } catch (IOException e) {
//            return null;
//        }
//    }
//
//    public static DataInputStream inputStreamToDataInputStream(InputStream inputStream, int bufferSize) {
//        return byteToDataInputStream(readInputStream(inputStream, bufferSize));
//    }
}
