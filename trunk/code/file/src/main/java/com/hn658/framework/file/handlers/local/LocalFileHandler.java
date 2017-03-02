package com.hn658.framework.file.handlers.local;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
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
public class LocalFileHandler implements IFileHandler, InitializingBean {

	private static final Logger LOG = LoggerFactory
			.getLogger(LocalFileHandler.class);

	private LocalFileDealUtil fileDealUtil;

	@Autowired
	private IFileConfig config;

	private static Lock lock = new ReentrantLock();
	
	@Override
	public void reload(IFileConfig config) throws Exception {
		fileDealUtil.reload(config);
	}

	@Override
	public void afterPropertiesSet() {
		lock.lock();
		try {
			this.fileDealUtil = new LocalFileDealUtil(config);
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
	 * @param fileType
	 *            String 保存文件类型
	 * @param targetFilePath
	 *            String 保存文件路径(目标文件路径)
	 * @return String 保存文件url路径
	 */
	@Override
	public String copyFile(String sourceFilePath, String targetFilePath,
			FileType fileType) {
		String fileSavePath=null;
		try {
			// 反转路径
			sourceFilePath = fileDealUtil.transURLPath(sourceFilePath);
			// 拷贝文件返回路径
			 fileSavePath = copyObject(sourceFilePath, targetFilePath,
					fileType);
		} catch (Exception e) {
			LOG.debug(LogCategory.SYSTEM, "文件拷贝异常",e);
			throw new FileException("文件拷贝异常", e);
		}
		
		return fileDealUtil.transferPath(fileSavePath);
	}

	/**
	 * 图片文件拷贝
	 * 
	 * @param sourceFilePath
	 *            String 源文件路径
	 * @param targetFilePath
	 *            String 保存文件路径(目标文件路径)
	 * @param size
	 *            String 保存缩略图尺寸
	 * @param fileType
	 *            String 保存文件类型
	 * @return String 保存文件路径（url形式以“，”分割，第一张为原图，后为缩略图）
	 */
	@Override
	public String copyImage(String sourceFilePath, String targetFilePath, String size, FileType fileType) {
		String fileSavePath=null;
		String generatePath=null;
		try {
			// 反转路径
			sourceFilePath = fileDealUtil.transURLPath(sourceFilePath);
			// 复制文件返回文件路径
			 fileSavePath = copyObject(sourceFilePath, targetFilePath, fileType);
			// 文件路径生成缩略图路径
			 generatePath = generateThumb(fileSavePath,
					ThumbGeneratorMode.ScaleByWidthAndHeight, size);
		} catch (Exception e) {
			LOG.debug(LogCategory.SYSTEM, "拷贝图片文件异常",e);
			throw new FileException("拷贝图片文件异常", e);
		}
		// 转化路径
		return fileDealUtil.transferPath(fileSavePath) + "," + generatePath;
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
	 *@param fileType
	 *            String 保存文件类型
	 * @return String 保存文件路径
	 */
	@Override
	public String upLoadFile(byte[] file, String fileDir, String fileName,
			FileType fileType) {
		String fileSavePath=null;
		try {
			StringBuffer fileFullPath = new StringBuffer(fileDir);
			if (fileFullPath.lastIndexOf(System.getProperty("file.separator")) != fileFullPath
					.length() - 1) {
				fileFullPath.append(System.getProperty("file.separator"));
			}
			fileFullPath.append(fileName);
			fileFullPath.append(fileType.getType());
			// 上传文件，返回文件路径
			 fileSavePath = upLoadObject(file, fileType,
					fileFullPath.toString());
		} catch (Exception e) {
			LOG.debug(LogCategory.SYSTEM, "上传文件异常",e);
			throw new FileException("上传文件异常", e);
		}
		// 转化路径
		return fileDealUtil.transferPath(fileSavePath);
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
		String fileSavePath=null;
		String generatePath=null;
		try {
			// 上传文件返回文件路径
			 fileSavePath = this
					.upLoadFile(file, fileDir, fileName, fileType);
			// 文件路径生成缩略图路径
			 generatePath = generateThumb(fileSavePath,
					ThumbGeneratorMode.ScaleByWidthAndHeight, sizeList);
		} catch (Exception e) {
			LOG.debug(LogCategory.SYSTEM, "上传文件异常",e);
			throw new FileException("上传文件异常", e);
		}
		
		// 转化路径
		return fileDealUtil.transferPath(fileSavePath) + "," + generatePath;
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 *            String 文件路径
	 */
	@Override
	public void deleteFile(String path) {
		try {
			path = fileDealUtil.transURLPath(path);
			if (fileDealUtil.isFile(path)) {
				// 删文件
				fileDealUtil.deleteFile(path);
			} else if (fileDealUtil.isDir(path)) {
				// 删目录
				fileDealUtil.deleteDirs(path);
			}
		} catch (Exception e) {
			LOG.debug(LogCategory.SYSTEM, "文件删除异常",e);
			throw new FileException("删除文件异常", e);
		}
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
		boolean replace = true;
		try {
			
			if (StringUtils.isBlank(size)) {
				String[] sizes = size.split("[,;]");
				for (String sizeArray : sizes) {
					String[] split = sizeArray.split("[xX*-]");
					for (int i = 0; i < split.length; i++) {
						if (!StringUtils.isNumeric(split[i])) {
							replace = false;
							break;
						}
					}
					if (!replace) {
						break;
					}
				}
			}
			if (!replace) {
				size = config.getDefaultSizeList();
			}
			imageFullName = fileDealUtil.getAbsolutePath(imageFullName);
		} catch (Exception e) {
			LOG.debug(LogCategory.SYSTEM, "生成缩略图异常",e);
			throw new FileException("生成缩略图异常", e);
		}
		// 返回","分割的转化路径
		return fileDealUtil.thumb(imageFullName,
				ThumbGeneratorMode.ScaleByWidthAndHeight, null, size);
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
		String fileContext=null;
		try {
			filePath = fileDealUtil.transURLPath(filePath);
			// 读取文件内容
			fileContext = fileDealUtil.readContext(filePath);
		} catch (Exception e) {
			LOG.debug(LogCategory.SYSTEM, "读取文件异常",e);
			throw new FileException("读取文件异常", e);
		}
		// 反转路径
		return fileContext;
	}
	
	/**
	 * 读取文件
	 */
	@Override
	public InputStream readFile(String filePath) throws FileException {
		InputStream fileInputStream=null;
		try {
			filePath = fileDealUtil.transURLPath(filePath);
			// 读取文件内容
			fileInputStream = fileDealUtil.readFile(filePath);
		} catch (Exception e) {
			LOG.debug(LogCategory.SYSTEM, "读取文件异常",e);
			throw new FileException("读取文件异常", e);
		}
		// 反转路径
		return fileInputStream;
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
		String[] filePaths=null;
		try {
			 filePaths = new String[] { filePath };
		} catch (Exception e) {
			LOG.debug(LogCategory.SYSTEM, "文件压缩异常",e);
			throw new FileException("文件压缩异常", e);
		}
		return this.zip(filePaths, zipFilePath);
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
		File[] files = new File[filePath.length];
		File file = null;
		if (zipFilePath.lastIndexOf(".") == -1) {
			LOG.info(LogCategory.SYSTEM, zipFilePath + ":请指定压缩文件名");
			return "";
		} else {
			for (int i = 0; i < filePath.length; i++) {
				// 反转路径获取文件
				file = fileDealUtil.getFileByPath(fileDealUtil
						.transURLPath(filePath[i]));
				if (file.exists()) {
					files[i] = file;
				} else {
					LOG.info(LogCategory.SYSTEM, filePath[i] + ":当前路径文件不存在已跳过");
				}
			}
		}
		fileDealUtil.makeDirectory(zipFilePath);
		try {
			String zip = fileDealUtil.zip(files, zipFilePath);
			// 转化路径
			zip = fileDealUtil.transferPath(zip);
			return zip;
		} catch (Exception e) {
			throw new FileException("压缩文件异常", e);
		}

	}

	/**************************/

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            byte[] 文件字节组
	 * @param fileType
	 *            String 保存文件类型
	 * @param fileSaveName
	 *            String 保存文件路径
	 * @return String 保存文件路径
	 */
	public String upLoadObject(byte[] file, FileType fileType,
			String fileSaveName) {
		String fileSavePath=null;
		try {
			 fileSavePath = fileDealUtil.createFilePath(fileSaveName,
					fileType);
			fileDealUtil.makeDirectory(fileSavePath);
			fileSavePath = fileDealUtil.saveFile(file, fileSavePath);
		} catch (Exception e) {
			LOG.debug(LogCategory.SYSTEM, "文件上传异常",e);
			throw new FileException("文件上传异常", e);
		}
		return fileSavePath;
	}

	/**
	 * 文件拷贝
	 * 
	 * @param sourceFilePath
	 *            String 源文件路径
	 * @param targetFilePath
	 *            String 保存文件路径(目标文件路径)
	 * @param fileType
	 *            String 保存文件类型
	 * @return String 保存文件路径
	 */
	public String copyObject(String sourceFilePath, String targetFilePath,
			FileType fileType) {
		try {
			String fileSavePath = fileDealUtil.createFilePath(sourceFilePath,
					fileType);
			fileDealUtil.makeDirectory(fileSavePath);
			String fetchFile = fileDealUtil.readContext(fileSavePath);
			fileSavePath = fileDealUtil.saveFile(fetchFile.getBytes(),
					fileSavePath);
			return fileSavePath;
		} catch (Exception e) {
			throw new FileException("文件拷贝异常", e);
		}
	}

}
