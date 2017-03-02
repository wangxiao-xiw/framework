package com.hn658.framework.excel;

import java.util.ArrayList;
import java.util.List;

import com.hn658.framework.excel.domain.Student;
import com.hn658.framework.excel.export.AbstractExcelProduct;
import com.hn658.framework.excel.export.ExcelConfig;

public class ExcelProductTestImpl extends AbstractExcelProduct {


	@Override
	public Class<Student> getDataClass() {
		return Student.class;
	}

	@Override
	public ExcelConfig getExcelConfig() {
		
		List<String> header = new ArrayList<String>();
		header.add("编号");
		header.add("用户名");
		header.add("年龄");
		header.add("日期");
		
		List<String> column = new ArrayList<String>();
		column.add("getId");
		column.add("getName");
		column.add("getAge");
		column.add("getDate");
		ExcelConfig ec = new ExcelConfig();
		ec.setColumnMethod(column);
		ec.setHeader(header);
		ec.setSheetName("student");
		
		return ec;
	}

	

}
