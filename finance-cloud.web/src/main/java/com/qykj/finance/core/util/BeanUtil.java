package com.qykj.finance.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qykj.finance.core.persistence.service.QueryService;

import lombok.extern.slf4j.Slf4j;

/**
 * Bean工具类 文件名: BeanUtil.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Slf4j
@Component
public class BeanUtil {

	@Autowired
	QueryService queryService;

	/**
	 * 根据类型获取所有成员变量
	 * 
	 * @param classType
	 *            类的类型
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	public static List<Field> getAllNoStaticFields(Class<?> classType) {
		List<Field> fields = new ArrayList<Field>();
		Field[] fieldArray = classType.getDeclaredFields();
		for (int i = 0; i < fieldArray.length; i++) {
			Field field = fieldArray[i];
			// 过滤静态变量 和final量
			if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
				fields.add(field);
			}
		}
		Class<?> parent = classType.getSuperclass();
		if (parent != Object.class) {
			List<Field> parentFields = getAllNoStaticFields(parent);
			fields.addAll(parentFields);
		}
		return fields;
	}

	/**
	 * 将数据库查询结果转化为指定类型对象
	 * 
	 * @param classType
	 *            对象类型
	 * @param rs
	 *            数据库结果集
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	public <T> T getDbData(Class<T> classType, ResultSet rs) {
		T object = null;
		try {
			object = classType.newInstance();
			List<Field> fields = getAllNoStaticFields(classType);
			for (Field field : fields) {
				String fieldName = field.getName();
				String columnName = fieldName;// 如果数据库为oracle 此处columnName全转为大写形式
				JPAAnnotations jpaAnnotations = getAnnotationColumnName(field);
				boolean fieldfromDB = false;
				if (jpaAnnotations.getColumn() != null) {
					String annotationColumnName = jpaAnnotations.getColumn().name();
					if (StringUtils.isNoneBlank(annotationColumnName))
						columnName = annotationColumnName;
				} else if (jpaAnnotations.getOneToOne() != null || jpaAnnotations.getManyToOne() != null) {
					fieldfromDB = true;
					if (jpaAnnotations.getJoinColumn() != null) {
						String annotationColumnName = jpaAnnotations.getJoinColumn().name();
						if (StringUtils.isNoneBlank(annotationColumnName))
							columnName = annotationColumnName;
					} else {
						columnName = new StringBuilder(fieldName).append(END_ID).toString();
					}
				} else if (jpaAnnotations.getManyToMany() != null || jpaAnnotations.getOneToMany() != null) {
					// 包含这些注解的这种字段不处理
					continue;
				}

				Class<?> filedType = field.getType();
				String methodSetName = getMethodName(SET, fieldName);
				Method method = classType.getMethod(methodSetName, new Class[] { filedType });
				Object value = null;
				try {
					 value = rs.getObject(columnName);// 数据库字段名
				}catch(Exception e) {
					// 找不到对应字段  不处理
					continue;
				}
				if (fieldfromDB)
					if (value != null) {
						value = queryService.selectOneById(filedType, value);
					}
				// 数据库类型转换
				if ((value instanceof BigDecimal)) {
					value = Double.valueOf(((BigDecimal) value).doubleValue());
				} else if (jpaAnnotations.getEnumerated() != null) {
					// 枚举类型转换
					value = BeanUtil.invokeStaticMethod(filedType, ENUM_VALUE_OF, value);
				}
				if (value != null) {
					try {
						 if (jpaAnnotations.getType() != null ) {
							if("yes_no".equals(jpaAnnotations.getType().type())) {
								  value = false;
								 if(value.equals("Y")) {
									 value = true;
								 }
							}
						}
						 
						method.invoke(object, new Object[] { filedType.cast(value) });
					} catch (Exception e) {
						log.error("BeanUtil.getDbData转换实体类字段{}错误{}", fieldName, e);
					}
				}
			}
		} catch (Exception e) {
			log.debug("getDbData:{}", e);
		}

		return object;
	}

	/**
	 * 获得GET\SET方法名
	 * 
	 * @param prefix
	 *            前缀 set get
	 * @param fieldName
	 *            字段名
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	private static String getMethodName(String prefix, String fieldName) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix).append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
		String name = builder.toString();

		return name;
	}

	/**
	 * 转换字段名为get set方法中的名字
	 * 
	 * @param name
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	private static String convertColumnName(String name) {
		StringBuilder builder = new StringBuilder();
		builder.append(name.substring(0, 1).toLowerCase()).append(name.substring(1));
		return builder.toString();
	}

	/**
	 * 获得对象的table注解映射数据库表名
	 * 
	 * @param clazz
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	public static String getAnnotationTableName(Class<?> clazz) {
		boolean isEmpty = clazz.isAnnotationPresent(Table.class);
		if (isEmpty) {
			Annotation[] annotations = clazz.getAnnotations();
			for (Annotation annotation : annotations) {
				if ((annotation instanceof Table)) {
					Table table = (Table) annotation;
					return table.name();
				}
			}
		}
		return "";
	}

	/**
	 * 根据对象生成sql查询语句和查询参数 null值不作为查询条件
	 * 
	 * @param t
	 *            对象
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	public static <T> SqlParam getSqlParam(T t) {
		SqlParam sqlParam = new SqlParam();
		StringBuilder sqlBuilder = new StringBuilder();
		List<String> columns = new ArrayList<String>();
		List<Object> objects = new ArrayList<Object>();
		Class<?> classType = t.getClass();

		String tableName = getAnnotationTableName(classType);
		if (StringUtils.isBlank(tableName)) {
			tableName = classType.getSimpleName().toLowerCase();
		}

		sqlBuilder.append(COMMON_SELECT_SQL).append(tableName);
		List<Field> fields = getAllNoStaticFields(classType);
		if (CollectionUtils.isNotEmpty(fields)) {
			for (Field field : fields) {
				String fieldName = field.getName();
				String columnName = fieldName;

				JPAAnnotations jpaAnnotations = getAnnotationColumnName(field);
				if (jpaAnnotations.getColumn() != null) {
					String annotationColumnName = jpaAnnotations.getColumn().name();
					if (StringUtils.isNoneBlank(annotationColumnName))
						columnName = annotationColumnName;
				}

				Object value = getFieldValue(t, fieldName);

				if (jpaAnnotations.getEnumerated() != null) {
					Enum<?> enumValue = (Enum<?>) value;
					if (jpaAnnotations.getEnumerated().value() == EnumType.STRING) {
						value = enumValue.name();
					} else {
						value = enumValue.ordinal();
					}
				}
				if (null != value) {
					objects.add(value);
					columns.add(columnName);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(columns)) {
			sqlBuilder.append(" where ");
			int lastAnd = columns.size() - 1;
			for (int i = 0; i < columns.size(); i++) {
				sqlBuilder.append(columns.get(i)).append("=? ");
				if (i < lastAnd) {
					sqlBuilder.append("and ");
				}
			}
		}
		sqlParam.setSql(sqlBuilder.toString());
		sqlParam.setParams(objects.toArray());

		return sqlParam;
	}

	/**
	 * 获取某个字段上的JPAAnnotations中的注解值
	 * 
	 * @param field
	 *            字段
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	private static JPAAnnotations getAnnotationColumnName(Field field) {
		JPAAnnotations jpaAnnotations = new JPAAnnotations();
		Annotation[] annotations = field.getDeclaredAnnotations();
		for (Annotation annotation : annotations) {
			String annotationName = annotation.annotationType().getSimpleName();
			annotationName = convertColumnName(annotationName);
			setFieldAnnotationValue(jpaAnnotations, annotationName, annotation);
		}
		return jpaAnnotations;
	}

	/**
	 * 获取对象某个字段的值
	 * 
	 * @param t
	 *            对象
	 * @param fieldName
	 *            字段
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	public static <T> Object getFieldValue(T t, String fieldName) {
		try {
			String methodGetName = getMethodName(GET, fieldName);
			Method method = t.getClass().getMethod(methodGetName);
			Object value = method.invoke(t);
			return value;
		} catch (Exception e) {
			log.error("{}获取变量{}失败", t.getClass(), fieldName);
		}

		return null;
	}

	/**
	 * 调用静态方法
	 * 
	 * @param classType
	 *            类
	 * @param methodName
	 *            静态方法名
	 * @param param
	 *            参数
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	public static <T> Object invokeStaticMethod(Class<?> classType, String methodName, Object... param) {
		try {
			Method method = classType.getMethod(methodName, String.class);
			Object value = method.invoke(null, param);
			return value;
		} catch (Exception e) {
			log.error("{}调用方法{}失败", classType, methodName);
		}

		return null;
	}

	/**
	 * 根据名字设置对象注解类型
	 * 
	 * @param t
	 *            对象
	 * @param fieldName
	 *            字段名
	 * @param value
	 *            注解
	 * @author wenjing
	 * @since V1.0.0
	 */
	public static <T> void setFieldAnnotationValue(T t, String fieldName, Annotation value) {
		try {
			String methodSetName = getMethodName(SET, fieldName);
			Method method = t.getClass().getMethod(methodSetName, value.annotationType());
			if(method!=null)
			method.invoke(t, value);
		} catch (Exception e) {
			// 忽略错误
			// log.debug("{}设置变量{}失败:{}", t.getClass(), fieldName,e);
		}
	}

	/**
	 * 获得select * from TableName from where id = ? 语句
	 * 
	 * @param clazz
	 *            TableName对应的类
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	public static String getSelectOneByIdSql(Class<?> clazz) {
		String tableName = getAnnotationTableName(clazz);
		if (StringUtils.isBlank(tableName)) {
			tableName = clazz.getSimpleName().toLowerCase();
		}
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(COMMON_SELECT_SQL).append(tableName).append(COMMON_WHERE_ID_SQL);
		return sqlBuilder.toString();
	}

	/**
	 * 
	 */
	public static final String COMMON_SELECT_SQL = "select * from ";
	/**
	 * 
	 */
	public static final String COMMON_WHERE_ID_SQL = " where id = ?";
	/**
	 * get
	 */
	public static final String GET = "get";
	/**
	 * set
	 */
	public static final String SET = "set";
	/**
	 * id尾缀
	 */
	public static final String END_ID = "_id";
	/**
	 * 枚举类名字转换类型方法
	 */
	public static final String ENUM_VALUE_OF = "valueOf";
}
