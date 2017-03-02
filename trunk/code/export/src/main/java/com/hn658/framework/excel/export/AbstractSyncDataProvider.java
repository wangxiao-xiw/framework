package com.hn658.framework.excel.export;

import java.util.List;

import com.hn658.framework.excel.exception.NotSupportException;

/**
 *<p>Description</p>
 *<p>
 *此类是数据提供的同步抽象实现，数据提供接口${@link IDataProvider}定义了，同步及异步的数据提供方法。
 *所以针对用户来说，在使用的时候需要根据自己的需要实现不同的方法，有时候可能只是需要实现同步方法，
 *有时候却需要实现异步方法，所以提供了抽象适配实现。
 *</p>
 *@ClassName: AbstractSyncDataProvider 
 *@author davidcun
 *@date 2014年8月4日 上午11:05:36 
 *@since 
 *@version
 */
public abstract class AbstractSyncDataProvider implements IDataProvider {

	@Override
	public int getPageSize() {
		return 0;
	}

	@Override
	public int getTotalSize() {
		return 0;
	}

	@Override
	public List<?> getData(int startRow, int endRow) {
		throw new NotSupportException("export of synchronous should use the method of getData() which had no arguments");
	}
	
	@Override
	public List<?> getData(Object param) {
		return null;
	}
	@Override
	public List<?> getData(int startRow, int endRow, Object param) {
		return null;
	}
}
