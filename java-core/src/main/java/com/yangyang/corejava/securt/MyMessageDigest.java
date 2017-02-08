package com.yangyang.corejava.securt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyMessageDigest {
    public static final String PLAIN_TEXT = "123456";
    public static final String MD_ALGORITHM = "MD5";
    public static final String SHA_ALGORITHM = "SHA-512";
    public static final String MAC_ALGORITHM = "HmacSHA512";

    public static void main(String[] args) {
        System.out.println("MD5: " + MD5(PLAIN_TEXT.getBytes()));
        System.out.println("SHA-512: " + SHA(PLAIN_TEXT.getBytes()));
        System.out.println("HmacSHA512：" + MAC(PLAIN_TEXT.getBytes()));
    }

    /**
     * 1.消息摘要算法，MD家族，有MD2 MD4 MD5，其中MD4 JDK不支持
     * 
     * @param plainText
     * @return
     */
    public static String MD5(byte[] plainText) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(MD_ALGORITHM);
            return Base64.encodeBase64String(messageDigest.digest(plainText));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 2.SHA Security Hash Algorithm 安全散列算法，固定长度摘要信息 SHA-1 SHA-2( SHA-224
     * SHA-256 SHA-384 SHA-512) 使用的依然是MessageDigest类，JDK不支持224
     * 
     * @param plainText
     * @return
     */
    public static String SHA(byte[] plainText) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(SHA_ALGORITHM);
            return Base64.encodeBase64String(messageDigest.digest(plainText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 3.MAC(Message Authentication Code) 消息认证码算法，是含有密钥散列函数算法。
     * 兼容了MD和SHA的特性。
     * 加密过程三步走，与后面要介绍的对称加密和非对称加密是相似的
     * 1) 传入算法，实例化一个加密器
     * 2) 传入密钥，初始化加密器
     * 3) 调用doFinal方法进行加密
     * @param plainText
     * @return
     */
    public static String MAC(byte[] plainText) {

        try {
            byte[] secretBytes = generatorMACSecretKey();
            SecretKey key = restoreMACSecretKey(secretBytes);
            Mac mac = Mac.getInstance(MAC_ALGORITHM);
            mac.init(key);
            return Base64.encodeBase64String(mac.doFinal(plainText));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    /**
     * MAC生成随机密钥 两步走 1.创建一个KeyGenerator 2.调用KeyGenerator.generateKey方法
     * 
     * @return
     */
    public static byte[] generatorMACSecretKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(MAC_ALGORITHM);
            SecretKey key = keyGenerator.generateKey();
            return key.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原密钥
     * 
     * @param secretBytes
     * @return
     */
    public static SecretKey restoreMACSecretKey(byte[] secretBytes) {
        SecretKey key = new SecretKeySpec(secretBytes, MAC_ALGORITHM);
        return key;
    }
}