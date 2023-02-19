package top.speedcubing.lib.utils.sockets;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ByteUtils {
    public static DataInputStream byteToDataInputStream(byte[] b) {
        return new DataInputStream(new ByteArrayInputStream(b));
    }

    public static byte[] readInputStream(int bufferSize, InputStream inputStream) {
        byte[] b = new byte[bufferSize];
        if (bufferSize == 0)
            return b;
        try {
            int c;
            for (int i = 0; i < bufferSize; i++) {
                c = inputStream.read();
                if (c == -1)
                    break;
                b[i] = (byte) c;
            }
            if (inputStream.read() != -1)
                throw new BufferOverflowException();
        } catch (IOException e) {
        }
        return b;
    }

    public static byte[] readInputStream(InputStream inputStream, int grow) {
        try {
            int index = 0;
            byte[] buffer = new byte[grow];
            int s;
            while (true) {
                s = inputStream.read();
                if (s == -1)
                    return buffer;
                if (index == grow)
                    break;
                else {
                    buffer[index] = (byte) s;
                    index++;
                }
            }
            List<byte[]> buffers = new ArrayList<>();
            buffers.add(buffer);
            buffer = new byte[grow];
            buffer[0] = (byte) s;
            buffers.add(buffer);
            index = 1;
            int size = 2;
            while ((s = inputStream.read()) != -1) {
                if (index == grow) {
                    buffer = new byte[grow];
                    buffers.add(buffer);
                    index = 0;
                    size++;
                }
                buffer[index] = (byte) s;
                index++;
            }
            buffer = new byte[grow * size];
            size = 0;
            s =  0;
            for (byte[] b : buffers) {
                for (; size < grow; size++) {
                    buffer[s] = b[size];
                    s++;
                }
                size = 0;
            }
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DataInputStream inputStreamToDataInputStream(int bufferSize, InputStream inputStream) {
        return byteToDataInputStream(readInputStream(bufferSize, inputStream));
    }

    public static DataInputStream inputStreamToDataInputStream(InputStream inputStream, int grow) {
        return byteToDataInputStream(readInputStream(inputStream, grow));
    }
}
