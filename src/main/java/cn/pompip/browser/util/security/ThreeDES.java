package cn.pompip.browser.util.security;


import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SecureRandom;

/**
 * 使用3DES加密与解密,可对byte[],String类型进行加密与解密 密文可使用String,byte[]存储.
 *
 * @author xiejiong 2013-6-3 11:10:54
 */
public class ThreeDES {
    private Key key; // 密钥

    /**
     * 根据参数生成KEY
     *
     * @param strKey 密钥字符串
     */
    public void getKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            _generator.init(new SecureRandom(strKey.getBytes()));
            this.key = _generator.generateKey();
            _generator = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密String明文输入,String密文输出
     *
     * @param strMing String明文
     * @return String密文
     */
    public String getEncString(String strMing) throws UnsupportedEncodingException {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        byteMing = strMing.getBytes("UTF8");
        byteMi = this.getEncCode(byteMing);
        strMi = Base64Utils.encodeToString(byteMi);

        return strMi;
    }

    /**
     * 解密 以String密文输入,String明文输出
     *
     * @param strMi String密文
     * @return String明文
     */
    public String getDesString(String strMi) throws UnsupportedEncodingException {
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";

        byteMi = Base64Utils.decodeFromString(strMi);
        byteMing = this.getDesCode(byteMi);
        strMing = new String(byteMing, "UTF8");

        return strMing;
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     *
     * @param byteS byte[]明文
     * @return byte[]密文
     */
    private byte[] getEncCode(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     *
     * @param byteD byte[]密文
     * @return byte[]明文
     */
    private byte[] getDesCode(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

}