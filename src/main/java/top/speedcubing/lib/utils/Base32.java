package top.speedcubing.lib.utils;

public class Base32 {

    public static String encode(byte[] bytes) {
        char[] table = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7'};

        int buffer = 0;
        int nBitsIn = 0;
        int nBitsOut = 0;
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            nBitsIn += 8;
            buffer |= (b << (32 - nBitsIn));
            while (nBitsIn >= 5) {

                builder.append(table[buffer >>> 27]);
                nBitsOut += 5;

                nBitsIn -= 5;
                buffer <<= 5;
            }
        }

        if (nBitsIn != 0) {
            builder.append(table[buffer >>> 27]);
            nBitsOut += 5;
        }

        if (nBitsOut % 40 != 0) {
            builder.append("=".repeat((40 - nBitsOut % 40) / 5));
        }
        return builder.toString();
    }

    public static byte[] decode(String base32) {
        base32 = base32.replace("=", "");
        char[] table = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7'};

        byte[] bytes = new byte[base32.length() * 5 / 8];
        int buffer = 0;
        int nBitsIn = 0;
        int i = 0;
        for (char c : base32.toCharArray()) {
            int b = -1;
            for (int j = 0; j < table.length; j++) {
                if (table[j] == c) {
                    b = j;
                    break;
                }
            }
            nBitsIn += 5;
            buffer |= (b << (32 - nBitsIn));
            if (nBitsIn >= 8) {
                bytes[i++] = (byte) (buffer >> 24);
                nBitsIn -= 8;
                buffer <<= 8;
            }
        }
        return bytes;
    }
}
