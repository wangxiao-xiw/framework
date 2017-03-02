/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *
 *	模	块：		IImageManager
 *	包	名：		com.wzitech.chaos.gaea.jgm.notificationmgmt.handlers
 *	项目名称：	jgm-notificationmgmt
 *	作	者：		Shawn
 *	创建时间：	2012-7-19
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-7-19 下午11:00:40
 *
 ************************************************************************************/
package com.hn658.framework.file.media.image;

/**
 * @author Shawn
 */
public interface IImageManager {

    /**
     * 缩略图生成器
     * 为指定路径图片生成缩略图
     *
     * @param imageLocalPath 图片路径
     * @param mode           缩略图生成模式
     * @param rootDir        图片存放跟路径
     * @param sizeList       图片size列表，如"200,300"，"200*300,320*480"
     * @return 生成缩略图的路径
     */
    String generateThumb(String imageLocalPath, ThumbGeneratorMode mode, String rootDir, String sizeList);

    /**
     * 剪裁图片
     *
     * @param imageLocalPath 图片路径
     * @param cropStartX     剪裁点X点坐标
     * @param cropStartY     剪裁起始位置Y点坐标
     * @param cropWidth      剪裁后图片宽度
     * @param cropHeight     剪裁后图片宽度
     * @return 图片剪裁后路径
     */
    String crop(String imageLocalPath, int cropStartX, int cropStartY, int cropWidth, int cropHeight);
}
