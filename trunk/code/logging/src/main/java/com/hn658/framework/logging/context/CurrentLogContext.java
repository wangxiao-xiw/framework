package com.hn658.framework.logging.context;

import org.springframework.core.NamedThreadLocal;

import com.hn658.framework.shared.utils.UUIDUtils;

/**
 * 当前线程日志ID信息类
 * @author ztjie
 *
 */
public class CurrentLogContext {
	
	/*
	 * 当前线程日志ID对象
	 */
	private static final ThreadLocal<String> currentThreadId = new NamedThreadLocal<String>("jgm-currentthreadid");
	
	/*
	 * 当前线程日志类型对象
	 */
	private static final ThreadLocal<String> currentLogCategory = new NamedThreadLocal<String>("jgm-currentLogCategory");
	
	/**
	 * 
	 * 当前线程日志分配日志类型
	 * @author ztjie
	 * @date 2014-7-31 下午3:39:45
	 * @param category
	 * @see
	 */
	public static void setLogCategory(String category){
		currentLogCategory.set(category);
	}
	
	/**
	 * 
	 * 获取当前线程日志分配类型
	 * @author ztjie
	 * @date 2014-7-31 下午3:42:42
	 * @return
	 * @see
	 */
	public static String getLogCategory() {
		String category = currentLogCategory.get();
		return category; 
	}
	
	/**
	 * 获取当前线程日志ID
	 * @return
	 */
	public static String getThreadId() {
		String threadId = currentThreadId.get();
		if(threadId == null){
			threadId = UUIDUtils.getUUID();
			currentThreadId.set(threadId);
		}
		return threadId; 
	}

	/**
	 * 清楚当前线程日志ID
	 */
	public static void cleanThreadId() {
		currentThreadId.set(null);
	}
	
	/**
	 * 清楚当前线程日志分配类型
	 */
	public static void cleanCategory() {
		currentLogCategory.set(null);
	}
	
	/**
	 * 清楚当前线程所有对象
	 */
	public static void clean() {
		currentThreadId.set(null);
		currentLogCategory.set(null);
	}
}
