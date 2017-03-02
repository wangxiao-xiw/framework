package com.hn658.framework.excel.export;

import java.util.List;

import com.hn658.framework.excel.exception.NotSupportException;
/**
 * 
 *<p>Description</p>
 *<p>
 * @see AbstractSyncDataProvider
 *</p>
 *@ClassName: AbstractAsyncDataProvider 
 *@author davidcun
 *@date 2014年8月4日 上午11:11:51
 */
public abstract class AbstractAsyncDataProvider implements IDataProvider {

	@Override
	public List<?> getData() {
		
		throw new NotSupportException("export of asynchronous should use the method of getData(int startRow, int endRow) which had arguments");
	}
	
	@Override
	public List<?> getData(int startRow, int endRow, Object param) {
		return null;
	}
	
	@Override
	public List<?> getData(Object param) {
		return null;
	}

}
