package com.hn658.framework.excel.export;

import java.util.List;

/**
 * 
 * <p>
 * Description
 * </p>
 * <p>
 * 数据提供类，分为分页数据提供方法和非分页数据提供方法
 * </p>
 * 
 * @ClassName: IDataProvider
 * @author davidcun
 * @date 2014年8月4日 下午2:07:46
 */
public interface IDataProvider {

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * 分页数据提供没有的大小
	 * </p>
	 * 
	 * @param @return
	 * @return int
	 * @throws
	 * @since 1.0
	 * @author davidcun
	 */
	public int getPageSize();

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * 数据的总大小条数，程序会根据此方法返回的结果集getPageSize()方法返回的结果 来判断需要分多少页导出
	 * </p>
	 * 
	 * @param @return
	 * @return int
	 * @throws
	 * @since 1.0
	 * @author davidcun
	 */
	public int getTotalSize();

	/**
	 * 针对需要异步导出的分页数据提供
	 * 
	 * @author davidcun
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<?> getData(int startRow, int endRow);

	/**
	 * 针对无需分页导出的同步数据导出
	 * 
	 * @author davidcun
	 * @return
	 */
	public List<?> getData();

	public List<?> getData(int startRow, int endRow, Object param);

	/**
	 * 针对无需分页导出的同步数据导出
	 * 
	 * @author davidcun
	 * @return
	 */
	public List<?> getData(Object param);

}
