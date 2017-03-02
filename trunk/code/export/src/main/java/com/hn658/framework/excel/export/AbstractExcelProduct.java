package com.hn658.framework.excel.export;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * <p>
 * Description
 * </p>
 * <p>
 * 这个类主要是提供Excel生成的抽象实现，*在getWorkBook方法里根据实现者提供的
 * head及class类型，动态的生成Workbook对象。当然，如果需要完全控制Workbook的生成， 那么就需要自己重载此方法
 * </p>
 * 
 * @ClassName: AbstractExcelProduct
 * @author davidcun
 * @date 2014年8月4日 上午11:12:30
 */
public abstract class AbstractExcelProduct implements IExcelProduct {
	
	
	private static final Log LOG = LogFactory.getLog(AbstractExcelProduct.class);

	@Override
	public Workbook getWorkBook(List<?> data, String excelType) {

		Class<?> cls = getDataClass();
		List<Method> mds = new ArrayList<Method>();
		
		Workbook wb = null;
		if ("xls".equals(excelType)) {
			wb = new HSSFWorkbook();
		}else if("xlsx".equals(excelType)){
			wb = new XSSFWorkbook();
		}else{
			LOG.error(String.format("no such file type of excel %s,we will use xlsx", excelType));
			wb = new XSSFWorkbook();
		}
		try {
			ExcelConfig excelConfig = this.getExcelConfig();
			Sheet st = wb.createSheet(excelConfig.getSheetName());
			Row row = st.createRow(0);
			for (int i = 0; i < excelConfig.getHeader().size(); i++) {
				String headerName = excelConfig.getHeader().get(i);
				row.createCell(i).setCellValue(headerName);
			}
			for (String str : getExcelConfig().getColumnMethod()) {
				mds.add(cls.getDeclaredMethod(str, null));
			}
			for (int i = 0 ; i < data.size() ; i++) {
				row = st.createRow(i+1);
				for (int j = 0; j < mds.size(); j++) {
					Object targetObject = data.get(i);
					Method method = mds.get(j);
					Object value = method.invoke(targetObject, null);
					if (value==null) {
						LOG.error(String.format("invoke method of %s.%s result is null",cls.getName(),mds.get(j).getName()));
						value = "";
					}
					if (method.getReturnType().equals(Date.class)
							&& (value instanceof Date)) {
						row.createCell(j).setCellValue(getDateString((Date)value));
					}else{
						row.createCell(j).setCellValue(value.toString());
					}
				}
			}
		} catch (SecurityException e) {
			LOG.error("create Workbook error",e);
		} catch (NoSuchMethodException e) {
			LOG.error("create Workbook error",e);
		} catch (IllegalAccessException e) {
			LOG.error("create Workbook error",e);
		} catch (InvocationTargetException e) {
			LOG.error("create Workbook error",e);
		}

		return wb;
	}
	
	public String getDateString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(date);
	}
}
