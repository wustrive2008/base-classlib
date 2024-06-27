package org.wustrive.java.common.digest;


import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;

public  class Base64CoderSUN {
	 /**
     * 字符编码
     */
     public final static String ENCODING = "UTF-8";

     /**
     * Base64编码
     *
     * @param data
     * @return
     * @throws Exception
     */
     public static String encode(String data) throws Exception {
          byte[] b = data.getBytes(ENCODING);
          return Base64Encoder.encode(b);
     }

     /**
     * Base64解码
     *
     * @param data
     * @return
     * @throws Exception
     */
     public static String decode(String data) throws Exception {
          byte[] b = Base64Decoder.decode(data);
          return new String(b, ENCODING);
     }
}
