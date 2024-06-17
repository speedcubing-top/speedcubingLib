package top.speedcubing.lib.utils.bytes;

public class DataConversion {

    public static byte[] hexStringToByteArray(String data) {
        int len = data.length();
        int d = len % 2;
        len -= d;
        byte[] b = new byte[len / 2 + d];

        for (int i = 0; i < len; i += 2) {
            b[i / 2] = (byte) ((Character.digit(data.charAt(i), 16) << 4)
                    + Character.digit(data.charAt(i + 1), 16));
        }
        if (d == 1) {
            b[len / 2] = (byte) Character.digit(data.charAt(len), 16);
        }
        return b;
    }

    public static String byteArrayToHexString(byte[] data) {
        StringBuilder s = new StringBuilder(data.length * 2);
        for (byte b : data) {
            String s2 = NumberConversion.toHex(b);
            int r = 2 - s2.length();
            while (r-- > 0) {
                s.append("0");
            }
            s.append(s2);
        }
        return s.toString();
    }
}
