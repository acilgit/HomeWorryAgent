package com.android.housingonitoringagent.homeworryagent.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class AESUtil {

    private static final String KEY_ALGORTHM = "AES";
    private static final String ALGORTHM = "AES/ECB/PKCS5Padding";

    public static SecretKeySpec getSecretKeySpec(byte[] password, int keySize) {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG","Crypto");
            random.setSeed(password);

            KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORTHM);
            kgen.init(keySize, random);

            SecretKey secretKey = kgen.generateKey();
            byte[] encoded = secretKey.getEncoded();

            return new SecretKeySpec(encoded, KEY_ALGORTHM);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private static Cipher getEncryptCipher(SecretKeySpec key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(ALGORTHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher;
    }

    private static Cipher getDecryptCipher(SecretKeySpec key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(ALGORTHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher;
    }

    /**
     2. * 加密
     3. *
     4. * @param content 需要加密的内容
     5. * @param password  加密密码
     6. * @return
     7. */
    public static byte[] encrypt(byte[] content, SecretKeySpec keySpec) {
        try {
            Cipher cipher = getEncryptCipher(keySpec);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**解密
     2. * @param content  待解密内容
     3. * @param password 解密密钥
     4. * @return
     5. */
    public static byte[] decrypt(byte[] content, SecretKeySpec keySpec) {
        try {
            Cipher cipher = getDecryptCipher(keySpec);

            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static String listToString(List<FileUploadSuccessBean.ContentEntity> list, char separator) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < list.size(); i++) {
//            sb.append(list.get(i).getViewPath()).append(separator);
//        }
//        return sb.toString().substring(0, sb.toString().length() - 1);
//    }

}
