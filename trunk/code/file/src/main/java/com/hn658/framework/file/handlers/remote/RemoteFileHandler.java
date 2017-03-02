package com.hn658.framework.file.handlers.remote;

import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.hn658.framework.file.config.IFileConfig;
import com.hn658.framework.file.enums.FileType;
import com.hn658.framework.file.exception.FileException;
import com.hn658.framework.file.handlers.IFileHandler;
import com.hn658.framework.file.media.image.ThumbGeneratorMode;
import com.hn658.framework.logging.Logger;
import com.hn658.framework.logging.LoggerFactory;
import com.hn658.framework.logging.enums.LogCategory;

/**
 * 文件处理逻辑层实现类
 * 
 * 
 */
public class RemoteFileHandler implements IFileHandler, InitializingBean {

	private static final Logger LOG = LoggerFactory
			.getLogger(RemoteFileHandler.class);

	@Autowired
	private IFileConfig config;

	private AliossFileDealUtil fileDealUtil;

	private static Lock lock = new ReentrantLock();

	@Override
	public void reload(IFileConfig config) throws Exception {
		fileDealUtil.reload(config);
	}

	@Override
	public void afterPropertiesSet() {
		lock.lock();
		try {
			this.fileDealUtil = new AliossFileDealUtil(config);
		} finally {
			lock.unlock();
		}
	}

	/*************** 第一层级方法 **************/
	/**
	 * 文件拷贝
	 * 
	 * @param sourceFilePath
	 *            String 源文件路径
	 * @param targetFilePath
	 *            String 保存文件路径(目标文件路径)
	 * @param fileType
	 *            String 保存文件类型
	 * @return String 保存文件路径（url形式以“，”分割，第一张为原图，后为缩略图）
	 */
	@Override
	public String copyFile(String sourceFilePath, String targetFilePath,
			FileType fileType) {
		// TODO 阿里云文件拷贝
		// CopyObjectResult result = client.copyObject(srcBucketName, srcKey,
		// destBucketName, destKey);
		return null;
	}

	/**
	 * 文件拷贝
	 * 
	 * @param sourceFilePath
	 *            String 源文件路径
	 * @param targetFilePath
	 *            String 保存文件路径(目标文件路径)
	 * @param sizeList
	 *            String 保存文件大小
	 * @param fileType
	 *            String 保存文件类型
	 * @return String 保存文件url路径
	 */
	@Override
	public String copyImage(String sourceFilePath, String targetFilePath,
			String sizeList, FileType fileType) {
		String fileSavePath = null;
		try {
			// 拷贝文件返回路径
			fileSavePath = this.copyFile(sourceFilePath, targetFilePath,
					fileType);

		} catch (Exception e) {
			LOG.debug(LogCategory.SYSTEM, "文件拷贝异常", e);
			throw new FileException("文件拷贝异常", e);
		}
		return fileSavePath;
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            byte[] 文件字节组
	 * @param fileDir
	 *            String 保存文件路径
	 * @param fileName
	 *            String 保存文件名
	 * @param fileType
	 *            String 保存文件类型
	 * @return String 保存文件路径
	 */
	@Override
	public String upLoadFile(byte[] file, String fileDir, String fileName,
			FileType fileType) {
		String fileSavePath = null;
		try {
			// 上传文件，返回文件路径
			fileSavePath = upLoadObject(file, fileName, fileType);
		} catch (Exception e) {
			LOG.debug(LogCategory.SYSTEM, "文件上传异常", e);
			throw new FileException("文件上传异常", e);
		}
		return fileSavePath;
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            byte[] 文件字节组
	 * @param fileDir
	 *            String 保存文件路径
	 * @param fileName
	 *            String 保存文件名
	 * @param sizeList
	 *            String 图片文件保存缩略尺寸
	 * @param fileType
	 *            String 保存文件类型
	 * @return String 保存文件路径（url形式以“，”分割，第一张为原图，后为缩略图）
	 */
	@Override
	public String upLoadImage(byte[] file, String fileDir, String fileName,
			String sizeList, FileType fileType) {
		return this.upLoadFile(file, fileDir, fileName, fileType);
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 *            String 文件路径
	 */
	@Override
	public void deleteFile(String path) {
		// 删目录
		fileDealUtil.deleteDirs(path);
	}

	/********* 第二层级方法 *************/

	/**
	 * 生成缩略图
	 * 
	 * @param imageFullName
	 *            String 原始图片存放路径
	 * @param mode
	 *            ThumbGeneratorMode 缩略图生成模式
	 * @param size
	 *            String 生成缩略图尺寸
	 * @return String 生成多张缩略图路径，以“，”符号分割
	 */
	@Override
	public String generateThumb(String imageFullName, ThumbGeneratorMode mode,
			String size) {
		// TODO 阿里生成缩略图
		return null;
	}

	/**
	 * 读取文件内容
	 * 
	 * @param filePath
	 *            String 读取文件的路径
	 * @return String 读取文件的内容
	 */
	@Override
	public String readContext(String filePath) {
		// 读取文件内容
		String fileContext = null;
		try {
			fileContext = fileDealUtil.readContext(filePath);
		} catch (Exception e) {
			LOG.debug(LogCategory.SYSTEM, "读取文件异常", e);
			throw new FileException("读取文件异常", e);
		}
		return fileContext;
	}
	

	@Override
	public InputStream readFile(String filePath) throws FileException {
		return fileDealUtil.readFile(filePath);
	}

	/**
	 * 压缩文件
	 * 
	 * @param filePath
	 *            String 原文件路径
	 * @param zipFilePath
	 *            String 压缩文件路径
	 * @return String 压缩图片路径
	 */
	@Override
	public String zip(String filePath, String zipFilePath) {
		// TODO 增加阿里云文件压缩
		return null;
	}

	/******** 第三层方法 ***********/
	/**
	 * 压缩多个文件
	 * 
	 * @param filePath
	 *            String[] 原文件路径
	 * @param zipFilePath
	 *            String 压缩文件路径
	 * @return String 压缩图片路径
	 */
	@Override
	public String zip(String[] filePath, String zipFilePath) {
		// TODO 增加阿里云文件压缩
		return null;
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            byte[] 文件字节组
	 * @param fileName
	 *            String 保存文件名
	 * @param fileType
	 *            String 保存文件类型
	 * @return String 保存文件路径
	 */
	public String upLoadObject(byte[] file, String fileName, FileType fileType) {
		String url = null;
		try {
			url = fileDealUtil.saveFile(file, fileName, fileType);
		} catch (Exception e) {
			LOG.debug(LogCategory.SYSTEM, "文件上传异常", e);
			throw new FileException("文件上传异常", e);
		}
		return url;
	}

	/**
	 * 文件拷贝
	 * 
	 * @param fileName
	 *            String 源文件路径
	 * @param fileType
	 *            String 保存文件类型
	 * @param fileSaveName
	 *            String 保存文件路径
	 * @return String 保存文件路径
	 */
	public String copyObject(String fileName, FileType fileType,
			String fileSaveName) {
		// TODO 增加阿里云文件拷贝
		return null;
	}
}
