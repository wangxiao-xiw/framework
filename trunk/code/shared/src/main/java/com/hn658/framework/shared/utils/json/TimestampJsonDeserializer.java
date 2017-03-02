/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		TimestampJsonDeserializer
 *	包	名：		com.wzitech.chaos.framework.server.common.utils.json
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-14
 *	描	述：		Timestamp Json Deserializer 使用时注意jackson版本要一致
 *	更新纪录：	1. Shawn 创建于 2012-7-14 上午3:00:04
 * 				
 ************************************************************************************/
package com.hn658.framework.shared.utils.json;

import java.io.IOException;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Timestamp Json Deserializer 使用时注意jackson版本要一致
 * @author dev
 *
 */
public class TimestampJsonDeserializer extends JsonDeserializer<Timestamp> {

	private static final Logger logger = LoggerFactory.getLogger(TimestampJsonDeserializer.class);
	
	@Override
	public Timestamp deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		String parserString = parser.getText();
		
		// 没有对应要解析的timestamp string value时
		if (StringUtils.isBlank(parserString) 
				|| parserString.compareToIgnoreCase("null") == 0 ) {
			return new Timestamp(0);
		} else {
			Long timestampLong = 0L;
			
			try {
				timestampLong = Long.parseLong(parserString);
			} catch (Exception e) {
				 logger.error(String.format("TimestampJsonDeserializer转换对应值%s时出错%s", 
						 parserString, e));
			}
			
			return new Timestamp(timestampLong);
		}
	}

}
