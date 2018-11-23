package com.qykj.finance.web.action.demo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.qykj.finance.core.persistence.service.QueryService;
import com.qykj.finance.core.util.ActionPageResponse;
import com.qykj.finance.core.util.ActionResponse;
import com.qykj.finance.core.util.WebPage;
import com.qykj.finance.demo.form.DemoCreateForm;
import com.qykj.finance.demo.model.Demo;
import com.qykj.finance.demo.repository.DemoRepository;
import com.qykj.finance.demo.service.DemoService;
import com.qykj.finance.sys.model.User;
import com.qykj.finance.sys.service.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 *  案例
 *  文件名: DemoController.java <br/>
 *  Copyright (c) 2017-2018 HangZhou qykj System Technology Co., Ltd. All Right Reserved. <br/>
 *  文件编号:  <br/>
 *  创 建 人: wenjing <br/>
 *  日 期: 2018年1月31日 <br/>
 *  修 改 人: wenjing <br/>
 *  日 期: 2018年1月31日 <br/>
 *  描 述:  <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Controller
@RequestMapping("view")
@Slf4j
public class DemoController {

	@Autowired
	DemoRepository demoRepository;
	@Autowired
	QueryService queryService;
	@Autowired
	DemoService pageDemoServcie;

	@Autowired
	UserService userService;

	/**
	 * hello页
	 * @param model
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@GetMapping("hello")
	public String getFarm(Model model) {
		return "hello";
	}

	/**
	 * 案例页面
	 * @param model
	 * @param pageNum
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@GetMapping("demo")
	public String test(Model model,Integer pageNum) {
		if(pageNum==null) {
			pageNum = 1;
		}
		Demo demo = demoRepository.findOne(1);
		model.addAttribute("demo", demo);
		// 此处只是案例  不作为规范案例   sql语句不允许出现在action层
		Page<Map<String, Object>> page = queryService.selectPage("select * from t_demo", pageNum, 5, true, new Object[0]);
		model.addAttribute("page", page);

		return "demo";
	}
	
	/**
	 * 案例页面
	 * @param model
	 * @param pageNum
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@GetMapping("demo2")
	public String test2(Model model,Integer pageNum) {
		if(pageNum==null) {
			pageNum = 1;
		}
		Demo demo = demoRepository.findOne(1);
		model.addAttribute("demo", demo);
		// 此处只是案例  不作为规范案例   sql语句不允许出现在action层
		Page<Map<String, Object>> page = queryService.selectPage("select * from t_demo", pageNum, 5, true, new Object[0]);
		model.addAttribute("page", page);

		return "test/demo";
	}

	/**
	 * 案例新增页功能
	 * @param demoCreateForm
	 * @param model
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("addDemo")
	@ApiOperation(value = "增加demo对象", notes = "增加demo对象")
	@ResponseBody
	public ResponseEntity<ActionResponse<Demo>> addDemo(@Valid DemoCreateForm demoCreateForm, Model model) {
		Assert.isTrue(demoCreateForm.getName() != null, "异常");
		Demo demo = new Demo();
		BeanUtils.copyProperties(demoCreateForm, demo);
		demo.setUpdateDate(new Date());
		demoRepository.save(demo);
		model.addAttribute("demo", demo);
		int pageNo = 1;// 页码
		int pageSize = 5;// 每页大小
		Object[] params = new Object[0];// 参数
		boolean needCount = true;// 是否获取总页数
		// 此处只是案例  不作为规范案例   sql语句不允许出现在action层
		Page<Map<String, Object>> page = queryService.selectPage("select * from t_demo", pageNo, pageSize, needCount, params);
		model.addAttribute("page", page);

		log.info("保存成功demoId:{}", demo.getId());
		return ActionResponse.ok(demo);
	}
	
	/**
	 * 案例新增页功能
	 * API使用时 @Valid需要注释
	 * @param demoCreateForm
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("addDemo2")
	@ApiOperation(value = "增加demo对象", notes = "增加demo对象")
	@ResponseBody
	public ResponseEntity<ActionResponse<Demo>> addDemo2(/*@Valid*/ DemoCreateForm demoCreateForm) {
		Demo demo = new Demo();
		BeanUtils.copyProperties(demoCreateForm, demo);
		demo.setUpdateDate(new Date());
		demoRepository.save(demo);

		log.info("保存成功demoId:{}", demo.getId());
		return ActionResponse.ok(demo);
	}
	

	/**
	 * 获得案例
	 * @param model
	 * @param demoId
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@ApiOperation(value = "获得demo对象", notes = "根据demo的id获取demo对象")
	@GetMapping("demo/{demoId}")
	@ResponseBody
	public Demo getArea(Model model, @PathVariable("demoId") Integer demoId) {
		Demo demo = demoRepository.findOne(demoId);

		return demo;
	}

	/**
	 * 获得用户分页
	 * @param model
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@ApiOperation(value = "获得demo对象", notes = "根据demo的id获取demo对象")
	@PostMapping("getUserPage")
	@ResponseBody
	public ActionResponse<WebPage<User>> getUserPage(Model model, Integer pageNo, Integer pageSize, User user) {
		ActionResponse<WebPage<User>> actionResponse = new ActionResponse<>();
		Page<User> servers = userService.selectPage(pageNo, pageSize, user);

		WebPage<User> webPage = new WebPage<User>(servers);
		actionResponse.setData(webPage);
		return actionResponse;
	}
	
	@ApiOperation(value = "获得demo对象", notes = "根据demo的id获取demo对象")
	@PostMapping("getUserPage2")
	@ResponseBody
	public ResponseEntity<ActionPageResponse<User>> getUserPage2(Model model, Integer pageNo, Integer pageSize, User user) {
		pageNo = 1;
		pageSize = 5;
		Page<User> servers = userService.selectPage(pageNo, pageSize, user);
		
		List<User> users = servers.getResult();
		log.info(JSONObject.toJSONString(userService.obtain("admin")));;
		
		return ActionPageResponse.create(servers,user);
	}


}
