package org.wustrive.java.common.digest;

import java.security.MessageDigest;

public class MDCoderSUN {
	/**
     * MD2加密
     *
     * @param data
     *            待加密数据
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte [] encodeMD2(byte[] data) throws Exception {
          // 初始化MessageDigest
         MessageDigest md = MessageDigest.getInstance("MD2");

          // 执行消息摘要
          return md.digest(data);
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
    public static byte [] encodeMD5(byte[] data) throws Exception {
          // 初始化MessageDigest
         MessageDigest md = MessageDigest.getInstance("MD5");

          // 执行消息摘要
          return md.digest(data);
    }

}
