package com.qykj.finance.core.license.util;

import java.io.File;
import java.net.URLDecoder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.qykj.finance.core.cache.CacheContainer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LincenseUtil {
	
	// 授权文件名
	public static final String LICENSE_FILE = "license.cyt";
	// 文件头 file: 占位5个字符
	public static final int FILE_HEAD = 5;
	
	public static String getLincenseInfo() {
		try {
			String path = LincenseUtil.class.getResource("/").toString();
			
			path = StringUtils.substringBeforeLast(path, "WEB-INF/");
			StringBuilder pathBuilder = new StringBuilder();
			if (path.startsWith("file")) {
				path = path.substring(FILE_HEAD);
			}
			path = URLDecoder.decode(path, "UTF-8");
			
			log.info("授权文件路径:{}",path);
			String filePath = pathBuilder.append(path).append(LICENSE_FILE).toString();
			byte[] encodedData = FileUtils.readFileToByteArray(new File(filePath));
			// 公钥
			String publicKey = CacheContainer.getPublicKey();
			// 解密
			byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
			String target = new String(decodedData);
			return target;
		} catch (Exception e) {
			log.info("授权文件错误,请检查{}", e);
		}
		
		return "";
	}
}
