/************************************************************************************
 *  Copyright 2014 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		StreamIOHelper
 *	包	名：		com.wzitech.gamegold.common.utils
 *	项目名称：	gamegold-common
 *	作	者：		SunChengfei
 *	创建时间：	2014-2-21
 *	描	述：		
 *	更新纪录：	1. SunChengfei 创建于 2014-2-21 下午1:02:15
 * 				
 ************************************************************************************/
package com.hn658.${projectName}.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hn658.framework.logging.Logger;
import com.hn658.framework.logging.LoggerFactory;
import com.hn658.framework.logging.enums.LogCategory;
import com.hn658.framework.shared.utils.JsonMapper;
import com.hn658.${projectName}.common.entity.Sort;

/**
 * @author SunChengfei
 * 
 */
public class JsonUtil {
	
	private static JsonMapper mapper = JsonMapper.nonEmptyMapper();

	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	public static List<Sort> sortList(String sort) {
		List<Sort> sortList = new ArrayList<Sort>();
		try {
			if(sort==null){
				return sortList;
			}
			Sort[] sortArray = mapper.fromJson(sort, Sort[].class);
			return Arrays.asList(sortArray);
		} catch (Exception e) {
			logger.debug(LogCategory.SYSTEM, "排序JSON串转换异常", e);
			return sortList;
		}
	}
}
