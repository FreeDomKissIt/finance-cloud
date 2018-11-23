package com.qykj.finance.shiro;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.qykj.finance.core.cache.CacheContainer;
import com.qykj.finance.core.exception.LicenseExpireException;
import com.qykj.finance.core.util.DateFormatUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Credentials {
	
	public static final String SERIAL = "serial";
	public static final String EXPIRE = "expire";
	public static final String TRIAL = "trial";
	
	public static String validate(String license) {
		Map<String, String> params = convertLicense(license);
		String serial = params.get(SERIAL);
		String expire = params.get(EXPIRE);
		String trial = params.get(TRIAL);
		if (!CacheContainer.getMachineCode().equals(serial)) {
			log.info("机器码不匹配");
			throw new LicenseExpireException(LicenseExpireException.LICENSE_UABLE);
		}
		if (System.currentTimeMillis() > Long.valueOf(expire)) {
			log.info("授权超期");
			throw new LicenseExpireException(LicenseExpireException.LICENSE_EXPIRE);
		}
		if (trial != null) {
			// 体验版
			log.info("体验版");
			return "trial";
		}
		
		return "";
	}
	
	public static String validateInfo(String license) {
		Map<String, String> params = convertLicense(license);
		String serial = params.get(SERIAL);
		String expire = params.get(EXPIRE);
		String trial = params.get(TRIAL);
		long time = 0;
		
		if(expire != null) {
			 time = Long.valueOf(expire);
		}else {
			return "授权情况： 证书不存在,请检查";
		}
		
		if (!CacheContainer.getMachineCode().equals(serial)) {
			log.info("机器码不匹配");
			return "授权情况： 机器码与证书不匹配";
		}
		
		if (System.currentTimeMillis() > time) {
			log.info("授权超期");
			return "授权情况：证书已过期";
		}
		
		String date = DateFormatUtils.format(time, DateFormatUtils.DATE_FORMAT_MIN);
		if (trial != null) {
			// 体验版
			log.info("体验版");
			StringBuilder message = new StringBuilder();
			message.append("授权情况：已激活体验版,体验有效截止时间至  ").append(date);
			return message.toString();
		}
		StringBuilder message = new StringBuilder();
		message.append("授权情况：已激活正式版,有效截止时间至 ").append(date);
		
		return message.toString();
	}
	
	public static Map<String, String> convertLicense(String license) {
		Map<String, String> map = new HashMap<>();
		if (StringUtils.isNoneBlank(license)) {
			String[] infos = license.split(";");
			if (infos != null && infos.length > 0) {
				for (String info : infos) {
					String[] keyValue = info.split("=");
					if (keyValue != null && keyValue.length >= 2)
						map.put(keyValue[0], keyValue[1]);
				}
			}
		}
		return map;
	}
	
}
