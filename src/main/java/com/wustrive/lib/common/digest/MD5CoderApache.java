package com.wustrive.lib.common.digest;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5CoderApache {
	 /**
     * MD5加密
     *
     * @param data
     *            待加密数据
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte [] encodeMD5(String data) throws Exception {

          // 执行消息摘要
          return DigestUtils.md5(data);
    }

    /**
     * MD5加密
     *
     * @param data
     *            待加密数据
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeMD5Hex(String data) throws Exception {

          // 执行消息摘要
          return DigestUtils.md5Hex(data);
    }

}
