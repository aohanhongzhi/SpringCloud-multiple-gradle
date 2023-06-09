package hxy.dream.dao.util;

import java.util.Base64;

public class KeyCenterUtils {

    /**
     * 自己写加密逻辑
     */
    public static String encrypt(String src) {
        try {
            // 最好替换成 RSA 或者 AES 。 Base64只能算是编码，不算加密。
            String result = Base64.getEncoder().encodeToString(src.getBytes("UTF-8"));
            return result;
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 自己写解密逻辑
     */
    public static String decrypt(String src) {
        try {
            byte[] asBytes = Base64.getDecoder().decode(src);
            String result = new String(asBytes, "UTF-8");
            return result;
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!可能是因为这个不是加密后的文字，而是正常的字符", e);
        }
    }

}

