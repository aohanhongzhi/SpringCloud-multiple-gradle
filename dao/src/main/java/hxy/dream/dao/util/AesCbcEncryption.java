package hxy.dream.dao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class AesCbcEncryption {

    private static final Logger log = LoggerFactory.getLogger(AesCbcEncryption.class);

    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 128;
    // 使用与Golang相同的key和iv
    static String key = "qwertyuiopasdfgr";
    static String iv = "qwertyuiopasdfgh";

    public static void main(String[] args) {
        try {
            // 原始明文
            String plaintext = "oooooo灰灰";


            System.out.println("明文：" + plaintext);
            System.out.println("key：" + key);
            System.out.println("iv：" + iv);

            // 加密
            byte[] encrypted = cbcEncrypt(plaintext.getBytes(StandardCharsets.UTF_8), key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8));
            String encryptedString = bytesToHex(encrypted);
            System.out.println("加密后的密文：" + encryptedString);

            // Base64编码
            String encryptedBase64 = base64Encode(encrypted);
            System.out.println("Base64编码后的密文：" + encryptedBase64);

            // 解密
            byte[] encrypted1 = hexToBytes(encryptedString);
            byte[] decrypted = cbcDecrypt(encrypted1, key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8));
            String decryptedText = new String(decrypted, StandardCharsets.UTF_8);
            System.out.println("解密后的明文：" + decryptedText);

            // 封装后的测试
            String encrypt = encrypt(plaintext);
            System.out.println("加密后的base64文本：" + encrypt);
            String decrypt = decrypt(encrypt);
            System.out.println("解密后的文本：" + decrypt);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String rawString) {
        byte[] encrypted = null;
        try {
            encrypted = cbcEncrypt(rawString.getBytes(StandardCharsets.UTF_8), key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
        String encryptedString = bytesToHex(encrypted);
        System.out.println("加密后的密文：" + encryptedString);

        // Base64编码
        String encryptedBase64 = base64Encode(encrypted);
        return encryptedBase64;
    }

    public static String decrypt(String encryptedBase64) {
        byte[] encrypted1 = base64Decode(encryptedBase64);
        byte[] decrypted = null;
        try {
            decrypted = cbcDecrypt(encrypted1, key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
        String decryptedText = new String(decrypted, StandardCharsets.UTF_8);
        return decryptedText;
    }

    private static byte[] cbcEncrypt(byte[] text, byte[] key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        return cipher.doFinal(text);
    }

    private static byte[] cbcDecrypt(byte[] encrypted, byte[] key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        return cipher.doFinal(encrypted);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b & 0xFF));
        }
        return sb.toString();
    }

    public static String base64Encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] base64Decode(String rawString) {
        return Base64.getDecoder().decode(rawString.getBytes());
    }

    private static byte[] hexToBytes(String hex) {
        int length = hex.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }
}
