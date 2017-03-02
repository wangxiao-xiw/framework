package com.hn658.framework.excel.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;

import com.hn658.framework.compress.CompressRegistry;
import com.hn658.framework.compress.ICompressor;
import com.hn658.framework.shared.utils.UUIDUtils;

/**
 * 
 * <p>
 * Description
 * </p>
 * <p>
 * 文件导出的上下文类，提供了同步及异步导出功能。在对类实例化后并且提供相应的属性之后，
 * 通过调用asyncExport()方法实现异步导出，异步导出的结果通过{@link IExportResultProcessor}
 * 接口实现来处理。简单例子如下
 * </p>
 * 
 * <pre>
 * ExportContext exportContext = new ExportContext();
 * exportContext.setCompressType(&quot;zip&quot;);
 * exportContext.setDataProvider(new DataProviderTestImpl());
 * exportContext.setExcelProduct(new ExcelProductTestImpl());
 * exportContext.setFileName(&quot;asyncExport&quot;);
 * exportContext.setErp(new ExportResultProcessorTestImpl());
 * exportContext.setExcelType(&quot;xlsx&quot;);
 * exportContext.setFilePath(&quot;directPath&quot;);
 * exportContext.asyncExport();
 * </pre>
 * <p>
 * 同步导出实例
 * </p>
 * 
 * <pre>
 * 在上述的方法中只需要把exportContext.asyncExport()换成exportContext.syncExport()即可。
 * </pre>
 * 
 * <pre>
 * 如下是异步分页导出的相关步骤
 * 1、实现{@link IDataProvider}接口，其中如果是异步分页导出，需要实现带参数的getData方法，
 * 如果是同步导出，则实现无参的getData方法
 * 2、实现{@link IExcelProduct}，实现getWorkbook方法，如果继承自抽象的{@link AbstractExcelProduct}类，
 * 那么可以只是提供getClass和和getHeader方法
 * 3、最后一步就是提供结果处理接口{@link IExportResultProcessor}，实现其中的process方法，
 * 方法的参数叫做{@link ExportResult}，在编写process方法的时候，注意先调用ExportResult.isError()方法
 * 如果返回true表示处理过程中出现错误，然后通过ExportResult.getExceptions()获取异常。
 * 如果没有出错则可以调用方法ExportResult.getFile()方法返回已经导出并且压缩好的文件。
 * </pre>
 * 
 * <pre>
 * 同步导出的相关步骤，
 * 与异步导出不同的是，在调用syncExport()方法的时候就可以直接返回调用的接口。
 * </pre>
 * 
 * <pre>
 * 最佳实践，需要导出大批量数据然后数据分页查询导出的时候，就采用异步导出。
 * </pre>
 * <p>
 * <h1>后续更新：</h1>
 * 
 * <pre>
 * 1、提供处理线程池可配置
 * 2、提供任务队列阀值阻塞
 * </pre>
 * 
 * </p>
 * 
 * @ClassName: ExportContext
 * @author davidcun
 * @date 2014年8月4日 上午11:45:32
 */
public class ExportContext {

	private static final Log LOG = LogFactory.getLog(ExportContext.class);

	// 文件最终存放的地方
	private String filePath;
	// 生成的文件名字
	private String fileName;
	// 压缩类型
	private String compressType;
	// excel类型，xls/xlsx
	private String excelType;
	//最大线程数量(工作线程)
	private int maxPoolSize = 5;
	
	private IDataProvider dataProvider;
	private IExcelProduct excelProduct;
	private IExportResultProcessor erp;

	private ExecutorService es ;//= Executors.newFixedThreadPool(3);
	
	//结果处理的线程池
	private ExecutorService res ;
	
//	private BlockingQueue<ExcelExportTask> taskQueue;

	public ExportContext() {
		initEs();
//		taskQueue = new LinkedBlockingQueue<ExcelExportTask>();
	}

	private void initEs() {

		es = new ThreadPoolExecutor(0, maxPoolSize, 60, TimeUnit.SECONDS,
				new LinkedBlockingDeque<Runnable>(), new ExportThreadFactory(),
				new QueueFullRejectedExecutionHandler());
		
		res = new ThreadPoolExecutor(0, 3, 60, TimeUnit.SECONDS,
				new LinkedBlockingDeque<Runnable>(), new ExportThreadFactory(),
				new QueueFullRejectedExecutionHandler());
	}
	
	public void asyncExport(Object param){
		int totalSize = getDataProvider().getTotalSize();
		int pageSize = getDataProvider().getPageSize();
		int count = totalSize / pageSize;
		if (totalSize % pageSize != 0) {
			count++;
		}
		
		List<Future<ExportResult>> results = new ArrayList<Future<ExportResult>>();

		for (int i = 0; i < count; i++) {
			
			int startRow = i * pageSize + 1;
			int endRow = (i + 1) * pageSize;

			ExcelExportTask eet = new ExcelExportTask(startRow, endRow,
					getExcelType(), getFileName() + String.valueOf(i));
			eet.setParam(param);
			eet.setDataProvider(getDataProvider());
			eet.setExcelProduct(getExcelProduct());
			Future<ExportResult> ft = es.submit(eet);
			
			results.add(ft);
		}
		res.submit(new ResultProcess(results, erp));
	}

	public void asyncExport() {
		
		int totalSize = getDataProvider().getTotalSize();
		int pageSize = getDataProvider().getPageSize();
		int count = totalSize / pageSize;
		if (totalSize % pageSize != 0) {
			count++;
		}
		
		List<Future<ExportResult>> results = new ArrayList<Future<ExportResult>>();

		for (int i = 0; i < count; i++) {
			
			int startRow = i * pageSize + 1;
			int endRow = (i + 1) * pageSize;

			ExcelExportTask eet = new ExcelExportTask(startRow, endRow,
					getExcelType(), getFileName() + String.valueOf(i));
			eet.setDataProvider(getDataProvider());
			eet.setExcelProduct(getExcelProduct());
			Future<ExportResult> ft = es.submit(eet);
			
			results.add(ft);
		}
		res.submit(new ResultProcess(results, erp));
	}

	public File syncExport() {

		FileOutputStream fos = null;
		File file = null;
		ExportResult er = new ExportResult(fileName);
		er.setError(false);
		try {
			Workbook wb = getExcelProduct().getWorkBook(getDataProvider().getData(),
					getExcelType());

			file = new File(getFilePath() + System.getProperty("file.separator")
					+ getFileName() + "." + getExcelType());
			File dir = file.getParentFile();
			if(!dir.exists()){
				dir.mkdirs();
			}
			fos = new FileOutputStream(file);
			wb.write(fos);

			return file;
		} catch (Exception e) {
			LOG.error("export excel file error", e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}

		return null;
	}
	
	public File syncExport(Object param) {

		FileOutputStream fos = null;
		File file = null;
		ExportResult er = new ExportResult(fileName);
		er.setError(false);
		try {
			Workbook wb = getExcelProduct().getWorkBook(getDataProvider().getData(param),
					getExcelType());

			file = new File(getFilePath() + System.getProperty("file.separator")
					+ getFileName() + "." + getExcelType());
			
			File dir = file.getParentFile();
			if(!dir.exists()){
				dir.mkdirs();
			}

			fos = new FileOutputStream(file);
			wb.write(fos);

			return file;
		} catch (Exception e) {
			LOG.error("export excel file error", e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}

		return null;
	}

	class ResultProcess implements Runnable {

		private List<Future<ExportResult>> results;

		private IExportResultProcessor erp;

		public ResultProcess(List<Future<ExportResult>> results,
				IExportResultProcessor erp) {
			super();
			this.results = results;
			this.erp = erp;
		}

		@Override
		public void run() {
			boolean flag = true;
			int count = 0;
			while (flag) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// ignore
				}
				for (Future<ExportResult> result : results) {
					if (result.isDone() || result.isCancelled()) {
						count++;
					}
				}
				if (count >= results.size()) {
					flag = false;
				} else {
					count = 0;
				}
			}

			List<File> files = new ArrayList<File>();
			ExportResult result = new ExportResult(fileName);
			List<Exception> ex = null;

			for (Future<ExportResult> rs : results) {
				try {
					ExportResult er = rs.get();
					if (er.isError()) {
						if (ex == null) {
							ex = new ArrayList<Exception>();
						}
						ex.addAll(er.getExceptions());
					}
					files.add(er.getFile());

				} catch (InterruptedException e) {
					LOG.error("process result error", e);
				} catch (ExecutionException e) {
					LOG.error("process result error", e);
				}
			}
			if (ex != null) {
				result.setError(true);
				result.setExceptions(ex);
			} else {

				ICompressor cs = CompressRegistry.getCompress(getCompressType());

				File file = new File(getFilePath()
						+ System.getProperty("file.separator") + getFileName() + "-"
						+ getDateString(new Date())
						+ "-[" +UUIDUtils.getUUID().toString() + "]-" +"." + getCompressType());
				cs.compress(files, file);
				if (file.exists()) {
					result.setFile(file);
					for (File f : files) {
						f.delete();
					}
				} else {
					LOG.error(String.format(
							"compress error, can not found file %s",
							file.getAbsoluteFile()));
				}
			}

			erp.process(result);
		}

		public String getDateString(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss");
			return sdf.format(date);
		}
	}

	public String getFilePath() {
		if (filePath==null) {
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
		if (fileName==null) {
			setFileName(UUIDUtils.getUUID().toString());
		}
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCompressType() {
		if (compressType==null) {
			setCompressType("zip");
		}
		return compressType;
	}

	public void setCompressType(String compressType) {
		this.compressType = compressType;
	}

	public String getExcelType() {
		if (excelType==null) {
			setExcelType("xlsx");
		}
		return excelType;
	}

	public void setExcelType(String excelType) {
		this.excelType = excelType;
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

	public IExportResultProcessor getErp() {
		return erp;
	}

	public void setErp(IExportResultProcessor erp) {
		this.erp = erp;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
}
