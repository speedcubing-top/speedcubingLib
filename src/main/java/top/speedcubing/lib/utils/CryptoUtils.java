package top.speedcubing.lib.utils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class CryptoUtils {
    public static String toSHA256(File f) throws IOException, NoSuchAlgorithmException {
        return new BigInteger(1, MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(f.toPath()))).toString(16);
    }

    public static byte[] decryptRsa(KeyPair keyPair, byte[] bytes) throws GeneralSecurityException {
        return decryptRsa(keyPair.getPrivate(),bytes);
    }
    public static byte[] decryptRsa(byte[] key, byte[] bytes) throws GeneralSecurityException {
        return decryptRsa(toPrivateRSA(key), bytes);
    }

    public static byte[] decryptRsa(PrivateKey privateKey, byte[] bytes) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(bytes);
    }

    public static byte[] encryptRSA(KeyPair keyPair, byte[] bytes) throws GeneralSecurityException {
        return encryptRSA(keyPair.getPublic(),bytes);
    }
    public static byte[] encryptRSA(byte[] key, byte[] bytes) throws GeneralSecurityException {
        return encryptRSA(toPublicKeyRSA(key), bytes);
    }

    public static byte[] encryptRSA(PublicKey publicKey, byte[] bytes) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(bytes);
    }

    public static PublicKey toPublicKeyRSA(byte[] key) throws InvalidKeySpecException, NoSuchAlgorithmException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey toPrivateRSA(byte[] key) throws InvalidKeySpecException, NoSuchAlgorithmException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
