package com.hn658.framework.excel.export;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;

import com.hn658.framework.shared.utils.UUIDUtils;

/**
 * 
 * <p>
 * Description
 * </p>
 * <p>
 * excel导出的执行器，负责把{@link IExcelProduct}提供的Workbook写入指定的文件
 * </p>
 * 
 * @ClassName: ExcelExportTask
 * @author davidcun
 * @date 2014年8月4日 上午11:37:57
 */
public class ExcelExportTask implements Callable<ExportResult> {

	private static final Log LOG = LogFactory.getLog(ExcelExportTask.class);

	private IDataProvider dataProvider;
	private IExcelProduct excelProduct;
	private int startRow;
	private int endRow;
	private String excelType;
	private String filePath;
	private String fileName;
	
	private Object param;

	public ExcelExportTask(int startRow, int endRow, String excelType) {
		super();
		this.startRow = startRow;
		this.endRow = endRow;
		this.excelType = excelType;
	}

	public ExcelExportTask(int startRow, int endRow, String excelType,
			String fileName) {
		super();
		this.startRow = startRow;
		this.endRow = endRow;
		this.excelType = excelType;
		this.fileName = fileName;
	}

	@Override
	/**
	 * 
	 *<p>Description:</p>
	 *<p>
	 *执行器把Workbook写入文件之后，都会返回一个{@link ExportResult}，作为执行的结果包装
	 *</p>
	 *@since 1.0
	 *@author davidcun
	 */
	public ExportResult call() throws Exception {
		FileOutputStream fos = null;
		File file = null;
		ExportResult er = new ExportResult();
		er.setError(false);
		try {
			Workbook wb = null;
			if (param!=null) {
				 wb = getExcelProduct()
						.getWorkBook(getDataProvider().getData(startRow, endRow, param),
								getExcelType());
			}else{
				wb = getExcelProduct()
						.getWorkBook(getDataProvider().getData(startRow, endRow),
								getExcelType());
			}

			file = new File(getFilePath()
					+ System.getProperty("file.separator") + getFileName()
					+ "-[" + UUIDUtils.getUUID().toString() + "]-" + "." + getExcelType());
			
			File dir = file.getParentFile();
			if(!dir.exists()){
				dir.mkdirs();
			}

			fos = new FileOutputStream(file);

			wb.write(fos);

		} catch (Exception e) {
			LOG.error("export excel file error", e);
			er.setError(true);
			er.getExceptions().add(e);
			return er;

		} finally {
			if (fos != null) {
				fos.close();
			}
		}

		er.setFile(file);

		return er;

	}

	public IDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(IDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public IExcelProduct getExcelProduct() {
		return excelProduct;
	}

	public void setExcelProduct(IExcelProduct excelProduct) {
		this.excelProduct = excelProduct;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public String getExcelType() {
		if (excelType == null) {
			setExcelType("xlsx");
		}
		return excelType;
	}

	public void setExcelType(String excelType) {
		this.excelType = excelType;
	}

	public String getFilePath() {
		if (filePath == null) {
			setFilePath(System.getProperty("java.io.tmpdir"));
		}
		return filePath;
	}

	public void setFilePath(String filePath) {
		if (filePath != null
				&& (filePath.endsWith("/") || filePath.endsWith("\\"))) {
			this.filePath = filePath.substring(0, filePath.length() - 1);
		}
		this.filePath = filePath;
	}

	public String getFileName() {
		if (fileName == null) {
			setFileName(UUIDUtils.getUUID().toString());
		}
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

}
