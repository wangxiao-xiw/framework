package com.hn658.framework.excel;

import com.hn658.framework.excel.export.ExportResult;
import com.hn658.framework.excel.export.IExportResultProcessor;

public class ExportResultProcessorTestImpl implements IExportResultProcessor {

	@Override
	public void process(ExportResult er) {
		System.out.println(er.getFile().getAbsolutePath());
	}

}
