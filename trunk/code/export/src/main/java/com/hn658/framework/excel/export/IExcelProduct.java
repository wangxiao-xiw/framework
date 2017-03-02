package com.hn658.framework.excel.export;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 *<p>Description</p>
 *<p>
 *</p>
 *@ClassName: IExcelProduct 
 *@author davidcun
 *@date 2014年8月4日 下午2:09:53
 */
public interface IExcelProduct {

//	public List<String> getHeader();
//
//	public List<String> getColumValueMethod();
	
	
	public ExcelConfig getExcelConfig();
	/**
	 * 
	 *<p>描述：</p>
	 *<p>通过加载到的数据集excel类型返回一个Workbook对象
	 *</p>
	 *@param data 就是加载到的数据
	 *@param excelType 可能是xls或者是xlsx
	 *@since 1.0
	 *@author davidcun
	 */
	public Workbook getWorkBook(List<?> list,String excelType);
	
	/**
	 * 获取数据的class类型
	 * @author davidcun
	 * @return
	 */
	public Class<?> getDataClass();
	
}
