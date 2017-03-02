/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		GenericsUtils
 *	包	名：		com.wzitech.chaos.framework.common.utils
 *	项目名称：	chaos-common 
 *	作	者：		Shawn
 *	创建时间：	2012-4-14
 *	描	述：		Generics Util类
 *	更新纪录：	1. Shawn 创建于 2012-4-14 上午2:00:51
 * 				
 ************************************************************************************/
package com.hn658.framework.shared.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generics Util类<br/>
 * 通过反射,获得定义Class时声明的父类的范型参数的类型
 * @author Shawn
 *
 */
public class GenericsUtils {

	private static final Logger logger = LoggerFactory.getLogger(GenericsUtils.class);
	
	/**
	 * 通过反射获得父类泛型第一个参数类型
	 * 如public ParameterizeType exnteds SuperParameterizeType<CustomType>，则返回CustomType
	 * @param clazz 需要反射的类
	 * @return 第一个泛型参数类型，如无则返回当前Class类型
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}
	
	/**
	 * 根据索引通过反射获得父类泛型参数类型
	 * @param clazz 需要反射的类
	 * @param index 第一个泛型参数类型，如无则返回当前Class类型
	 * @return 制定的泛型参数类型，如无则返回当前Class类型
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			logger.warn("{}的父类非泛型类", clazz.getSimpleName());
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			logger.warn("索引: {} 超出了  {} 的泛型参数长度:{}", new Object[]{index, clazz.getSimpleName(), params.length});
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn("{} 在父类的泛型参数中不是真正的Class类型", clazz.getSimpleName());
			return Object.class;
		}
		return (Class) params[index];
	}
}
