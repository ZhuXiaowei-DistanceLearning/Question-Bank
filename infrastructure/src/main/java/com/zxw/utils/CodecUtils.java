package com.zxw.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class CodecUtils {
    public static void main(String[] args) {
        // 假设 hexString 是一个十六进制字符串
        String hexString = "67F1E5860D482CB6963FDCE53FF9390864BB53738DEDFBE142804CEE74E6228F";

        try {
            // 使用 Hex.decodeHex 方法将十六进制字符串转换为字节数组
            byte[] byteArray = Hex.decodeHex(hexString.toCharArray());

            // 将字节数组转换为原始字符串
            String originalString = new String(byteArray);

            // 输出结果
            System.out.println(originalString);
        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }
}
