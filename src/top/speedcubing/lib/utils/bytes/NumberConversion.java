package top.speedcubing.lib.utils.bytes;

import top.speedcubing.lib.utils.ArrayUtils;

import java.util.*;

public class NumberConversion {

    public static String toBinary(long i) {
        return toBinary(i, 64);
    }

    public static String toBinary(int i) {
        return toBinary(i, 32);
    }

    public static String toBinary(short i) {
        return toBinary(i, 16);
    }

    public static String toBinary(byte i) {
        return toBinary(i, 8);
    }
    public static String toSizedBinary(short i) {
        return toSizedBinary(i, 16);
    }

    public static String toSizedBinary(byte i) {
        return toSizedBinary(i, 8);
    }

    public static String toSizedBinary(long i) {
        return toSizedBinary(i, 64);
    }

    public static String toSizedBinary(int i) {
        return toSizedBinary(i, 32);
    }

    public static String toBinary(long i, int size) {
        return toBinaryString(i,size,false);
    }

    public static String toSizedBinary(long i, int size) {
        return toBinaryString(i,size,true);
    }

    public static String toBinaryString(long i, int size,boolean actualSize) {
        boolean negative = i < 0;
        if (negative)
            i = -i - 1;
        StringBuilder s = new StringBuilder();
        int j = 0;
        while (negative || actualSize ? j < size : i != 0) {
            s.insert(0, negative ? 1 - i % 2 : i % 2);
            i >>>= 1;
            j++;
        }
        return s.toString();
    }

    public static String toHex(long i) {
        return toHex(i, 16);
    }

    public static String toHex(int i) {
        return toHex(i, 8);
    }

    public static String toHex(short i) {
        return toHex(i, 4);
    }

    public static String toHex(byte i) {
        return toHex(i, 2);
    }

    public static String toHex(long i, int size) {
        boolean negative = i < 0;
        if (negative)
            i = -i - 1;
        StringBuilder s = new StringBuilder();
        long j = 0, t;
        while (negative ? j < size : i != 0) {
            t = negative ? 15 - i % 16 : i % 16;
            s.insert(0, (char) (t < 10 ? t + 48 : t + 55));
            i >>>= 4;
            j++;
        }
        return s.toString();
    }


    public static byte[] toByteArray(long i) {
        return toByteArray(i, 64);
    }

    public static byte[] toByteArray(int i) {
        return toByteArray(i, 32);
    }

    public static byte[] toByteArray(short i) {
        return toByteArray(i, 16);
    }

    public static byte[] toByteArray(long i, int size) {
        size /= 8;
        byte[] b = new byte[size];
        for (int j = 0; j < size; j++)
            b[j] = (byte) (i >>> (size - j - 1) * 8 & 0xFF);
        return b;
    }

    public static String toVarBinary(int i) {
        StringBuilder b = new StringBuilder();
        int part;
        do {
            part = i & 0x7F;
            i >>>= 7;
            if (i != 0)
                part |= 0x80;
            b.append(toBinary((byte) part));
        } while (i != 0);
        return b.toString();
    }

    public static byte[] toVarByteArray(int i) {
        List<Byte> b = new ArrayList<>();
        int part;
        do {
            part = i & 0x7F;
            i >>>= 7;
            if (i != 0)
                part |= 0x80;
            b.add((byte) part);
        } while (i != 0);
        return ArrayUtils.toPrimitiveArray(b);
    }

    public static int toDecimal(String s, int radix) {
        return Integer.parseInt(s, radix);
    }

    public static int toUnsigned(byte b) {
        return ((int) b) & 0xFF;
    }

    public static long toUnsigned(int i) {
        return ((long) i) & 0xFFFFFFFFL;
    }
}
