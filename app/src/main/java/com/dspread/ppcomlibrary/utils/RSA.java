package com.dspread.ppcomlibrary.utils;

import android.util.Base64;
import android.util.Log;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAKeyGenParameterSpec;

import javax.crypto.Cipher;

public class RSA {
    RSAPublicKey publicKey;
    RSAPrivateKey privateKey;
    public void generateRSAKeyPair(String exp) {
        try {
            // 获取 RSA 密钥对生成器实例
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");

            // 设置密钥大小为 2048 位，并指定公钥指数（例如 65537 或 3）
            // 使用 BigInteger 指定指数值
            BigInteger publicExponent = new BigInteger(exp); // 可替换为 new BigInteger("3")

            RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(2048, publicExponent);
            kpg.initialize(spec, new SecureRandom());

            // 生成密钥对
            KeyPair keyPair = kpg.generateKeyPair();

            // 获取公钥和私钥
            publicKey = (RSAPublicKey) keyPair.getPublic();
            privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Log.d("","Public: " + publicKey.getModulus().toString(16));
            Log.d("","Public Exponent: " + publicKey.getPublicExponent().toString(16));
            Log.d("","Private: " + privateKey.getPrivateExponent().toString(16));


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }
    public byte[] decryptWithPrivateKey(byte[] encryptedData) {
        if(encryptedData.length != 256){
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            //Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decryptedBytes = cipher.doFinal(encryptedData);
            Log.d("NewTestActivity", "decryptedBytes: "+ POSUtil.byteArray2Hex(decryptedBytes));
            return decryptedBytes;

        } catch (Exception e) {
            Log.e("RSA", "私钥解密失败 (byte[] 输入)", e);
            return null;
        }
    }
    public String encryptWithPublicKey(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);

        } catch (Exception e) {
            Log.e("RSA", "公钥加密失败", e);
            return null;
        }
    }

    public String getPublicKeyModulus() {
        return publicKey.getModulus().toString(16);
    }

    public String getPublicKeyExponent() {
        return publicKey.getPublicExponent().toString(16);
    }

    public String getPrivatekeyModulus() {
        return privateKey.getPrivateExponent().toString(16);
    }
}

