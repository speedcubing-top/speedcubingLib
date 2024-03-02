package top.speedcubing.lib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtils {
    public static String toSHA256(File f) {
        //new BigInteger(1, MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(f.toPath()))).toString(16);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (DigestInputStream dis = new DigestInputStream(new FileInputStream(f), digest)) {
                while (dis.read() != -1) {
                }
                char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
                byte[] hash = digest.digest();
                char[] hexChars = new char[hash.length * 2];
                for (int j = 0; j < hash.length; j++) {
                    int v = hash[j] & 0xFF;
                    hexChars[j * 2] = HEX_ARRAY[v >>> 4];
                    hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
                }
                return new String(hexChars);
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
