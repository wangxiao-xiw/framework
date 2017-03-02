package com.hn658.framework.excel;

import java.io.File;

import com.hn658.framework.excel.export.ExportContext;

public class ExportContextTest {

	public static void main(String[] args) {
		ExportContext exportContext = new ExportContext();
		// exportContext.setCompressType("zip");
		exportContext.setDataProvider(new DataProviderTestImpl());
		exportContext.setExcelProduct(new ExcelProductTestImpl());
		exportContext.setFileName("asyncExport");
		exportContext.setErp(new ExportResultProcessorTestImpl());
		// exportContext.setMaxPoolSize(5);
		// exportContext.setExcelType("xlsx");
		// exportContext.setFilePath("E:\\workspace\\eclipse\\5173\\b2c\\framework\\trunk\\code\\export");

		for (int i = 0; i < 2; i++) {
			Thread t = start(exportContext);
			System.out.println(t.getName() + "=========" + t.getId());
			t.start();
		}

		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	//
	// @Test
	// public void asynExportTest(){
	//
	// ExportContext exportContext = new ExportContext();
	// exportContext.setCompressType("zip");
	// exportContext.setDataProvider(new DataProviderTestImpl());
	// exportContext.setExcelProduct(new ExcelProductTestImpl());
	// exportContext.setFileName("asyncExport");
	// exportContext.setErp(new ExportResultProcessorTestImpl());
	// exportContext.setExcelType("xlsx");
	// exportContext.setFilePath("E:\\workspace\\eclipse\\5173\\b2c\\framework\\trunk\\code\\export");
	// exportContext.asyncExport();
	//
	// try {
	// Thread.sleep(100000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	//
	// @Test
	// public void syncExportTest(){
	//
	// ExportContext exportContext = new ExportContext();
	// exportContext.setCompressType("zip");
	// exportContext.setDataProvider(new DataProviderTestImpl());
	// exportContext.setExcelProduct(new ExcelProductTestImpl());
	// exportContext.setFileName("syncExport");
	// exportContext.setErp(new ExportResultProcessorTestImpl());
	// exportContext.setExcelType("xlsx");
	// exportContext.setFilePath("E:\\workspace\\eclipse\\5173\\b2c\\framework\\trunk\\code\\export");
	// File file = exportContext.syncExport();
	// System.out.println(file.getAbsolutePath());
	// }

	public static Thread start(final ExportContext exportContext) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				exportContext.asyncExport(new Object());
			}
		});
	}

//	@Test
	public void syncExportTest() {

		ExportContext exportContext = new ExportContext();
		exportContext.setCompressType("zip");
		exportContext.setDataProvider(new DataProviderTestImpl());
		exportContext.setExcelProduct(new ExcelProductTestImpl());
		exportContext.setFileName("syncExport");
		exportContext.setErp(new ExportResultProcessorTestImpl());
		exportContext.setExcelType("xlsx");
		exportContext
				.setFilePath("E:\\workspace\\eclipse\\5173\\b2c\\framework\\trunk\\code\\export");
		File file = exportContext.syncExport(new Object());
		System.out.println(file.getAbsolutePath());
	}

}
