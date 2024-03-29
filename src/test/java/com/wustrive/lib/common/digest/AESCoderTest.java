package com.wustrive.lib.common.digest;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.wustrive.java.common.digest.AESCoderBounc;

import static org.junit.Assert.assertEquals;

public class AESCoderTest {
	 /**
     * 测试
     *
     * @throws Exception
     */
    @Test
    public final void test() throws Exception {
         String inputStr = "W00000000001";
          byte[] inputData = inputStr.getBytes();
         System. err.println("原文:\t" + inputStr);

          // 初始化密钥
          byte[] key = AESCoderBounc.initKey(128);
         System. err.println("密钥:\t" + Base64.encodeBase64String(key));

          // 加密
         inputData = AESCoderBounc. encrypt(inputData, key);
         System. err.println("加密后:\t" + Base64.encodeBase64String(inputData));

          // 解密
          byte[] outputData = AESCoderBounc.decrypt(inputData, key);

         String outputStr = new String(outputData);
         System. err.println("解密后:\t" + outputStr);

          // 校验
          assertEquals(inputStr, outputStr);
    }
}
