package org.wustrive.java.common.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.wustrive.java.common.properties.PropertiesConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 *
 * Description: 
 * 
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/3/20 16:39
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class OssUtil {
	public static String ACCESS_KEY;
	public static String ACCESS_SECRET;
	public static String BASE_URL;
	public static String BUCKET;
	public static String MIDDEL;
	public static String CDNURL;

	static {
		ACCESS_KEY = PropertiesConfig.getProperty("oss.key");
		ACCESS_SECRET = PropertiesConfig.getProperty("oss.secret");
		BASE_URL = PropertiesConfig.getProperty("oss.base.url");
		BUCKET = PropertiesConfig.getProperty("oss.bucket");
		MIDDEL = PropertiesConfig.getProperty("oss.middel");
		CDNURL=PropertiesConfig.getProperty("oss.cdn");
	}

	public static void deleteObject(OSSClient client, String key) {
		client.deleteObject(BUCKET, key);
	}

	public static String initMultiUpload(OSSClient client, String key) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("application/octet-stream");
		return initMultiUpload(client, key, metadata);
	}

	public static String initMultiUpload(OSSClient client, String key, ObjectMetadata metadata) {
		InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(BUCKET, key, metadata);
		InitiateMultipartUploadResult initiateMultipartUploadResult = client.initiateMultipartUpload(initiateMultipartUploadRequest);
		return initiateMultipartUploadResult.getUploadId();
	}

	public static List<PartSummary> getParts(OSSClient client, String key, String uploadId) {
		ListPartsRequest listPartsRequest = new ListPartsRequest(BUCKET, key, uploadId);

		// 获取上传的所有Part信息
		PartListing partListing = client.listParts(listPartsRequest);
		return partListing.getParts();
	}

	public static void finishedMultiUpload(OSSClient client, String key, String uploadId, List<PartETag> partETags) {

		CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(BUCKET, key, uploadId, partETags);

		// 完成分块上传
		CompleteMultipartUploadResult completeMultipartUploadResult = client.completeMultipartUpload(completeMultipartUploadRequest);

		// 打印Object的ETag
		System.out.println(completeMultipartUploadResult.getETag());
	}

	public static void multiUploadFile(OSSClient client, File partFile, long blckSize, int fileNo, String key, String uploadId) throws Exception {
		FileInputStream fis = new FileInputStream(partFile);

		// 跳到每个分块的开头
		// long skipBytes = blckSize * fileNo;
		// fis.skip(skipBytes);

		// 计算每个分块的大小
		long size = partFile.length();

		// 创建UploadPartRequest，上传分块

		UploadPartRequest uploadPartRequest = new UploadPartRequest();
		uploadPartRequest.setBucketName(BUCKET);
		uploadPartRequest.setKey(key);
		uploadPartRequest.setUploadId(uploadId);
		uploadPartRequest.setInputStream(fis);
		uploadPartRequest.setPartSize(size);
		uploadPartRequest.setPartNumber(fileNo + 1);

		UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);

		// 将返回的PartETag保存到List中。
		@SuppressWarnings("unused")
		PartETag partETag = uploadPartResult.getPartETag();

		// partETags.add(partETag);

		// 关闭文件
		fis.close();
	}

	// 上传文件
	public static boolean uploadFile(OSSClient client, String key, InputStream input, int len, String bucketName, String fileName) {
		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(len);
		String suffix = getFileSuffix(fileName);
		ImageFormat imgFormat = ImageFormat.getImageFormat(suffix);
		if (imgFormat == ImageFormat.UNKNOWN) {
			return false;
		}
		try {
			// 可以在metadata中标记文件类型
			objectMeta.setContentType("image/" + imgFormat.getDesc().toLowerCase());
			client.putObject(bucketName, key, input, objectMeta);
			input.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	// 上传文件
	public static boolean uploadFileOther(OSSClient client, String key, InputStream input, int len, String bucketName, String contentType) {
		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(len);
		try {
			// 可以在metadata中标记文件类型
			objectMeta.setContentType(contentType);
			client.putObject(bucketName, key, input, objectMeta);
			input.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static ObjectListing listObjects(OSSClient client, String key) {
		return client.listObjects(BUCKET, key);
	}

	static public OSSObject getObject(OSSClient client, String key) {
		return client.getObject(BUCKET, key);
	}

	static public boolean isObjectExits(OSSClient client, String key) {

		if (client.getObject(BUCKET, key) == null)
			return false;
		else
			return true;
	}

	static public OSSClient getOssClient() {
		return getOssClient(BASE_URL);
	}

	private static Boolean exists = null;
	static public OSSClient getOssClient(String baseUrl) {
		OSSClient ossClient = new OSSClient(baseUrl, ACCESS_KEY, ACCESS_SECRET);

		if(exists==null){
			exists = ossClient.doesBucketExist(BUCKET);
		}
		if (!exists) {
			ossClient.createBucket(BUCKET);
			ossClient.setBucketAcl(BUCKET, CannedAccessControlList.PublicRead);
		}

		return ossClient;
	}

	static public String getBookBucket() {
		return BUCKET;
	}

	static public String getBaseUrl() {
		return BASE_URL;
	}

	public static String getFileSuffix(String originalFileName) {
		if (originalFileName != null) {
			int pos = originalFileName.lastIndexOf(".");
			if (pos >= 0) {
				return originalFileName.substring(pos + 1, originalFileName.length());
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
}
