package top.speedcubing.lib.utils.bytes;

public class NumberConversion {
    public static String toBinary(int i) {
        return toBinary(i, 0xFFFFFF);
    }

    public static String toBinary(short i) {
        return toBinary(i, 0xFFFF);
    }


    public static String toBinary(byte i) {
        return toBinary(i, 0xFF);
    }

    public static String toBinary(char i) {
        return toBinary(i, 0xFF);
    }

    public static String toBinary(int i, int range) {
        return Integer.toBinaryString(i & range);
    }

    public static String toHex(int i) {
        return toHex(i, 0xFFFFFFFF);
    }

    public static String toHex(short i) {
        return toHex(i, 0xFFFF);
    }

    public static String toHex(byte i) {
        return toHex(i, 0xFF);
    }

    public static String toHex(char i) {
        return toHex(i, 0xFF);
    }

    public static String toHex(int i, int range) {
        return Integer.toHexString(i & range).toUpperCase();
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
