package org.wustrive.java.common.oss;

import com.aliyun.oss.OSSClient;
import org.wustrive.java.common.secret.MD5Encrypt;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;


/**
 * Description: 阿里云oss工具类
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/3/20 16:38
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class AliOSSHelper {
    private static Logger logger = LoggerFactory.getLogger(AliOSSHelper.class);
    /**
     * 删除 阿里云的 文件
     */
    public static void deleteObjectByCdnFull(String fullUrl) {
        try {
            URL url = new URL(fullUrl);
            String key = url.getPath();
            if (key != null) {
                if (key.startsWith("/")) {
                    key = key.substring(1, key.length());
                }
                deleteObject(key);
            }
        } catch (Exception e) {
        }

    }

    public static void deleteObject(String key) {
        OSSClient client = OssUtil.getOssClient();
        OssUtil.deleteObject(client, key);
    }


    public static String getKey(String fullUrl) {
        try {
            new URL(fullUrl);
        } catch (Exception e) {
            return null;
        }
        String[] keys = fullUrl.split("/" + OssUtil.BUCKET + "/");
        if (keys.length >= 2) {
            return keys[1];
        }
        return null;
    }

    /**
     * 服务器上传文件，返回一个完整的url请求路径
     * @param imageFormat
     * @param input
     */
    public static String uploadFile(ImageFormat imageFormat, InputStream input) {
        return uploadFile(imageFormat, input, OssUtil.MIDDEL);
    }

    /**
     * 服务器上传文件，返回一个完整的url请求路径
     *
     * @param imageFormat
     * @param input
     * @param middle
     */
    public static String uploadFile(ImageFormat imageFormat, InputStream input, String middle) {
        String key = null;
        try {
            byte[] bytes = IOUtils.toByteArray(input);
            String md5 = MD5Encrypt.MD5(bytes);
            String fileName = md5 + "." + imageFormat.getDesc().toLowerCase();
            key = middle + "/" + fileName;
            input = new ByteArrayInputStream(bytes);
            OSSClient client = OssUtil.getOssClient();
            boolean result = OssUtil.uploadFile(client, key, input, input.available(), OssUtil.BUCKET, fileName);
            if (!result) {
                return null;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        // 上传文件到服务器的路径
        String serverUrl = "http://" + OssUtil.CDNURL + "/" + key;
        if (OssUtil.CDNURL == null || OssUtil.CDNURL.equals("")) {
            serverUrl = "http://" + OssUtil.BUCKET + "." + OssUtil.BASE_URL + "/" + key;
        }
        return serverUrl;
    }

}
