/***************************************************************************************************
 * Copyright (c) 2013. WZITech Information Technology Co., Ltd.(Shanghai). All rights reserved.
 **************************************************************************************************/

package com.hn658.framework.file.media.image.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hn658.framework.file.media.image.AbstractImageManager;
import com.hn658.framework.file.media.image.ThumbGeneratorMode;
import com.hn658.framework.file.media.image.op.impl.JMagickCropper;
import com.hn658.framework.file.media.image.op.impl.JMagickThumbGenerator;

/**
 * 模    块：JMagickImageManager
 * 包    名：com.wzitech.chaos.framework.server.common.media
 * 项目名称：Gaea_Mobile_ServerEnd
 * 作    者：Shawn
 * 创建时间：8/14/13 6:04 PM
 * 描    述：
 * 更新纪录：1. Shawn 创建于 8/14/13 6:04 PM
 */
@Component("jMagickImageManager")
public class JMagickImageManager extends AbstractImageManager {
    @Autowired
    JMagickThumbGenerator jMagickThumbGenerator;

    @Autowired
    JMagickCropper jMagickCropper;

    static {
        //不能漏掉这个，不然jmagick.jar的路径找不到
        System.setProperty("jmagick.systemclassloader", "no");
    }

    @Override
    public String generateThumb(String imageLocalPath, ThumbGeneratorMode mode, String rootDir, String sizeList) {
        return jMagickThumbGenerator.generateThumb(imageLocalPath, mode, rootDir, sizeList);
    }

    @Override
    public String crop(String imageLocalPath, int cropStartX, int cropStartY, int cropWidth, int cropHeight) {
        return jMagickCropper.crop(imageLocalPath, cropStartX, cropStartY, cropWidth, cropHeight);
    }
}
