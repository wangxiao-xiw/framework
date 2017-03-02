package com.hn658.framework.file.handlers;

import java.io.InputStream;

import com.hn658.framework.file.config.IFileConfig;
import com.hn658.framework.file.enums.FileType;
import com.hn658.framework.file.exception.FileException;
import com.hn658.framework.file.media.image.ThumbGeneratorMode;


/**
 * 文件处理逻辑层接口
 * 业务逻辑处理，方法层调用
 * @author wen
 *
 */
public interface IFileHandler {
	/**
	 * 文件拷贝
	 * @param fileName String 源文件路径
	 * @param fileType String 保存文件类型
	 * @param targetFilePath String 保存文件路径
	 * @return String 保存文件路径
	 */
	public String copyFile(String sourceFilePath, String targetFilePath, FileType fileType) throws FileException;
	
	/**
	 * 图片文件拷贝
	 * @param sourceFilePath String 源文件路径
	 * @param fileType String 保存文件类型
	 * @param targetFilePath String 保存文件路径
	 * @param sizeList String 保存缩略图尺寸
	 * @return String 保存文件路径
	 */
	public String copyImage(String sourceFilePath, String targetFilePath,String sizeList, FileType fileType) throws FileException;
	
	/**
	 * 生成缩略图
	 * @param imageFullName String 原始图片存放路径
	 * @param mode ThumbGeneratorMode  缩略图生成模式 
	 * @param sizeList String 生成缩略图尺寸
	 * @return String 生成多张缩略图路径，以“，”符号分割
	 */
	public String generateThumb(String imageFullName, ThumbGeneratorMode mode, String sizeList) throws FileException;
	
	/**
	 * 上传文件
	 * @param file 文件字节组
	 * @param fileType 保存文件类型
	 * @param targetFilePath 保存文件路径
	 * @return 保存文件路径
	 */
	public String upLoadFile(byte[] file, String fileDir, String fileName, FileType fileType) throws FileException;
	
	/**
	 * 上传图片
	 * @param file 文件字节组
	 * @param fileType 保存文件类型
	 * @param targetFilePath 保存文件路径
	 * @param sizeList 图片文件保存缩略大小
	 * @return 保存文件路径
	 */
	public String upLoadImage(byte[] file, String fileDir, String fileName, String sizeList, FileType fileType) throws FileException;
	
	/**
	 * 读取文件内容
	 * @param filePath 读取文件的路径
	 * @return 读取文件的内容
	 */
	public String readContext(String filePath) throws FileException;
	
	/**
	 * 获取文件
	 * @param filePath 读取文件的路径
	 * @return 读取文件的内容
	 */
	public InputStream readFile(String filePath) throws FileException;
	
	/**
	 * 压缩文件
	 * @param filePath String 原文件路径
	 * @param zipFilePath String 压缩文件路径
	 * @return String 压缩图片路径
	 */
	public String zip(String filePath,String zipFilePath) throws FileException;
	
	/**
	 * 压缩多个文件
	 * @param filePath String[] 原文件路径
	 * @param zipFilePath String 压缩文件路径
	 * @return String 压缩图片路径
	 */
	public String zip(String[] filePath,String zipFilePath) throws FileException;
	
	/**
	 * 删除文件
	 * @param path String 文件路径
	 */
	public void deleteFile(String path) throws FileException;

	/**
	 * 更新配置文件，重新构造文件处理对象
	 * @throws Exception
	 */
	void reload(IFileConfig config) throws Exception;
}
