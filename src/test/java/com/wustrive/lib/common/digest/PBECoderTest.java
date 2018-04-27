package com.wustrive.lib.common.digest;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.wustrive.java.common.digest.PBECoderJDK;

import static org.junit.Assert.assertEquals;

public class PBECoderTest {
	@Test
    public void test() throws Exception {

         String inputStr = "PBE";
         System. err.println("原文：" + inputStr);
          byte[] input = inputStr.getBytes();

         String pwd = "snowolf@zlex.org";
         System. err.println("密码：\t" + pwd);

          // 初始化盐
          byte[] salt = PBECoderJDK.initSalt();
         System. err.println("盐：\t" + Base64.encodeBase64String(salt));

          // 加密
          byte[] data = PBECoderJDK.encrypt(input, pwd, salt);
         System. err.println("加密后：\t" + Base64.encodeBase64String(data));
         
          // 解密
          byte[] output = PBECoderJDK.decrypt(data, pwd, salt);
         String outputStr = new String(output);
         System. err.println("解密后：\t" + outputStr);
         
          // 校验
          assertEquals(inputStr, outputStr);
    }

}
