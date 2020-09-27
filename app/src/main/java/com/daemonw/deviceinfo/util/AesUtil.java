package com.daemonw.deviceinfo.util;

import java.security.Key;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil {
    private final static String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+";

    public static Cipher getAesCbcCipher(byte[] key, byte[] iv, boolean encrypt) throws Exception {
        int mode = encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
        Cipher c = Cipher.getInstance("AES/CBC/PKCS7Padding");
        Key keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        c.init(mode, keySpec, ivSpec);
        return c;
    }

    public static byte[] encrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
        Cipher c = getAesCbcCipher(key, iv, true);
        return c.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
        Cipher c = getAesCbcCipher(key, iv, false);
        return c.doFinal(data);
    }


    //generate random string with specified length
    public static String randomString(int strLen) {
        Random random = new Random();
        char[] str = new char[strLen];
        for (int i = 0; i < strLen; i++) {
            random.setSeed(System.nanoTime());
            str[i] = ALPHABET.charAt(random.nextInt() % ALPHABET.length());
        }
        return new String(str);
    }


    //generate random string with specified length
    public static byte[] randomBytes(int len) {
        Random random = new Random();
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            random.setSeed(System.nanoTime());
            bytes[i] =(byte)(random.nextInt() % 128);
        }
        return bytes;
    }

}
