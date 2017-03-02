package com.hn658.framework.file.media.image.op.impl;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.springframework.stereotype.Component;

/**
 * 模    块：Im4JavaCropper
 * 包    名：com.wzitech.chaos.framework.server.common.media.image.op.impl
 * 项目名称：Gaea_Mobile_ServerEnd
 * 作    者：Shawn
 * 创建时间：8/15/13 1:28 AM
 * 描    述：
 * 更新纪录：1. Shawn 创建于 8/15/13 1:28 AM
 */
@Component("im4JavaCropper")
public class Im4JavaCropper extends AbstractCropper {
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
            // 计算Cropped图片名称
            String fileSuffix = FilenameUtils.getExtension(imageLocalPath);
            String cropperdFileName = FilenameUtils.getBaseName(imageLocalPath)
                    + "-cropped." + fileSuffix;
            // 设置完整路径的文件名
            String cropperdFullFileName = FilenameUtils.getFullPath(imageLocalPath)
                    + cropperdFileName;

            // 使用IM2Java进行图片剪裁
            IMOperation imOperation = new IMOperation();
            imOperation.addImage(imageLocalPath);
            imOperation.crop(cropWidth, cropHeight, cropStartX, cropStartY);
            imOperation.addImage(cropperdFullFileName);

            ConvertCmd convertCmd = new ConvertCmd(true);
            convertCmd.run(imOperation);

            logger.debug("成功剪切图片{}, 剪裁后文件名为{}.", new Object[]{imageLocalPath, cropperdFullFileName});

            return cropperdFullFileName;
        } catch (Exception e) {
            logger.error("剪切图片{}时发生错误：{}",
                    new Object[]{imageLocalPath, e});

            return imageLocalPath;
        }
    }
}
