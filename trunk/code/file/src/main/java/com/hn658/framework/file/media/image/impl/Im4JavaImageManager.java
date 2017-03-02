/***************************************************************************************************
 * Copyright (c) 2013. WZITech Information Technology Co., Ltd.(Shanghai). All rights reserved.
 **************************************************************************************************/

package com.hn658.framework.file.media.image.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hn658.framework.file.media.image.AbstractImageManager;
import com.hn658.framework.file.media.image.ThumbGeneratorMode;
import com.hn658.framework.file.media.image.op.impl.Im4JavaCropper;
import com.hn658.framework.file.media.image.op.impl.Im4JavaThumbGenerator;

/**
 * 模    块：Im4JavaImageManager
 * 包    名：com.wzitech.chaos.framework.server.common.media.image.impl
 * 项目名称：Gaea_Mobile_ServerEnd
 * 作    者：Shawn
 * 创建时间：8/15/13 1:28 AM
 * 描    述：
 * 更新纪录：1. Shawn 创建于 8/15/13 1:28 AM
 */
@Component("im4JavaImageManager")
public class Im4JavaImageManager extends AbstractImageManager {
    @Autowired
    Im4JavaThumbGenerator im4JavaThumbGenerator;

    @Autowired
    Im4JavaCropper im4JavaCropper;


    @Override
    public String generateThumb(String imageLocalPath, ThumbGeneratorMode mode, String rootDir, String sizeList) {
        return im4JavaThumbGenerator.generateThumb(imageLocalPath, mode, rootDir, sizeList);
    }

    @Override
    public String crop(String imageLocalPath, int cropStartX, int cropStartY, int cropWidth, int cropHeight) {
        return im4JavaCropper.crop(imageLocalPath, cropStartX, cropStartY, cropWidth, cropHeight);
    }
}
