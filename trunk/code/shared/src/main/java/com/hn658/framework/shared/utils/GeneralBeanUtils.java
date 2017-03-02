/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		GeneralBeanUtils
 *	包	名：		com.wzitech.chaos.framework.common.utils
 *	项目名称：	chaos-common 
 *	作	者：		Shawn
 *	创建时间：	2012-4-14
 *	描	述：		泛型类帮助工具
 *	更新纪录：	1. Shawn 创建于 2012-4-14 下午2:13:27
 * 				
 ************************************************************************************/
package com.hn658.framework.shared.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 泛型类帮助工具
 * @author Shawn
 *
 */
public class GeneralBeanUtils extends BeanUtils {

	private static final Logger logger = LoggerFactory.getLogger(GeneralBeanUtils.class);
	
	/**
	 * 获取对象的指定名称Field
	 * 获取方式采用遍历该类型的所有父类直至获取到对应Field信息.
	 * @param object 要获取Field信息的对象
	 * @param propertyName 指定属性名称
	 * @return 对应属性名称的Field
	 * @throws NoSuchFieldException 如遍历完成后仍未找到对应Field信息则抛出异常
	 */
	public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
        if(null == object){
        	logger.warn("object参数为空.");
        	return null;
        }
        
        if(StringUtils.isEmpty(propertyName)){
        	logger.warn("propertyName参数为空.");
        	return null;
        }
        return getDeclaredField(object.getClass(), propertyName);
    }
	
	/**
	 * 获取类的指定名称Field
	 * 获取方式采用遍历该类型的所有父类直至获取到对应Field信息.
	 * @param clazz 要获取Field信息的类型
	 * @param propertyName 指定属性名称
	 * @return 对应属性名称的Field
	 * @throws NoSuchFieldException 如遍历完成后仍未找到对应Field信息则抛出异常
	 */
	public static Field getDeclaredField(Class<?> clazz, String propertyName) throws NoSuchFieldException {
		if(null == clazz){
        	logger.warn("clazz参数为空.");
        	return null;
        }
        
        if(StringUtils.isEmpty(propertyName)){
        	logger.warn("propertyName参数为空.");
        	return null;
        }
	        
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(propertyName);
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上递归
            	continue;
            }
        }
        
        logger.error(String.format("没有找到对应的field：%s.%s", clazz.getName(), propertyName));
        throw new NoSuchFieldException(String.format("没有找到对应的field：%s.%s", clazz.getName(), propertyName));
    }
	
	/**
	 * 强制获取对象变量值
	 * 获取时忽略private,protected修饰符的限制.
	 * @param object 要获取属性信息的对象
	 * @param propertyName 指定属性名称
	 * @return 指定属性对应的变量值
	 * @throws NoSuchFieldException 未找到对应Field信息时抛出异常
	 */
	public static Object forceGetProperty(Object object, String propertyName) throws NoSuchFieldException {
		if(null == object){
        	logger.warn("object参数为空.");
        	return null;
        }
        
        if(StringUtils.isEmpty(propertyName)){
        	logger.warn("propertyName参数为空.");
        	return null;
        }
        Field field = getDeclaredField(object, propertyName);
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            // Error wont' happen.
        	logger.warn("发生异常：", e);
        }
        field.setAccessible(accessible);
        return result;
    }
	
	/**
	 * 强制设置对象变量值
	 * 设置时忽略private,protected修饰符的限制.
	 * @param object 要设置属性信息的对象
	 * @param propertyName 指定属性名称
	 * @param newValue 设置的属性值
	 * @throws NoSuchFieldException 未找到对应Field信息时抛出异常
	 */
	public static void forceSetProperty(Object object, String propertyName, Object newValue) throws NoSuchFieldException {
		if(null == object){
        	logger.warn("object参数为空.");
        	return;
        }
        
        if(StringUtils.isEmpty(propertyName)){
        	logger.warn("propertyName参数为空.");
        	return;
        }
        Field field = getDeclaredField(object, propertyName);
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        try {
            field.set(object, newValue);
        } catch (IllegalAccessException e) {
            // Error won't happen
        	logger.warn("发生异常：", e);
        }
        field.setAccessible(accessible);
    }
	
	/**
	 * 强制调用对象函数
	 * 调用时忽略private,protected修饰符的限制.
	 * @param object 要调用方法的对象
	 * @param methodName 调用方法名
	 * @param params 方法参数
	 * @return 返回方法调用结果
	 * @throws NoSuchMethodException 未找到对应Method信息时抛出异常
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object... params)
            throws NoSuchMethodException {
		if(null == object){
        	logger.warn("object参数为空.");
        	return null;
        }
        
        if(StringUtils.isEmpty(methodName)){
        	logger.warn("methodName参数为空.");
        	return null;
        }
        Class<?>[] types = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            types[i] = params[i].getClass();
        }
        Class<?> clazz = object.getClass();
        Method method = null;
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                method = superClass.getDeclaredMethod(methodName, types);
                break;
            } catch (NoSuchMethodException e) {
                // 方法不在当前类定义,继续向上转型
            	continue;
            }
        }
        
        if (method == null){
            throw new NoSuchMethodException(String.format("找不到对应方法：%s.%s",  clazz.getSimpleName(), methodName));
        }
        
        boolean accessible = method.isAccessible();
        method.setAccessible(true);
        Object result = null;
        
        try {
            result = method.invoke(object, params);
        } catch (Exception e) {
        	logger.error("调用方法时出现异常.", e);
        }
        
        method.setAccessible(accessible);
        return result;
    }

	/**
	 * 按Field的类型取得Field列表
	 * @param object 要获取Field信息的对象
	 * @param type 要获取的Field类型
	 * @return 返回对应类型的Field列表
	 */
    public static List<Field> getFieldsByType(Object object, Class<?> type) {
        List<Field> list = new ArrayList<Field>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().isAssignableFrom(type)) {
                list.add(field);
            }
        }
        return list;
    }
    
    /**
     * 按FieldName获取Field类型
     * @param type 要获取信息的类型
     * @param name Field名称
     * @return 返回对应Field类型
     * @throws NoSuchFieldException 如未找到对应Field信息则抛出异常
     */
    public static Class<?> getPropertyType(Class<?> type, String name) throws NoSuchFieldException {
        return getDeclaredField(type, name).getType();
    }
    /**
     * 获得field的getter函数名称.
     * @param type 要获取信息的类型
     * @param fieldName Field名称
     * @return 返回对应getter函数名称
     */
    @SuppressWarnings("rawtypes")
	public static String getGetterName(Class type, String fieldName) {
    	if(null == type){
        	logger.warn("type参数为空.");
        	return null;
        }
        
        if(StringUtils.isEmpty(fieldName)){
        	logger.warn("fieldName参数为空.");
        	return null;
        }
        if (type.getName().equals("boolean")) {
            return "is" + StringUtils.capitalize(fieldName);
        } else {
            return "get" + StringUtils.capitalize(fieldName);
        }
    }

    /**
     * 根据Field名称获取getter函数
     * @param type 要获取信息的类型
     * @param fieldName Field名称
     * @return 返回对应getter函数，如果找不到该方法,返回null.
     */
    public static Method getGetterMethod(Class<?> type, String fieldName) {
        try {
            return type.getMethod(getGetterName(type, fieldName));
        } catch (NoSuchMethodException e) {
            logger.error(String.format("找不到%s.%s对应的getter方法信息", type.getSimpleName(), fieldName));
        }
        return null;
    }
}
