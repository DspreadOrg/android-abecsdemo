package com.dspread.ppcomlibrary.utils;

import android.os.Build;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtils {

    // AES 密钥长度 (128, 192 或 256 bits)
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/NoPadding";

    /**
     * 使用 AES/CBC/PKCS7Padding 加密数据
     *
     * @param plainText 明文字符串
     * @param key       密钥（必须为16、24或32字节）
     * @param iv        初始化向量（IV，必须为16字节）
     * @return Base64 编码的加密结果
     */
    public static byte[] encrypt(byte[] plainText, byte[] key, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            SecretKeySpec keySpec = new SecretKeySpec(key, KEY_ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encryptedData = cipher.doFinal(plainText);

            return encryptedData;
        } catch (Exception e) {
            throw new RuntimeException("AES加密失败", e);
        }
    }

    /**
     * 使用 AES/CBC/PKCS7Padding 解密数据
     *
     * @param cipherText Base64 编码的密文
     * @param key        密钥（必须为16、24或32字节）
     * @param iv         初始化向量（IV，必须为16字节）
     * @return 解密后的明文字符串
     */
    public static byte[] decrypt(byte[] cipherText, byte[] key, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            SecretKeySpec keySpec = new SecretKeySpec(key, KEY_ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decryptedData = cipher.doFinal(cipherText);

            return decryptedData;
        } catch (Exception e) {
            Log.e("AESUtils", "AES解密失败: "+ e);
            return null;
        }
    }
}
