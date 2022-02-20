package hxy.dream.common.util;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * @author HealerJean
 * @ClassName AES
 * @date 2020/4/9  14:28.
 * @Description
 */
public class KeyCenterUtils {

    /**
     * 自己写加密逻辑
     */
    public static String encrypt(String src) {
        try {
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
            throw new RuntimeException("decrypt fail!", e);
        }
    }

}

