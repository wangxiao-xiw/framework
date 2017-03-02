package com.hn658.framework.excel.export;

/**
 * 
 *<p>描述：</p>
 *<p>异步导出结果处理接口，详细看@see ExportResult
 *</p>
 *@ClassName: IExportResultProcessor 
 *@author davidcun
 *@date 2014年8月4日 下午2:10:54
 */
public interface IExportResultProcessor {

	/**
	 * 导出结果可能是错误的
	 * @author davidcun
	 * @param er
	 */
	public void process(ExportResult er);
	
}
