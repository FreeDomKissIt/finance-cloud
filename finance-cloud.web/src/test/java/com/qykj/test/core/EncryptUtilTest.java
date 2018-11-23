package com.qykj.test.core;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import com.qykj.finance.core.util.BeanUtil;
import com.qykj.finance.core.util.EncryptUtil;
import com.qykj.finance.sys.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EncryptUtilTest {
	public static Map<String, String> map = new HashMap<String, String>();

	/**
	 * 利用java原生的摘要实现SHA256加密
	 * 
	 * @param str
	 *            加密后的报文
	 * @return
	 */
	public static String getSHA256StrJava(String str) {
		MessageDigest messageDigest;
		String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes("UTF-8"));
			encodeStr = byte2Hex(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeStr;
	}

	/**
	 * 将byte转为16进制
	 * 
	 * @param bytes
	 * @return
	 */
	private static String byte2Hex(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		String temp = null;
		for (int i = 0; i < bytes.length; i++) {
			temp = Integer.toHexString(bytes[i] & 0xFF);
			if (temp.length() == 1) {
				// 1得到一位的进行补0操作
				stringBuffer.append("0");
			}
			stringBuffer.append(temp);
		}
		return stringBuffer.toString();
	}

	public static String getSHA256Str(String str) {
		MessageDigest messageDigest;
		String encdeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
			encdeStr = Hex.encodeHexString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encdeStr;
	}

	/**
	 * 盐密码原理
	 * 
	 * @since 1.0.0
	 */
	//@Test
	public void EncryptUtil() {
		String ypwd = "hik12345+";
		String password = getSHA256StrJava(ypwd);
		log.info("后台收到密码:{}", password);
		String salt = EncryptUtil.createSalt();
		salt = "5d9ebf02fb7848b08eee5e6f94968354";

		log.info("salt:{}", salt);
		log.info("数据库存储密码:{}", EncryptUtil.encrypt(password, salt));
		log.info("数据库存储密码:{}", EncryptUtil.encrypt(password, salt));
		User user = new User();
		user.setName("1");

		System.out.println(BeanUtil.getSqlParam(user));
	}
	
	@Test
	public void EncryptUtil2() {
		String ypwd = "hik12345+";
		String password = md5x32Code(ypwd);
		log.info("后台收到密码:{}", password);
		String salt = EncryptUtil.createSalt();
		salt = "5d9ebf02fb7848b08eee5e6f94968354";

		log.info("salt:{}", salt);
		log.info("数据库存储密码:{}", EncryptUtil.encrypt(password, salt));
		User user = new User();
		user.setName("1");

		System.out.println(BeanUtil.getSqlParam(user));
	}

	public static String md5x32Code(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			try {
				md.update(password.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			byte[] b = md.digest();
			StringBuilder builder = new StringBuilder("");
			for (int offset = 0; offset < b.length; offset++) {
				int i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					builder.append("0");
				}
				builder.append(Integer.toHexString(i));
			}
			password = builder.toString();
		} catch (NoSuchAlgorithmException e) {
			log.info("加密失败{}", e);
		}
		return password;
	}
}
