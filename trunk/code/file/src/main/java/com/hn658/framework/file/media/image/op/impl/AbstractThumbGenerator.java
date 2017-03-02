package com.hn658.framework.file.media.image.op.impl;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hn658.framework.file.media.image.ThumbGeneratorMode;
import com.hn658.framework.file.media.image.op.IThumbGenerator;

/**
 * 模    块：AbstractThumbGenerator
 * 包    名：com.wzitech.chaos.framework.server.common.media.image.op.impl
 * 项目名称：Gaea_Mobile_ServerEnd
 * 作    者：Shawn
 * 创建时间：8/15/13 1:59 AM
 * 描    述：
 * 更新纪录：1. Shawn 创建于 8/15/13 1:59 AM
 */
public abstract class AbstractThumbGenerator implements IThumbGenerator {
    /**
     * 日志记录器
     */
    protected final static Logger logger = LoggerFactory.getLogger(AbstractThumbGenerator.class);

    @Override
    public String generateThumb(String imageFullName, ThumbGeneratorMode mode, String rootDir, String sizeList) {
        logger.debug("开始为{}生成size list：{}的缩略图", new Object[]{imageFullName, sizeList});

        // 多张图片url间以","分隔
        String generateImageUrls = "";

        if (StringUtils.isNotBlank(sizeList)) {
            for (String thumbSize : sizeList.split("[,:;]")) {
                thumbSize = thumbSize.trim();

                // 解析图片宽度与高度
                int imgWidth = 0, imgHeight = 0;
                String[] thumbSizeList = thumbSize.split("[xX*-]");

                if (thumbSizeList.length == 1) {
                    imgWidth = Integer.parseInt(thumbSize);
                    imgHeight = Integer.parseInt(thumbSize);
                } else if (thumbSizeList.length > 1) {
                    imgWidth = Integer.parseInt(thumbSizeList[0].trim());
                    imgHeight = Integer.parseInt(thumbSizeList[1].trim());
                }

                // 设置缩略图文件名
                // 如原图为afcdeftgh1234321.jpg
                // 则缩略图格式为afcdeftgh1234321_width*height.jpg
                String thumbFileName = FilenameUtils.getBaseName(imageFullName)
                        + "_" + imgWidth + "x" + imgHeight + "." + FilenameUtils.getExtension(imageFullName);
                // 设置缩略图带完整路径的文件名
                String thumbFullName = FilenameUtils.getFullPath(imageFullName)
                        + thumbFileName;

                logger.debug("缩略图完整文件名：{}", thumbFullName);

                String imageUrl = generate(imageFullName, mode, imgWidth, imgHeight);

                if (StringUtils.isEmpty(imageUrl)) {
                    return null;
                }

                generateImageUrls += FilenameUtils.separatorsToUnix(imageUrl).replace(
                        FilenameUtils.separatorsToUnix(rootDir), "");
                generateImageUrls += ",";

            }
        }
        return (generateImageUrls == null) ? null : generateImageUrls.substring(0, generateImageUrls.length() - 1);
    }

    protected abstract String generate(String imageFullName, ThumbGeneratorMode mode, int width, int height);
}
