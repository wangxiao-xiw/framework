package com.hn658.framework.excel.export;

import java.util.List;

public class ExcelConfig {

	private List<String> header;
	
	private List<String> columnMethod;
	
	private String sheetName = "newSheet";
	
	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

	public List<String> getColumnMethod() {
		return columnMethod;
	}

	public void setColumnMethod(List<String> columnMethod) {
		this.columnMethod = columnMethod;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
}
