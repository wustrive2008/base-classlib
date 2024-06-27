package org.wustrive.java.common.digest;


import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class AESCoderBounc {
	 /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法 / 工作模式 / 填充方式
     * Java 6支持PKCS5Padding填充方式
     * Bouncy Castle支持PKCS7Padding填充方式
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding" ;
    
    public static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding" ;

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {

          // 实例化AES密钥材料
         SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM );

          return secretKey;
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key 密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte [] decrypt(byte[] data, byte[] key) throws Exception {

          // 还原密钥
         Key k = toKey(key);

          /*
          * 实例化
          * 使用PKCS7Padding填充方式，按如下方式实现
          * Cipher.getInstance(CIPHER_ALGORITHM, "BC");
          */
         Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

          // 初始化，设置为解密模式
         cipher.init(Cipher. DECRYPT_MODE, k);

          // 执行操作
          return cipher.doFinal(data);
    }
    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key 密钥
     * @param iv 偏移量
     * @return String 解密数据
     * @throws Exception
     */
    public static String decrypt_CBC(String data, String key,String iv) throws Exception {
	  try{
          byte[] encrypted1 = Base64Decoder.decode(data);
          Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
          SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
          IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
          cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
          byte[] original = cipher.doFinal(encrypted1);
          String originalString = new String(original);
          return originalString;
      }
      catch (Exception e) {
          e.printStackTrace();
          return null;
      }
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key 密钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte [] encrypt(byte[] data, byte[] key) throws Exception {

          // 还原密钥
         Key k = toKey(key);

          /*
          * 实例化
          * 使用PKCS7Padding填充方式，按如下方式实现
          * Cipher.getInstance(CIPHER_ALGORITHM, "BC");
          */
         Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

          // 初始化，设置为加密模式
         cipher.init(Cipher. ENCRYPT_MODE, k);

          // 执行操作
          return cipher.doFinal(data);
    }
    
    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key 密钥
     * @param iv 偏移量
     * @return String 加密数据
     * @throws Exception
     */
    public static String encrypt_CBC(String data, String key,String iv) throws Exception {
    	try {
         	Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
             int blockSize = cipher.getBlockSize();
  
             byte[] dataBytes = data.getBytes();
             int plaintextLength = dataBytes.length;
             if (plaintextLength % blockSize != 0) {
                 plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
             }
  
             byte[] plaintext = new byte[plaintextLength];
             System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
              
             SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
             IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
  
             cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
             byte[] encrypted = cipher.doFinal(plaintext);
  
             return Base64Encoder.encode(encrypted);
  
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
    }

    /**
     * 生成密钥 <br>
     *
     * @return byte[] 二进制密钥
     * @throws Exception
     */
    public static byte [] initKey() throws Exception {

          // 实例化
         KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

          /*
          * AES 要求密钥长度为 128位、192位或 256位
          */
         kg.init(256);

          // 生成秘密密钥
         SecretKey secretKey = kg.generateKey();

          // 获得密钥的二进制编码形式
          return secretKey.getEncoded();
    }
    
    /**
     * 生成密钥 <br>
     *
     * @param keylenght 密钥长度为 128位、192位或 256位
     * @return byte[] 二进制密钥
     * @throws Exception
     */
    public static byte [] initKey(int keylenght) throws Exception {

          // 实例化
         KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

          /*
          * AES 要求密钥长度为 128位、192位或 256位
          */
         kg.init(keylenght);

          // 生成秘密密钥
         SecretKey secretKey = kg.generateKey();

          // 获得密钥的二进制编码形式
          return secretKey.getEncoded();
    }
}
