package com.hn658.framework.excel.export;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QueueFullRejectedExecutionHandler implements
		RejectedExecutionHandler {

	private static final Log LOG = LogFactory.getLog(QueueFullRejectedExecutionHandler.class);
	
	public QueueFullRejectedExecutionHandler() {

	}

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//		System.out.println(r);
		LOG.error(executor+" {executor and runnable} "+r);
		//we should not ignore this handler
	}

}
