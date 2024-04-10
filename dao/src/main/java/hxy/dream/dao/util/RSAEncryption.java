package hxy.dream.dao.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


/**
 * @author eric
 */
public class RSAEncryption {

    public static final String KEY_ALGORITHM = "RSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final Logger log = LoggerFactory.getLogger(RSAEncryption.class);

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 初始化RSAKey
     *
     * @return
     * @throws Exception
     */

    private static Map<String, RSAKey> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, RSAKey> keyMap = new HashMap<String, RSAKey>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }


    /**
     * 获取公钥
     *
     * @param key
     * @return
     * @throws Exception
     */

    public static PublicKey getPublicKey(String key) throws Exception {

        Base64 base64 = new Base64();
        byte[] keyBytes = base64.decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 获取私钥
     *
     * @param key
     * @return
     * @throws Exception
     */

    public static PrivateKey getPrivateKey(String key) throws Exception {
        Base64 base64 = new Base64();
        byte[] keyBytes = base64.decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    //***************************签名和验证*******************************
    public static byte[] sign(byte[] data, String privateKeyStr) throws Exception {
        PrivateKey priK = getPrivateKey(privateKeyStr);
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initSign(priK);
        sig.update(data);
        return sig.sign();
    }

    public static boolean verify(byte[] data, byte[] sign, String publicKeyStr) throws Exception {
        PublicKey pubK = getPublicKey(publicKeyStr);
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(pubK);
        sig.update(data);
        return sig.verify(sign);
    }

    /**
     * 加密
     *
     * @param plainText
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] plainText, String publicKeyStr) throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = plainText.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        byte[] cache;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(plainText, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(plainText, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptText = out.toByteArray();
        out.close();
        return encryptText;
    }

    /**
     * @param encryptText
     * @param privateKeyStr
     * @return
     * @throws Exception
     * @Description 解密
     */
    private static byte[] decrypt(byte[] encryptText, String privateKeyStr) throws Exception {
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int inputLen = encryptText.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptText, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptText, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] plainText = out.toByteArray();
        out.close();
        return plainText;
    }


    /**
     * RSA 可以用于JWT的用户信息加密，JWT的第二字段Payload一般是用户的一些信息和Claim（声明和权利），JWT只是将这一部分base64编码了，并没有对其加密
     * <p>
     * 公钥可以用来加密，并且也可以用来解密。私钥可以用来加密也可以用来解密。
     *
     * @param args
     */
//    public static void main(String[] args) {
//
//        String input = "Hello World!";
////        second(input);
//
//        String s = infoEncrypt(input);
//        System.out.println("加密后密码" + s);
//        String s1 = infoDecrypt(s);
//        System.out.println("解密后的密码" + s1);
//    }

    static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLUKF8+tqeLt+MESnrFxrUDkt56MocLoh/On2I\n" +
            "+jm/0W+BcnAlPC3Q6xH5lQjNklqcN+XldKmGqkDQS/qT9/ZcNWNv1oI7skAP7TqPupRdMIzvd0rg\n" +
            "W5EqTidpTDMiCTk5B4CFQtTYLGWLh6ihql/v4qvJYZTr5jem5VvN02EgdQIDAQAB";
    static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAItQoXz62p4u34wRKesXGtQOS3no\n" +
            "yhwuiH86fYj6Ob/Rb4FycCU8LdDrEfmVCM2SWpw35eV0qYaqQNBL+pP39lw1Y2/WgjuyQA/tOo+6\n" +
            "lF0wjO93SuBbkSpOJ2lMMyIJOTkHgIVC1NgsZYuHqKGqX+/iq8lhlOvmN6blW83TYSB1AgMBAAEC\n" +
            "gYEAgXQhOyNWthpUO7FPRFap5VaLJ/L6q4lJ6P+O2xO5SdbxSFXwg4kSAcoNX9/u2Ccg73y5safg\n" +
            "R3Q42dVgONL3JnXrDmNZdg2GUl9BZVzLEmFkhkUcKIzTtT+wYounWzkc92UkH0VXf6QZdIaPm7p5\n" +
            "36367MMlCvyw77EuUyTQk4ECQQD8NMllkWfMPyGjH9qQqrPtuNVbjS+fxaim8X/7bzwtFOyfXTa5\n" +
            "4xne6oUKO2RehUt85eIyPDC6J5fvDaNSmLJxAkEAjWkdWMURtvQkuK5GBi/8KDTh1ICepRf2xa5Q\n" +
            "cRWFyzmgS9+5vcHKj/8A8ODB3Yyg2aau0h3ce9eCCyOY9L2IRQJACt4bb4z8dAikYmsU9/bjGfNE\n" +
            "/lTvIGtcARWW9jas2SR1rchOe2QV0U05vl8gzBHiVtdxD8kYMG1UVrC3wO9jgQJAJBt48NexBEQm\n" +
            "tDlbi+zot+N43mfRGlqaGjO+OoHCXffi5DzGEpvO+yGjvd3F4qor0CdtcB1L1RtcCzgVsDhM+QJA\n" +
            "TJ0IBDm3gLq14lxs0z5JwKCEwG73dV99KkguN82a1MqsstcCL2WRB8EGxMwDDkoKG/AzHnU4nvWj\n" +
            "EfLxRCyXew==";

    static void second(String input) {

        byte[] cipherText;
        try {
            System.out.println("测试可行性-------------------");
            System.out.println("\n明文：" + input);
            cipherText = encrypt(input.getBytes(), publicKey);
            //加密后的东西
            System.out.println("密文：" + new String(cipherText));
            String base64String = Base64.encodeBase64String(cipherText);
            System.out.println("Base64处理后" + base64String);
            System.out.println("Base64处理后长度" + base64String.length());
            byte[] bytes = Base64.decodeBase64(base64String);
            System.out.println("base64解码" + new String(bytes));

            //开始解密
            byte[] plainText = decrypt(cipherText, privateKey);
            System.out.println("解密后明文：" + new String(plainText));

        } catch (Exception e) {
            log.error("\n====>{}", e.getMessage(), e);
        }


    }

    /**
     * 信息加密，相同输入加密信息不一样
     *
     * @param rowInfo
     * @return 加密后内容，base64编码,长度172
     */
    public static String infoEncrypt(String rowInfo) {
        try {
            byte[] cipherText = encrypt(rowInfo.getBytes(), publicKey);
            String base64String = Base64.encodeBase64String(cipherText);
            return base64String;
        } catch (Exception e) {
            log.error("\n====>{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 信息解密
     *
     * @param base64EncryptInfo
     * @return
     */
    public static String infoDecrypt(String base64EncryptInfo) {
        byte[] cipherText = Base64.decodeBase64(base64EncryptInfo);
        try {
            byte[] plainText = decrypt(cipherText, privateKey);
            return new String(plainText);
        } catch (Exception e) {
            log.error("\n====>{}", e.getMessage(), e);
        }
        return null;
    }
}

