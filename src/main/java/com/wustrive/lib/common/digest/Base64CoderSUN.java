package com.wustrive.lib.common.digest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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
          BASE64Encoder encoder = new BASE64Encoder();
          byte[] b = data.getBytes(ENCODING);
          return encoder.encodeBuffer(b);
     }

     /**
     * Base64解码
     *
     * @param data
     * @return
     * @throws Exception
     */
     public static String decode(String data) throws Exception {
          BASE64Decoder decoder = new BASE64Decoder();
          byte[] b = decoder.decodeBuffer(data);
          return new String(b, ENCODING);
     }
}
