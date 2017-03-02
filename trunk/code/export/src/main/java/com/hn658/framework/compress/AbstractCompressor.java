package com.hn658.framework.compress;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象实现
 * @author davidcun
 *
 */
public abstract class AbstractCompressor implements ICompressor  {

	/**
	 * 获得指定目录下的所有文件列表
	 * @author davidcun
	 * @param dir
	 * @return
	 */
	public List<File> getFiles(File dir) {
		List<File> files = new ArrayList<File>();
		if (dir.isDirectory()) {
			File[] fs = dir.listFiles();
			for (File file : fs) {
				if (file.isDirectory()) {
					files.addAll(getFiles(file));
				} else {
					files.add(file);
				}
			}
		}else {
			files.add(dir);
		}
		return files;
		
	}
}
