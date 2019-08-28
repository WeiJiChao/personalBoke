package com.jwBlog.utils.encrypt;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * devFrame Created by 许林 on 2017/6/1.
 */
public class DesJsEnc {
    Key key;
    public DesJsEnc(String str) {
        setKey(str);// 生成密匙
    }
    public DesJsEnc() {
        setKey("12345678");
    }
    /**
     * 根据参数生成KEY
     */
    private void setKey(String strKey) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            this.key  = keyFactory.generateSecret(new DESKeySpec(strKey.getBytes("UTF8")));
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        }
    }
    /**
     * 加密String明文输入,String密文输出
     */
    public String encrypt(String strMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        try {
            byteMing = strMing.getBytes("UTF8");
            byteMi = this.getEncCode(byteMing);
            strMi = Base64.encodeBase64String(byteMi);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     *
     * @param byteS
     * @return
     */
    private byte[] getEncCode(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key,SecureRandom.getInstance("SHA1PRNG"));
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }
}
