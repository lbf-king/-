package cn.wolfcode.wolf2w.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 文件上传工具
 */
public class UploadUtil {
	//阿里域名
	public static final String ALI_DOMAIN = "https://wolf2w-66-2.oss-cn-guangzhou.aliyuncs.com/";

	public static String uploadAli(MultipartFile file) throws Exception {
		//生成文件名称
		String uuid = UUID.randomUUID().toString();
		String orgFileName = file.getOriginalFilename();//获取真实文件名称 xxx.jpg
		String ext= "." + FilenameUtils.getExtension(orgFileName);//获取拓展名字.jpg
		String fileName =uuid + ext;//xxxxxsfsasa.jpg

		// Endpoint以杭州为例，其它Region请按实际情况填写。
		String endpoint = "http://oss-cn-guangzhou.aliyuncs.com";
		// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，
		// 请登录 https://ram.console.aliyun.com 创建。
		String accessKeyId = "LTAI5tQ92ZrnvAkHKFkhhoBW";
		String accessKeySecret = "bTyKQAsCYV9y42tPZGcsgN6sGx9ZQN";
		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);
		// 上传文件流。
		ossClient.putObject("wolf2w-66-2", fileName, file.getInputStream());
		// 关闭OSSClient。
		ossClient.shutdown();
		return ALI_DOMAIN + fileName;
	}


}























