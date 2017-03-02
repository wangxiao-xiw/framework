package com.hn658.framework.file.handlers.local;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.hn658.framework.file.config.IFileConfig;
import com.hn658.framework.file.enums.FileType;
import com.hn658.framework.file.exception.FileException;
import com.hn658.framework.file.media.image.ThumbGeneratorMode;
import com.hn658.framework.logging.Logger;
import com.hn658.framework.logging.LoggerFactory;
import com.hn658.framework.logging.enums.LogCategory;

import magick.ImageInfo;
import magick.MagickImage;
import magick.PixelPacket;

public class LocalFileDealUtil {

	protected final static Logger logger = LoggerFactory
			.getLogger(LocalFileDealUtil.class);

	static {
		// 不能漏掉这个，不然jmagick.jar的路径找不到
		System.setProperty("jmagick.systemclassloader", "no");
	}

	private IFileConfig config;
	
	public void reload(IFileConfig config) throws Exception {
		this.config = config;
	}

	public LocalFileDealUtil(IFileConfig config) {
		this.config = config;
	}

	/******** 第一层方法 ************/
	/**
	 * 生成多张缩略图，多次生成单张
	 * 
	 * @param imageFullName
	 *            String 原始图片存放路径
	 * @param mode
	 *            ThumbGeneratorMode 缩略图生成模式
	 * @param dir
	 *            String 生成缩略图根路径
	 * @param sizeList
	 *            String 生成缩略图尺寸
	 * @return String 生成多张缩略图路径，以“，”符号分割
	 */
	public String thumb(String imageFullName, ThumbGeneratorMode mode,
			String dir, String sizeList) {
		return generateThumb(imageFullName, mode, dir, sizeList);
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 *            String 文件路径
	 */
	public void deleteFile(String path) {
		File file = getFileByPath(path);
		file.delete();
	}

	/**
	 * 删除整个目录path，包括该目录下的所有文件和子目录
	 * 
	 * @param path
	 *            String 文件路径
	 */
	public void deleteDirs(String path) {
		File rootFile = getFileByPath(path);
		if (rootFile == null) {
			return;
		}
		File[] files = rootFile.listFiles();
		if (files == null) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				deleteDirs(file.getPath());
			} else {
				file.delete();
			}
		}
		rootFile.delete();
	}

	/**
	 * 读取文件内容
	 * 
	 * @param file
	 *            File 文件对象
	 * @return String 文件内容
	 */
	public String readFile(File file)  {
		StringBuffer strBuffer = new StringBuffer();
		FileInputStream in=null;
		if (file.isFile()) {
			try {
				 in = new FileInputStream(file);
				byte[] buffer = new byte[10240];
				while ((in.read(buffer)) != -1) {
					strBuffer.append(new String(buffer));
				}
			} catch (IOException e) {
				throw new FileException(FileException.ERROR_CODE);
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					throw new FileException("文件读取流关闭异常", e);
				}
			}
		}
		return new String(strBuffer);
	}

	/**
	 * 保存文件
	 * 
	 * @param data
	 *            byte[]被保存文件字节组
	 * @param filePath
	 *            String 文件保存路径
	 * @return String 文件保存的路径
	 */
	public String saveFile(byte[] data, String filePath) {
		File file = getFileByPath(filePath);
		if (file.isFile()) {
			logger.error(LogCategory.SYSTEM, filePath
					+ ":has existed..delete..");
		}
		try {
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			out.write(data);
			out.close();
			file.exists();
		} catch (IOException e) {
			throw new FileException(FileException.ERROR_CODE);
		}
		return filePath;
	}

	/**
	 * 自动生成限制类型的文件名
	 * 
	 * @param filePath
	 *            String 文件路径
	 * @param suffix
	 *            String 传入文件类型，不满足范围则无效
	 * @return String 文件名
	 */
	public String getLimitFilePath(String filePath, FileType suffix) {
		filePath = createFilePath(filePath, FileType.ZIP);
		return filePath;
	}

	/**
	 * 读取文件内容
	 * 
	 * @param filename
	 *            String 文件名
	 * @return String 文件内容
	 */
	public String readContext(String filename) {
		StringBuffer strBuffer = new StringBuffer();
		InputStream in = this.readFile(filename);
		if (in!=null) {
			try {
				byte[] buffer = new byte[10240];
				while (in.read(buffer) != -1) {
					strBuffer.append(new String(buffer));
				}
			} catch (IOException e) {
				throw new FileException(FileException.ERROR_CODE);
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					throw new FileException("文件读取流关闭异常", e);
				}
			}
		}
		return new String(strBuffer);
	}
	
	/**
	 * 读取文件内容
	 * 
	 * @param filename
	 *            String 文件名
	 * @return String 文件内容
	 */
	public InputStream readFile(String filename) {
		File file = getFileByPath(filename);
		FileInputStream in =null;
		if (file.isFile()) {
			try {
				 in = new FileInputStream(file);
			} catch (IOException e) {
				throw new FileException(FileException.ERROR_CODE);
			}
		} else{
			logger.error(LogCategory.SYSTEM, filename
					+ ":is not a file ,please confirm you file name");
		}
		return in;
	}

	/**
	 * 压缩多个文件到生成指定压缩文件
	 * 
	 * @param files
	 *            File[] 需要压缩的文件数组
	 * @param zipFileName
	 *            String 指定压缩文件的完整路径名（可选）
	 * @return String 文件的路径
	 */
	public String zip(File[] files, String zipFileName){
		ZipOutputStream out=null;
		try {
			out = new ZipOutputStream(new FileOutputStream(
					zipFileName));
			for (int i = 0; i < files.length; i++) {
				zip(out, files[i], files[i].getName());
			}
		} catch (Exception e) {
			logger.info(LogCategory.SYSTEM, "文件压缩异常");
			throw new FileException("文件压缩异常", e);
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				throw new FileException("文件流关闭异常", e);
			}
		}
		logger.info(LogCategory.SYSTEM, "压缩文件完成");
		return zipFileName;
	}

	/**
	 * 压缩文件夹
	 * 
	 * @param inputFileName
	 *            String 输入一个文件夹完整路径名 zipFileName String 输出一个压缩文件完整路径名（可选）
	 * @return String 压缩文件完整路径名
	 */
	public String zip(String inputFileName, String zipFileName) {
		zipFileName = createFilePath(zipFileName, FileType.ZIP);
		try {
			zip(zipFileName, getFileByPath(inputFileName));
		} catch (Exception e) {
			throw new FileException("文件压缩异常", e);
		}
		return zipFileName;
	}

	/******** 第二层方法 ************/
	/**
	 * 压缩文件
	 * 
	 * @param zipFileName
	 *            String 压缩文件绝对路径
	 * @param inputFile
	 *            File 被压缩文件对象
	 */
	private void zip(String zipFileName, File inputFile) {
		ZipOutputStream out=null;
		try {
			out = new ZipOutputStream(new FileOutputStream(
					zipFileName));
			zip(out, inputFile, null);
		} catch (Exception e) {
			throw new FileException("文件压缩异常",e);
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				throw new FileException("文件流关闭异常", e);
			}
			
		}
		logger.error(LogCategory.SYSTEM, "zip done");
	}

	/**
	 * 生成多张缩略图，多次生成单张
	 * 
	 * @param imageFullName
	 *            String 原始图片存放路径
	 * @param mode
	 *            ThumbGeneratorMode 缩略图生成模式
	 * @param rootDir
	 *            String 生成缩略图根路径
	 * @param sizeList
	 *            String 生成缩略图尺寸
	 * @return String 生成多张缩略图路径，以“，”符号分割
	 */
	public String generateThumb(String imageFullName, ThumbGeneratorMode mode,
			String dir, String sizeList) {
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
						+ "_" + imgWidth + "x" + imgHeight + "."
						+ FilenameUtils.getExtension(imageFullName);
				// 设置缩略图带完整路径的文件名
				makeDirectory(imageFullName);
				String thumbFullName = FilenameUtils.getFullPath(imageFullName)
						+ thumbFileName;
				String imageUrl = generate(imageFullName, mode, imgWidth,
						imgHeight);

				if (StringUtils.isEmpty(imageUrl)) {
					return null;
				}
				generateImageUrls += transferPath(FilenameUtils
						.separatorsToUnix(imageUrl));
				generateImageUrls += ",";

			}
		}
		return (StringUtils.isBlank(generateImageUrls)) ? null
				: generateImageUrls
						.substring(0, generateImageUrls.length() - 1);
	}

	/**
	 * 生成绝对文件名称
	 * 
	 * @param filePath
	 *            String 文件路径
	 * @param suffix
	 *            String 文件类型
	 * @return String 文件类型
	 */
	public String createFilePath(String filePath, FileType suffix) {
		if (filePath != null) {
			filePath = getAbsolutePath(filePath);
		} else {
			filePath = createFileName();
		}
		return changeType(filePath, suffix);
	}

	/******** 第三层方法 ************/
	/**
	 * 文件路径转换url,目录路径中替代转换部分再添加文件路径
	 * 
	 * @param oldPath
	 *            String 文件路径
	 * @return String url路径和文件名
	 */
	public String transferPath(String oldPath) {
		String[] split = config.getTransferDir().split(",");
		String fileName = FilenameUtils.getName(oldPath);
		oldPath = FilenameUtils.getFullPath(oldPath);
		String newPath = "";
		if (split.length > 1) {
			newPath = oldPath.replaceFirst(split[0], split[1]);
		} else {
			newPath = oldPath.replaceFirst(split[0], "file:///");
		}
		return newPath + fileName;
	}

	/**
	 * url转换文件路径
	 * 
	 * @param URLPath
	 *            String url路径
	 * @return String 文件路径和文件名
	 */
	public String transURLPath(String URLPath) {
		String[] split = config.getTransferDir().split(",");
		String fileName = FilenameUtils.getName(URLPath);
		URLPath = FilenameUtils.getFullPath(URLPath);
		String newPath = "";
		if (split.length > 1) {
			newPath = URLPath.replaceFirst(split[1], split[0]);
		} else {
			newPath = URLPath.replaceFirst("file:///", split[0]);
		}
		return newPath + fileName;
	}

	/**
	 * 生成缩略图
	 * 
	 * @param imageFullName
	 *            String 原图片路径
	 * @param mode
	 *            ThumbGeneratorMode 缩略图生成模式
	 * @param width
	 *            int 生成图片宽度
	 * @param height
	 *            int 生成图片高度
	 * @return String 生成图片路径
	 */
	public String generate(String imageFullName, ThumbGeneratorMode mode,
			int width, int height) {
		try {
			File fi = getFileByPath(imageFullName); // 大图文件
			String fileSuffix = FilenameUtils.getExtension(imageFullName);

			// 如果要生成的原图的不存在则直接返回
			if (!fi.exists()) {
				logger.error("图片{}不存在，无法生成缩略图. ", imageFullName);
				return null;
			}

			ImageInfo originalImageInfo = new ImageInfo(imageFullName);
			MagickImage originalImage = new MagickImage(originalImageInfo);
			Dimension originalImageDimension = originalImage.getDimension();

			int oWidth = originalImageDimension.width; // 得到源图宽
			int oHeight = originalImageDimension.height; // 得到源图长
			int scaleWidth = 0;
			int scaleHeight = 0;
			int startX = 0;
			int startY = 0;

			double scaling = (double) oWidth / (double) oHeight;

			if (mode == ThumbGeneratorMode.ScaleByWidthAndHeight) {
				double newScaling = (double) width / (double) height;

				// 根据生成图片长宽比例及原始长宽比例缩小图片，
				// 并是图片居中放置
				if (scaling >= newScaling) {
					// 如果原始图片宽度大于长度，依据长度调整宽度
					scaleWidth = width;
					scaleHeight = (int) (scaleWidth / scaling);
					startX = 0;
					startY = (int) ((height - scaleHeight) / 2);
				} else {
					scaleHeight = height;
					scaleWidth = (int) (scaleHeight * scaling);
					startX = (int) ((width - scaleWidth) / 2);
					startY = 0;
				}
			} else if (mode == ThumbGeneratorMode.ScaleByWidth) {
				scaleWidth = width;
				scaleHeight = (int) (scaleWidth / scaling);
				height = scaleHeight;
				startX = 0;
				startY = 0;
			} else if (mode == ThumbGeneratorMode.ScaleByHeight) {
				scaleHeight = height;
				scaleWidth = (int) (scaleHeight * scaling);
				width = scaleWidth;
				startX = 0;
				startY = 0;
			}

			// 设置缩略图文件名
			// 如原图为afcdeftgh1234321.jpg
			// 则缩略图格式为afcdeftgh1234321_width*height.jpg
			String thumbFileName = FilenameUtils.getBaseName(imageFullName)
					+ "_" + width + "x" + height + "." + fileSuffix;
			// 设置缩略图带完整路径的文件名
			String thumbFullName = FilenameUtils.getFullPath(imageFullName)
					+ thumbFileName;

			logger.debug("缩略图完整文件名：{}", thumbFullName);

			MagickImage thumbImage = originalImage.scaleImage(scaleWidth,
					scaleHeight);
			thumbImage.setBorderColor(PixelPacket.queryColorDatabase("white"));
			MagickImage borderedThumbImage = thumbImage
					.borderImage(new Rectangle(0, 0, startX, startY));

			borderedThumbImage.setFileName(thumbFullName);
			borderedThumbImage.writeImage(originalImageInfo);

			logger.debug("成功为{}生成缩略图{}", imageFullName + ":" + thumbFullName);
			return thumbFullName;
		} catch (Exception e) {
			logger.error("为{}生成缩略图时发生错误：{}", e.getMessage());
			return null;
		}
	}

	/**
	 * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。 注意：可能会在返回false的时候创建部分父目录。
	 * 
	 * @param fileName
	 *            String 要创建的目录的目录名
	 * @return boolean 完全创建成功时返回true，否则返回false。
	 * @since 0.1
	 */
	public boolean makeDirectory(String fileName) {
		File file = getFileByPath(fileName);
		return makeDirectory(file);
	}

	/**
	 * 生成文件名称
	 * 
	 * @return String 按配置根目录rootDir和当前时间生成文件路径
	 */

	public String createFileName() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * 修改文件路径中的文件类型
	 * 
	 * @param fullPath
	 *            String 文件路径
	 * @param suffix
	 *            文件类型
	 * @return String 文件路径
	 */
	public String changeType(String fullPath, FileType suffix) {
		if (suffix != null) {
			fullPath = trimType(fullPath);
			if (suffix.getType() != "") {
				fullPath += "." + suffix.getType();
			}
		}
		return fullPath;
	}

	/******** 第四层方法 ************/
	/**
	 * 压缩文件夹
	 * 
	 * @param out
	 *            ZipOutputStream 压缩文件输出字节流
	 * @param f
	 *            File 被压缩文件对象
	 * @param base
	 *            String 被压缩文件目标目录
	 */
	private void zip(ZipOutputStream out, File f, String base) {
		FileInputStream in =null;
		try {
			if (f.isDirectory()) {
				File[] fl = f.listFiles();
				out.putNextEntry(new ZipEntry(base + "/"));
				base = base.length() == 0 ? "" : base + "/";
				for (int i = 0; i < fl.length; i++) {
					zip(out, fl[i], base + fl[i].getName());
				}
			} else {
				out.putNextEntry(new ZipEntry(base));
				 in = new FileInputStream(f);
				int b;
				while ((b = in.read()) != -1) {
					out.write(b);
				}
			}
		} catch (Exception e) {
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				throw new FileException("文件流关闭异常", e);
			}
		}
	}

	/**
	 * 获取File对象，当前为本地
	 * 
	 * @param 本地文件路径
	 * @return File对象
	 */
	public File getFileByPath(String path) {
		path = getAbsolutePath(path);
		return new File(path);
	}

	/**
	 * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。 注意：可能会在返回false的时候创建部分父目录。
	 * 
	 * @param file
	 *            要创建的目录
	 * @return boolean 完全创建成功时返回true，否则返回false。
	 * @since 0.1
	 */
	public boolean makeDirectory(File file) {
		File parent = file.getParentFile();
		if (parent != null) {
			return parent.mkdirs();
		}
		return false;
	}

	/******** 辅助方法 ************/

	/**
	 * 判断路径文件是否存在并为文件类型
	 * 
	 * @param path
	 *            String 文件路径
	 * @return boolean 存在返回true，不存在就返回false
	 */
	public boolean isFile(String path) {
		File file = getFileByPath(path);
		if (file.exists()) {
			if (file.isFile()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断路径文件是否存在并为目录类型
	 * 
	 * @param path
	 *            String 文件路径
	 * @return boolean 是为true，否则为false
	 */
	public boolean isDir(String path) {
		File file = getFileByPath(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取文件的绝对路径
	 * 
	 * @param filePath
	 *            String 文件路径
	 * @return filePath String 文件的绝对路径
	 * 
	 */
	public String getAbsolutePath(String filePath) {
		if (!isAbsolute(filePath)) {
			filePath = config.getRootDir() + filePath;
		}
		return filePath;
	}

	/**
	 * 根据传入的文件全名（包括后缀名）或者文件全路径返回文件名（没有后缀名） 适用于DOS和UNIX a/b/c.txt --> c
	 * a/b/.txt/d -->d a/b/.txt -->""
	 * 
	 * @param fullPath
	 *            String 文件全名（包括后缀名）或者文件全路径
	 * @return String 文件名（没有后缀名）
	 */
	public String getPureFileName(String fullPath) {
		String baseName = FilenameUtils.getBaseName(fullPath);
		return baseName;
	}

	/**
	 * 将文件名中的类型部分去掉。
	 * 
	 * @param fullPath
	 *            String 文件名
	 * @return String 去掉类型部分的结果
	 * @since 0.5
	 */
	public String trimType(String fullPath) {
		String pureName = getPureFileName(fullPath);
		return fullPath.substring(0, fullPath.lastIndexOf(pureName) + pureName.length());
	}

	/**
	 * 得到文件的类型。 c:/a/b.txt -->txt c:/a/b.txt/5 -->"" c:/a/b.txt/5.log -->log
	 * c:/a/b/ -->""
	 * 
	 * @param fullPath
	 *            String 文件名
	 * @return String 文件名中的类型部分
	 * @since 0.5
	 */
	public String getTypePart(String fullPath) {
		return FilenameUtils.getExtension(fullPath);
	}

	/**
	 * 判断路径是否为相对路径
	 * 
	 * @param String
	 *            filePath 文件路径
	 * @return boolean 判断结果,是为true，否则为false
	 */
	public boolean isAbsolute(String filePath) {
		if (FilenameUtils.getPrefix(filePath).equals("")) {
			return false;
		}
		return true;
	}
}
