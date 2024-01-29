package top.speedcubing.lib.utils;

import java.util.List;

public class ArrayUtils {

    public static byte[] toPrimitiveArray(List<Byte> list) {
        byte[] b = new byte[list.size()];
        for (int i = 0; i < list.size(); i++)
            b[i] = list.get(i);
        return b;
    }

    public static byte[] toPrimitiveArray(Byte[] arr) {
        byte[] b = new byte[arr.length];
        for (int i = 0; i < arr.length; i++)
            b[i] = arr[i];
        return b;
    }
}
