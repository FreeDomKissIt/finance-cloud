package com.qykj.finance.config;

import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qykj.finance.core.exception.JSONDataException;
import com.qykj.finance.core.exception.PageException;
import com.qykj.finance.core.util.ActionResponse;
import com.qykj.finance.core.util.exception.ExpectedException;

import lombok.extern.slf4j.Slf4j;

/**
 * 控制层全局异常统一处理 文件名: GlobalExceptionHandler.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@ControllerAdvice(basePackages = "com.qykj.finance.web.action")
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * 表单验证时返回的错误拦截
	 * @param request
	 * @param exception
	 * @return
	 * @throws Exception
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@ExceptionHandler(value = { BindException.class })
	@ResponseBody
	public ResponseEntity<ActionResponse<Set<ValidationError>>> BindException(HttpServletRequest request,
			BindException exception) throws Exception {
		// 解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
		Set<ValidationError> errors = exception
				.getBindingResult().getFieldErrors().stream().map(f -> new ValidationError()
						.setMessage(f.getDefaultMessage()).setName(f.getField()).setValue(f.getRejectedValue()))
				.collect(Collectors.toSet());
		return ActionResponse.fail(400, "所提交信息错误", errors);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	@ResponseBody
	public ResponseEntity<ActionResponse<Object>> IllegalArgumentException(IllegalArgumentException exception) {
		return ActionResponse.fail(400, exception.getMessage());
	}

	/**
	 * 控制层返回json数据情况的错误拦截
	 * @param exception
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@ExceptionHandler(value = { JSONDataException.class })
	@ResponseBody
	public ResponseEntity<ActionResponse<Object>> OtherException(JSONDataException exception) {
		log.error("{}", exception);
		return ActionResponse.fail(exception.getErrorCode(), exception.getMessage());
	}
	
	/**
	 * 控制层返回页面情况的错误拦截
	 * @param model
	 * @param exception
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@ExceptionHandler(value = { PageException.class })
	public String pageException(Model model, PageException exception) {
		model.addAttribute("message", exception.getMessage());
		return "error/error";
	}

	/**
	 * 其他情况错误拦截
	 * @param model
	 * @param exception
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@ExceptionHandler(value = { Exception.class })
	public String pageException(Model model, Exception exception) {
		model.addAttribute("message", exception.getMessage());
		return "error/error";
	}

	@ExceptionHandler(value = { ExpectedException.class })
	@ResponseBody
	public ResponseEntity<ActionResponse<Object>> ExpectedException(ExpectedException exception) {
		return ActionResponse.fail(400, exception.getMessage());
	}
}