package com.hn658.framework.excel.export;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>
 * Description
 * </p>
 * <p>
 * 导出结果类，结果中封装了是否导出过程错误及错误信息。并且封装了导出的文件File对象
 * </p>
 * 
 * @ClassName: ExportResult
 * @author davidcun
 * @date 2014年8月4日 下午2:06:00
 */
public class ExportResult {

	private File file;

	private String fileName;

	private boolean error;

	private List<Exception> exceptions;

	public ExportResult() {
	}
	
	public ExportResult(String fileName) {
		super();
		this.fileName = fileName;
	}

	public ExportResult(File file) {
		super();
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * thread not safe
	 * 
	 * @author davidcun
	 * @return
	 */
	public List<Exception> getExceptions() {
		if (exceptions == null) {
			exceptions = new ArrayList<Exception>();
		}
		return exceptions;
	}

	public void setExceptions(List<Exception> exceptions) {
		this.exceptions = exceptions;
	}
}
