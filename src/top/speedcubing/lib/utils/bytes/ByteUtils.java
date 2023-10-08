package top.speedcubing.lib.utils.bytes;

public class ByteUtils {
    public static String intToBinary(int i) {
        return signedToBinary(i, 0xFFFFFF);
    }

    public static String shortToBinary(int i) {
        return signedToBinary(i, 0xFFFF);
    }


    public static String byteToBinary(int i) {
        return signedToBinary(i, 0xFF);
    }


    public static String signedToBinary(int i, int cnt) {
        return Integer.toBinaryString(i & cnt);
    }
}
