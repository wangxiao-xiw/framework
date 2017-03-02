/***************************************************************************************************
 * Copyright (c) 2013. WZITech Information Technology Co., Ltd.(Shanghai). All rights reserved.
 **************************************************************************************************/

package com.hn658.framework.file.media.image.op.impl;

import java.awt.Rectangle;
import java.io.File;

import magick.ImageInfo;
import magick.MagickImage;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

/**
 * 模    块：JMagickCropper
 * 包    名：com.wzitech.chaos.framework.server.common.media.image.op.impl
 * 项目名称：Gaea_Mobile_ServerEnd
 * 作    者：Shawn
 * 创建时间：8/15/13 1:28 AM
 * 描    述：
 * 更新纪录：1. Shawn 创建于 8/15/13 1:28 AM
 */
@Component("jMagickCropper")
public class JMagickCropper extends AbstractCropper {
    @Override
    public String crop(String imageLocalPath, int cropStartX, int cropStartY, int cropWidth, int cropHeight) {
        logger.debug("开始剪裁图片{}", new Object[]{imageLocalPath});
        try {
            // 检查图片是否存在
            File fi = new File(imageLocalPath);
            if (!fi.exists()) {
                logger.error("图片{}不存在，剪切图片失败. ", imageLocalPath);
                return null;
            }

            // 使用JMagick进行图片剪裁
            ImageInfo originalImageInfo = new ImageInfo(imageLocalPath);
            MagickImage originalImage = new MagickImage(originalImageInfo);

            Rectangle croppedRect = new Rectangle(cropStartX, cropStartY, cropWidth, cropHeight);

            MagickImage croppedImage = originalImage.cropImage(croppedRect);

            // 计算Cropped图片名称
            String fileSuffix = FilenameUtils.getExtension(imageLocalPath);
            String cropperdFileName = FilenameUtils.getBaseName(imageLocalPath)
                    + "-cropped." + fileSuffix;
            // 设置缩略图带完整路径的文件名
            String cropperdFullFileName = FilenameUtils.getFullPath(imageLocalPath)
                    + cropperdFileName;

            croppedImage.setFileName(cropperdFullFileName);
            croppedImage.writeImage(originalImageInfo);

            logger.debug("成功剪切图片{}, 剪裁后文件名为{}.", new Object[]{imageLocalPath, cropperdFullFileName});

            return cropperdFullFileName;
        } catch (Exception e) {
            logger.error("剪切图片{}时发生错误：{}",
                    new Object[]{imageLocalPath, e});

            return imageLocalPath;
        }
    }
}
