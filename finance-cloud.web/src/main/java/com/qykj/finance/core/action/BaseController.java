package com.qykj.finance.core.action;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.MappedSuperclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qykj.finance.core.persistence.service.BaseQueryService;

import lombok.extern.slf4j.Slf4j;

@MappedSuperclass
@Slf4j
public abstract class BaseController<T> {
	public static final String SPLIT = "/";// 路径分隔符
	public static final String BASE_PATH = "modules";// 基础路径
	public static final String DIALOG = "dialog";// 对话框页面路径
	public static final String ADD_PAGE = "add";// 添加页
	public static final String EDIT_PAGE = "edit";// 编辑页
	public static final String VIEW_PAGE = "view";// 查看页
	public String modelPath;// 模型路径
	public String modelManagerPage;// 模型管理页路径
	public String modelAddPage;// 模型添加页路径
	public String modelEditPage;// 模型编辑页路径
	public String modelViewPage;// 模型查看页路径
	protected Class<T> entityClass;
	@Autowired(required = false)
	protected BaseQueryService<T> baseQueryService;

	/**
	 * 管理页
	 * @param model
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@RequestMapping
	public String manager(Model model) {
		log.info(modelManagerPage);
		return modelManagerPage;
	}
	
	/**
	 * 管理页
	 * @param model
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@RequestMapping
	public String add(Model model) {
		log.info(modelAddPage);
		return modelAddPage;
	}
	
	/**
	 * 管理页
	 * @param model
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@RequestMapping
	public String edit(Model model,Integer id) {
		model.addAttribute(getModel(), baseQueryService.findOne(id));
		log.info(modelEditPage);
		return modelEditPage;
	}
	
	/**
	 * 查看页
	 * @param model
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@RequestMapping
	public String view(Model model,Integer id) {
		model.addAttribute(getModel(), baseQueryService.findOne(id));
		log.info(modelViewPage);
		return modelViewPage;
	}
	
	
	
	public BaseController() {
		this.entityClass = getEntityClass(getClass().getGenericSuperclass());
		if (this.entityClass == null) { // 尝试2级
			Class<?> parent = getClass().getSuperclass();
			if (parent != null) {
				this.entityClass = getEntityClass(parent);
			}
		}
		// 模型页面位置 modules/模块名称/实体名/ eg modules/sysConfig/user/
		String[] models = { BASE_PATH, SPLIT, getModule(), SPLIT, getModel(), SPLIT };
		modelPath = builderPath(models);
		// 模型管理页
		String[] modelManagerPages = { modelPath, getModel() };
		modelManagerPage = builderPath(modelManagerPages);
		modelAddPage = getDialogPage(ADD_PAGE);
		modelEditPage = getDialogPage(EDIT_PAGE);
		modelViewPage = getDialogPage(VIEW_PAGE);
	}

	public String getDialogPage(String dialogPage) {
		String[] modelDialogPages = { modelPath, DIALOG, SPLIT, dialogPage };
		return builderPath(modelDialogPages);
	}

	public static String builderPath(String[] strings) {
		StringBuilder builder = new StringBuilder();
		for (String string : strings) {
			builder.append(string);
		}
		return builder.toString();
	}

	public abstract String getModule();

	public String getModel() {
		return this.entityClass.getSimpleName().toLowerCase();
	}

	@SuppressWarnings("unchecked")
	private Class<T> getEntityClass(Type clazz) {
		try {
			Type[] types = ((ParameterizedType) clazz).getActualTypeArguments();
			if (types.length > 0) {
				return (Class<T>) types[0];
			}
		} catch (Exception ex) {
			// ignore
		}
		return null;
	}

}
