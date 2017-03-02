package com.hn658.framework.shared.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hn658.framework.shared.enums.ErrorCodeType;
import com.hn658.framework.shared.exception.SystemException;

/**
 * 模    块：CommonFileUtils
 * 包    名：com.wzitech.chaos.framework.server.common.utils
 * 项目名称：dada
 * 作    者：Shawn
 * 创建时间：10/24/13 4:04 PM
 * 描    述：
 * 更新纪录：1. Shawn 创建于 10/24/13 4:04 PM
 */
public class CommonFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(CommonFileUtils.class);

    /**
     * 保存文件到磁盘
     *
     * @param fullPath
     * @param file
     *
     */
    public static void saveFile(String fullPath, byte[] file) {
        if (StringUtils.isBlank(fullPath)) {
            throw new SystemException(ErrorCodeType.InternalError.getCode(),new IllegalArgumentException("保存文件名fullPath为空"));
        }

        if (null == file || file.length == 0) {
            throw new SystemException(ErrorCodeType.InternalError.getCode(), new IllegalArgumentException("保存文件为空"));
        }

        logger.debug("要保存的文件大小为{},文件保存地址为{}", new Object[]{(file != null ? file.length : 0), fullPath});

        try {
            File dir = new File(FilenameUtils.getFullPathNoEndSeparator(fullPath));
            FileUtils.forceMkdir(dir);// 生成目录如果文件目录不存在

            FileUtils.writeByteArrayToFile(new File(fullPath), file);
        } catch (IOException e) {
            logger.error("保存文件出错{}", ExceptionUtils.getStackTrace(e));
            throw new SystemException(ErrorCodeType.InternalError.getCode(), e);
        }

    }
}
