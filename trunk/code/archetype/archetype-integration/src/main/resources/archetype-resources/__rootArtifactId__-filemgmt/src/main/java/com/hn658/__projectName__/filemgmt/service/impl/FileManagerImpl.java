package com.hn658.${projectName}.filemgmt.service.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hn658.framework.common.AbstractBusinessObject;
import com.hn658.framework.file.enums.FileType;
import com.hn658.framework.file.handlers.IFileHandler;
import com.hn658.framework.logging.enums.LogCategory;
import com.hn658.${projectName}.config.service.IProjectNameConfig;
import com.hn658.${projectName}.filemgmt.exceptions.FileException;
import com.hn658.${projectName}.filemgmt.service.IFileManager;

/**
 *
 *<p>描述：</p>
 *<p>
 *</p>
 *@ClassName: FileManagerImpl
 *@author davidcun
 *@date 2014年9月1日 下午3:12:23
 */
@Component("fileManagerImpl")
public class FileManagerImpl extends AbstractBusinessObject implements IFileManager{
	
	@Autowired
	private IFileHandler fileManagerHandler;

	@Autowired
	private IProjectNameConfig config;
	
	@Override
	public String saveBackendFileAvatar(byte[] file, String appId) {
		String fileUrl = null;
		try {
			String imageRootDir = config.getImageRootDir();
			imageRootDir = FilenameUtils.normalize(imageRootDir);
			String userAvatarDir = imageRootDir+ "useravatar" + System.getProperty("file.separator");
			fileUrl = fileManagerHandler.upLoadFile(file, userAvatarDir, appId, FileType.P12);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(LogCategory.SYSTEM, "文件上传异常", e);
			throw new FileException(FileException.UploadFileError, e);
		}
		return fileUrl;
	}
}
