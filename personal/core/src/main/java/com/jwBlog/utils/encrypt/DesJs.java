package com.jwBlog.utils.encrypt;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.SecureRandom;


/**  
 * des加密解密  
 *   
 * @author  
 *   
 */  
public class DesJs {  
  
    Key key;  
  
    public DesJs(String str) {  
        setKey(str);// 生成密匙  
    }  
  
    public DesJs() {  
        setKey("12345678");  
    }  
  
    /**  
     * 根据参数生成KEY  
     */  
    public void setKey(String strKey) {  
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
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            strMi = Base64.encodeBase64String(md.digest(byteMi));
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
     * 解密 以String密文输入,String明文输出  
     *   
     * @param strMi  
     * @return  
     */  
    public String decrypt(String strMi) {
        byte[] byteMing = null;  
        byte[] byteMi = null;  
        String strMing = "";  
        try {  
        	strMi=strMi.replaceAll(" ", "+");
            byteMi = Base64.decodeBase64(strMi);
            byteMing = this.getDesCode(byteMi);  
            strMing = new String(byteMing, "UTF8");  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            byteMing = null;  
            byteMi = null;  
        }  
        return strMing;  
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
  
    /**  
     * 解密以byte[]密文输入,以byte[]明文输出  
     *   
     * @param byteD  
     * @return  
     */  
    private byte[] getDesCode(byte[] byteD) {  
        Cipher cipher;  
        byte[] byteFina = null;  
        try {  
            cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.DECRYPT_MODE, key,SecureRandom.getInstance("SHA1PRNG"));  
            byteFina = cipher.doFinal(byteD);  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing SqlMap class. Cause: " + e);  
        } finally {  
            cipher = null;  
        }  
        return byteFina;  
    }

    public static void main(String[] args) {
        String str="BASE64Decoder是内部专用 API, 可能会在未来发行版中删除";
        String encStr=new DesJs().encrypt(str);
        System.out.println(encStr);
        String str2=new DesJs().decrypt(encStr);
        System.out.println(str2);
    }
}