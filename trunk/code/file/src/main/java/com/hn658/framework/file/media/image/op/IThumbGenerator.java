/***************************************************************************************************
 * Copyright (c) 2013. WZITech Information Technology Co., Ltd.(Shanghai). All rights reserved.
 **************************************************************************************************/

package com.hn658.framework.file.media.image.op;

import com.hn658.framework.file.media.image.ThumbGeneratorMode;


/**
 * 模    块：IThumbGenerator
 * 包    名：com.wzitech.chaos.framework.server.common.media.image.op
 * 项目名称：Gaea_Mobile_ServerEnd
 * 作    者：Shawn
 * 创建时间：8/15/13 1:25 AM
 * 描    述：
 * 更新纪录：1. Shawn 创建于 8/15/13 1:25 AM
 */
public interface IThumbGenerator {
    /**
     * 缩略图生成器
     *
     * @param imageFullName 图片路径
     * @param mode          缩略图生成模式
     * @param rootDir       图片存放跟路径
     * @param sizeList      图片size列表
     * @return 生成缩量图路径
     */
    String generateThumb(String imageFullName, ThumbGeneratorMode mode, String rootDir, String sizeList);
}
