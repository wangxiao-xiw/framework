package com.hn658.framework.file.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.hn658.framework.logging.Logger;
import com.hn658.framework.logging.LoggerFactory;
import com.hn658.framework.logging.enums.LogCategory;


public class ExportExcel {
	private Workbook wb = null;

	private Sheet sheet = null;

	private static final Logger LOG = LoggerFactory.getLogger(ExportExcel.class);
	
	/**
	 * @param wb
	 * @param sheet
	 */
	public ExportExcel(Workbook wb, Sheet sheet) {
		super();
		this.wb = wb;
		this.sheet = sheet;
	}

	/**
	 * @return the sheet
	 */
	public Sheet getSheet() {
		return sheet;
	}

	/**
	 * @param sheet
	 *            the sheet to set
	 */
	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	/**
	 * @return the wb
	 */
	public Workbook getWb() {
		return wb;
	}

	/**
	 * @param wb
	 *            the wb to set
	 */
	public void setWb(Workbook wb) {
		this.wb = wb;
	}

	/**
	 * 创建通用EXCEL头部
	 * 
	 * @param headString
	 *            头部显示的字符
	 * @param colSum
	 *            该报表的列数
	 */
	public void createNormalHead(int rowIndex, String headString, int colSum) {

		Row row = sheet.createRow(rowIndex);

		// 设置第一行
		Cell cell = row.createCell(0);
		row.setHeight((short) 400);

		// 定义单元格为字符串类型
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(new HSSFRichTextString(headString));

		// 指定合并区域
		sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0,
				(short) colSum));

		CellStyle cellStyle = wb.createCellStyle();

		cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 设置单元格字体
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 300);
		cellStyle.setFont(font);

		cell.setCellStyle(cellStyle);
	}

	/**
	 * 设置报表标题
	 * 
	 * @param columHeader
	 *            标题字符串数组
	 */
	public void createColumHeader(int rowIndex, String[] columHeader) {

		// 设置列头
		Row row = sheet.createRow(rowIndex);

		// 指定行高
		row.setHeight((short) 600);

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 单元格字体
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 250);
		cellStyle.setFont(font);

		// 设置单元格背景色
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		for (int i = 0; i < columHeader.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new HSSFRichTextString(columHeader[i]));
		}

	}

	/**
	 * 创建内容单元格
	 * 
	 * @param wb
	 *            Workbook
	 * @param row
	 *            Row
	 * @param col
	 *            short型的列索引
	 * @param align
	 *            对齐方式
	 * @param val
	 *            列值
	 */
	public void createCell(Workbook wb, Row row, int col, short align,
			String val) {
		Cell cell = row.createCell(col);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(new HSSFRichTextString(val));
		CellStyle cellstyle = wb.createCellStyle();
		cellstyle.setAlignment(align);
		cell.setCellStyle(cellstyle);
	}

	/**
	 * 创建合计行
	 * 
	 * @param colSum
	 *            需要合并到的列索引
	 * @param cellValue
	 */
	public void createLastSumRow(int colSum, String[] cellValue) {

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 单元格字体
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 250);
		cellStyle.setFont(font);

		Row lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
		Cell sumCell = lastRow.createCell(0);

		sumCell.setCellValue(new HSSFRichTextString("合计"));
		sumCell.setCellStyle(cellStyle);
		sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),
				(short) 0, sheet.getLastRowNum(), (short) colSum));// 指定合并区域

		for (int i = 2; i < (cellValue.length + 2); i++) {
			sumCell = lastRow.createCell(i);
			sumCell.setCellStyle(cellStyle);
			sumCell.setCellValue(new HSSFRichTextString(cellValue[i - 2]));

		}

	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 *            要合并单元格的excel 的sheet
	 * @param cellLine
	 *            要合并的列
	 * @param startRow
	 *            要合并列的开始行
	 * @param endRow
	 *            要合并列的结束行
	 */
	public void addMergedRegion(HSSFSheet sheet, int cellLine,
			int startRow, int endRow, Workbook workBook) {

		CellStyle style = workBook.createCellStyle(); // 样式对象

		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
		style.setAlignment(CellStyle.ALIGN_CENTER);// 水平
		// 获取第一行的数据,以便后面进行比较
		String s_will = sheet.getRow(startRow).getCell(cellLine)
				.getStringCellValue();

		int count = 0;
		boolean flag = false;
		for (int i = 1; i <= endRow; i++) {
			String s_current = sheet.getRow(i).getCell(0).getStringCellValue();
			if (s_will.equals(s_current)) {
				s_will = s_current;
				if (flag) {
					sheet.addMergedRegion(new CellRangeAddress(
							startRow - count, startRow, cellLine, cellLine));
					Row row = sheet.getRow(startRow - count);
					String cellValueTemp = sheet.getRow(startRow - count)
							.getCell(0).getStringCellValue();
					Cell cell = row.createCell(0);
					cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
					cell.setCellStyle(style); // 样式
					count = 0;
					flag = false;
				}
				startRow = i;
				count++;
			} else {
				flag = true;
				s_will = s_current;
			}
			// 由于上面循环中合并的单元放在有下一次相同单元格的时候做的，所以最后如果几行有相同单元格则要运行下面的合并单元格。
			if (i == endRow && count > 0) {
				sheet.addMergedRegion(new CellRangeAddress(endRow - count,
						endRow, cellLine, cellLine));
				String cellValueTemp = sheet.getRow(startRow - count)
						.getCell(0).getStringCellValue();
				Row row = sheet.getRow(startRow - count);
				Cell cell = row.createCell(0);
				cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
				cell.setCellStyle(style); // 样式
			}
		}
	}

	/**
	 * 输入EXCEL文件
	 * 
	 * @param fileName
	 *            文件名
	 */
	public void outputExcel(String fileName) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(fileName));
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			LOG.error(LogCategory.SYSTEM, "系统异常", e);
		} catch (IOException e) {
			LOG.error(LogCategory.SYSTEM, "系统异常", e);
		}
	}
}