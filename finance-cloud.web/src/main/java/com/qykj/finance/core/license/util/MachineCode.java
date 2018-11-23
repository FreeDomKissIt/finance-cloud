package com.qykj.finance.core.license.util;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.qykj.finance.core.util.EncryptUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MachineCode {
	public static final String LINUX_OS = "linux";
	public static final String DEFAULT_CODE= "FINANCE";
	public static String getCode() {
		String[] WindowsCmd = { "wmic", "cpu", "get", "ProcessorId" };
		String[] LinuxCmd = { "dmidecode", "-s", "system-serial-number" };
		String code = DEFAULT_CODE;
		try {
			String osName = System.getProperty("os.name").toLowerCase();
			Process process = null;
			String[] cmd = null;
			if (osName.startsWith(LINUX_OS)) {
				cmd = LinuxCmd;
			} else {
				cmd = WindowsCmd;
			}
			process = Runtime.getRuntime().exec(cmd);
			process.getOutputStream().close();
			code = IOUtils.toString(process.getInputStream(), "UTF-8");
			code = code.replaceAll("\\s", "");
			process.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("MachineCode:{}",code);
		code = EncryptUtil.md5x32Code(code);
		log.info("EncryptUtil MachineCode:{}",code);
		
		return code;
	}
}
