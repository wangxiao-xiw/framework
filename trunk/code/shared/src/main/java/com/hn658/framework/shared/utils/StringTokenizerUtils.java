/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		StringTokenizerUtils
 *	包	名：		com.wzitech.chaos.framework.server.common.utils
 *	项目名称：	chaos-common 
 *	作	者：		Shawn
 *	创建时间：	2012-4-20
 *	描	述：		将字符串按多个分隔符进行切分
 *	更新纪录：	1. Shawn 创建于 2012-4-20 上午2:27:24
 * 				
 ************************************************************************************/
package com.hn658.framework.shared.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 将字符串按多个分隔符进行切分
 * @author Shawn
 *
 */
public class StringTokenizerUtils {
	
	/**
	 * 将str将多个分隔符进行切分，
     * 
     * 示例：StringTokenizerUtils.split("1,2;3 4"," ,;");
     * 返回: ["1","2","3","4"]
	 * @param str 要分割的字符串
	 * @param seperators 分隔符
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] split(String str,String seperators) {
		StringTokenizer tokenlizer = new StringTokenizer(str,seperators);
		List result = new ArrayList();
		
		while(tokenlizer.hasMoreElements()) {
			Object s = tokenlizer.nextElement();
			result.add(s);
		}
		return (String[])result.toArray(new String[result.size()]);
	}
}
