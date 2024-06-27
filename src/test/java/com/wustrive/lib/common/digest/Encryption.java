package com.wustrive.lib.common.digest;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
	
	public static void main(String[] args) throws Exception {
		System.out.println(encrypt());
		System.out.println(desEncrypt());
	}
    public static String encrypt() throws Exception {
        try {
            String data = "Test String";
            String key = "1234567812345678";
            String iv = "1234567812345678";
 
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int blockSize = cipher.getBlockSize();
 
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
 
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
             
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
 
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
 
            return Base64Encoder.encode(encrypted);
 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 
    public static String desEncrypt() throws Exception {
        try
        {
            String data = "2fbwW9+8vPId2/foafZq6fnv2wTOKuxMP7azMqv4UY4=";
            String key = "1234567812345678";
            String iv = "1234567812345678";
             
            byte[] encrypted1 = Base64Decoder.decode(data);
             
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
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
}
