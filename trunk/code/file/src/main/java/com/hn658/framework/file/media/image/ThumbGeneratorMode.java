/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *
 *	模	块：		ThumbGeneratorMode
 *	包	名：		com.wzitech.chaos.framework.server.common.media
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-9-7
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-9-7 上午1:28:04
 *
 ************************************************************************************/
package com.hn658.framework.file.media.image;

/**
 * 缩略图生成模式
 *
 * @author Shawn
 */
public enum ThumbGeneratorMode {
    /**
     * 按宽度比例生成缩略图
     */
    ScaleByWidth,
    /**
     * 按高度比例生成缩略图
     */
    ScaleByHeight,
    /**
     * 按宽高比例生成缩略图
     */
    ScaleByWidthAndHeight;
}
