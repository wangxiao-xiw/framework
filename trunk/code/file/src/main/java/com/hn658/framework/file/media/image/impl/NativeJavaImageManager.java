/***************************************************************************************************
 * Copyright (c) 2013. WZITech Information Technology Co., Ltd.(Shanghai). All rights reserved.
 **************************************************************************************************/

package com.hn658.framework.file.media.image.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hn658.framework.file.media.image.AbstractImageManager;
import com.hn658.framework.file.media.image.ThumbGeneratorMode;
import com.hn658.framework.file.media.image.op.impl.NativeJavaCropper;
import com.hn658.framework.file.media.image.op.impl.NativeJavaThumbGenerator;

/**
 * 模    块：NativeJavaImageManager
 * 包    名：com.wzitech.chaos.framework.server.common.media.image.impl
 * 项目名称：Gaea_Mobile_ServerEnd
 * 作    者：Shawn
 * 创建时间：8/14/13 6:02 PM
 * 描    述：
 * 更新纪录：1. Shawn 创建于 8/14/13 6:02 PM
 */
@Component("nativeJavaImageManager")
public class NativeJavaImageManager extends AbstractImageManager {
    @Autowired
    NativeJavaThumbGenerator nativeJavaThumbGenerator;

    @Autowired
    NativeJavaCropper nativeJavaCropper;

    @Override
    public String generateThumb(String imageLocalPath, ThumbGeneratorMode mode, String rootDir, String sizeList) {
        return nativeJavaThumbGenerator.generateThumb(imageLocalPath, mode, rootDir, sizeList);
    }

    @Override
    public String crop(String imageLocalPath, int cropStartX, int cropStartY, int cropWidth, int cropHeight) {
        return nativeJavaCropper.crop(imageLocalPath, cropStartX, cropStartY, cropWidth, cropHeight);
    }
}
