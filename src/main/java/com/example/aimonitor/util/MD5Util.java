package com.example.aimonitor.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * 使用 MD5 对输入字符串进行加密
     * @param input 需要加密的字符串
     * @return 加密后的 MD5 哈希值（32位小写）
     */
    public static String encrypt(String input) {
        try {
            // 创建 MD5 MessageDigest 实例
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 输入字符串转为字节数组并计算哈希值
            byte[] digest = md.digest(input.getBytes());

            // 将字节数组转为 16 进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b & 0xff));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}
