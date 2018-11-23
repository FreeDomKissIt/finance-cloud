package com.qykj.finance.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 加密工具 文件名: EncryptUtil.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Slf4j
public class EncryptUtil {
	// 默认密码 hik12345+  sha256
	//public final static String DEFAULT_PASSWORD = "8f0a06ebd27f187d9f98ffd78cbe56ed8b340f3f1948d1587a7511875c517c1c";
	
	// 默认密码 888888  sha256
	public final static String DEFAULT_PASSWORD = "92925488b28ab12584ac8fcaa8a27a0f497b2c62940c8f4fbc8ef19ebc87c43e";
	// sha256加密
	public final static String SHA_256 = "SHA-256";
	// MD5加密
	public final static String MD5 = "MD5";
	// 修正0
	public final static String FIX_ZERO = "0";
	// 默认编码
	public final static String DEFAULT_CHARSET = "UTF-8";
	// uuid分隔符
	public final static String UUID_SPLIT = "-";

	/**
	 * 创建密码盐
	 * 
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	public static String createSalt() {
		String salt = UUID.randomUUID().toString().replaceAll(UUID_SPLIT, "");
		return salt;
	}

	public static void main(String[] args) {
		System.out.println(getSHA256Str("888888"));;
	}
	
	/**
	 * 根据盐加密密码
	 * 
	 * @param password
	 *            密码
	 * @param salt
	 *            盐
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	public static String encrypt(String password, String salt) {
		StringBuilder builder = new StringBuilder(salt);
		builder.append(password);
		return getSHA256Str(builder.toString());
	}

	/**
	 * SHA256加密
	 * 
	 * @param str
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	public static String getSHA256Str(String str) {
		MessageDigest messageDigest;
		String encdeStr = "";
		try {
			messageDigest = MessageDigest.getInstance(SHA_256);
			byte[] hash = messageDigest.digest(str.getBytes(DEFAULT_CHARSET));
			encdeStr = Hex.encodeHexString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encdeStr;
	}

	/**
	 * 32位MD5加密
	 * 
	 * @param password
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	public static String md5x32Code(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance(MD5);
			md.update(password.getBytes());
			byte[] b = md.digest();
			StringBuilder builder = new StringBuilder("");
			for (int offset = 0; offset < b.length; offset++) {
				int i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					builder.append(FIX_ZERO);
				}
				builder.append(Integer.toHexString(i));
			}
			password = builder.toString();
		} catch (NoSuchAlgorithmException e) {
			log.info("加密失败{}", e);
		}
		return password;
	}

	/**
	 * 验证密码是否一致
	 * @param oldDBPassword 数据库密码
	 * @param newPassword   新输入密码
	 * @param salt
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static boolean validate(String oldDBPassword, String newPassword, String salt) {
		if (StringUtils.isNotBlank(oldDBPassword) && StringUtils.isNotBlank(newPassword)
				&& StringUtils.isNotBlank(salt)) {
			String newSaltPassword = EncryptUtil.encrypt(newPassword, salt);
			return oldDBPassword.equals(newSaltPassword);
		}
		return false;
	}
}
