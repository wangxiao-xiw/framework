/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		ErrorCodes
 *	包	名：		com.wzitech.chaos.framework.common.enums
 *	项目名称：	chaos-common 
 *	作	者：		Shawn
 *	创建时间：	2012-4-13
 *	描	述：		错误码枚举类
 *	更新纪录：	1. Shawn 创建于 2012-4-13 下午4:56:24
 * 				
 ************************************************************************************/
package com.hn658.framework.file.enums;

import org.springframework.http.MediaType;


/**
 * 图片类型枚举类
 * @author ztjie
 *
 */
public enum FileType {
	
	BMP("bmp", MediaType.IMAGE_JPEG_VALUE),
    JPG("jpg", MediaType.IMAGE_JPEG_VALUE),
    JPEG("jpeg", MediaType.IMAGE_JPEG_VALUE),
    PNG("png", MediaType.IMAGE_PNG_VALUE),
    GIF("gif", MediaType.IMAGE_GIF_VALUE),
    SWF("swf", 	MediaType.APPLICATION_OCTET_STREAM_VALUE),
    TIFF("tiff", MediaType.APPLICATION_OCTET_STREAM_VALUE),
    ZIP("zip", MediaType.APPLICATION_OCTET_STREAM_VALUE),
    
    
    P12("p12", MediaType.APPLICATION_OCTET_STREAM_VALUE);
	
	private String type;
	
	private String mediaType;
	
	public String getType() {
		return type;
	}

	public String getMediaType() {
		return mediaType;
	}

	FileType(String type, String mediaType) {
		this.type = type;
		this.mediaType = mediaType;
	}
}
