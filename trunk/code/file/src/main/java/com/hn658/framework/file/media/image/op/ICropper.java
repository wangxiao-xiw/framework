/***************************************************************************************************
 * Copyright (c) 2013. WZITech Information Technology Co., Ltd.(Shanghai). All rights reserved.
 **************************************************************************************************/

package com.hn658.framework.file.media.image.op;

/**
 * 模    块：ICropper
 * 包    名：com.wzitech.chaos.framework.server.common.media.image.op
 * 项目名称：Gaea_Mobile_ServerEnd
 * 作    者：Shawn
 * 创建时间：8/15/13 1:28 AM
 * 描    述：
 * 更新纪录：1. Shawn 创建于 8/15/13 1:28 AM
 */
public interface ICropper {
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
