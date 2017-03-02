package com.hn658.framework.compress;

import java.io.File;
import java.util.List;

/**
 * 压缩文件接口
 * @author davidcun
 *
 */
public interface ICompressor {
	/**
	 * 压缩文件列表
	 * @param files
	 * @param file
	 */
	public void compress(List<File> files, File file);
	
	/**
	 * 压缩文件目录
	 * @param dirFile
	 */
	void compress(File srcDir,File desFile);
	
	/**
	 * 将压缩文件解压到指定目录
	 * @param file
	 * @param dir
	 */
	void decompress(File file,File dir);
	
	/**
	 * 将压缩文件解压到临时目录，并返回所有的文件句柄
	 * @param file
	 */
	List<File> decompress(File file);
}
