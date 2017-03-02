/***************************************************************************************************
 * Copyright (c) 2013. WZITech Information Technology Co., Ltd.(Shanghai). All rights reserved.
 **************************************************************************************************/

package com.hn658.framework.file.media.image.op.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

/**
 * 模    块：NativeJavaCropper
 * 包    名：com.wzitech.chaos.framework.server.common.media.image.op.impl
 * 项目名称：Gaea_Mobile_ServerEnd
 * 作    者：Shawn
 * 创建时间：8/15/13 1:29 AM
 * 描    述：
 * 更新纪录：1. Shawn 创建于 8/15/13 1:29 AM
 */
@Component("nativeJavaCropper")
public class NativeJavaCropper extends AbstractCropper {
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

            BufferedImage originalImage = ImageIO.read(fi); // 读入文件
            ImageFilter cropFilter = new CropImageFilter(cropStartX, cropStartY, cropWidth, cropHeight);
            Image croppedImage = Toolkit.getDefaultToolkit().createImage(
                    new FilteredImageSource(originalImage.getSource(), cropFilter));
            BufferedImage tag = new BufferedImage(cropWidth, cropHeight,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, cropWidth, cropHeight);
            g.drawImage(croppedImage, 0, 0, null); // 绘制剪切后的图
            g.dispose();

            // 计算Cropped图片名称
            // 获取图片类型
            String fileSuffix = FilenameUtils.getExtension(imageLocalPath);
            String cropperdFileName = FilenameUtils.getBaseName(imageLocalPath)
                    + "-cropped." + fileSuffix;
            // 设置缩略图带完整路径的文件名
            String cropperdFullFileName = FilenameUtils.getFullPath(imageLocalPath)
                    + cropperdFileName;

            ImageIO.write(tag, fileSuffix, new File(cropperdFullFileName));

            logger.debug("成功剪切图片{}, 剪裁后文件名为{}.", new Object[]{imageLocalPath, cropperdFullFileName});

            return cropperdFullFileName;
        } catch (Exception e) {
            logger.error("剪切图片{}时发生错误：{}",
                    new Object[]{imageLocalPath, e});

            return imageLocalPath;
        }
    }
}
