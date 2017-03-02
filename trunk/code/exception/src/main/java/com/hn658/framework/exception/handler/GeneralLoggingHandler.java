/************************************************************************************
 *  Copyright 2011 WZITech Corporation. All rights reserved.
 *	
 *	模        块：	GenericLoggingHandler
 *	包        名：	com.wzitech.iboxpayment.framework.exception.handler
 *	项目名称：	iboxpayment-framework-exception 
 *	作        者： 	Shawn
 *	创建时间：	2011-10-17
 *	描        述：
 *	更新纪录：
 * 
 ************************************************************************************/
package com.hn658.framework.exception.handler;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hn658.framework.shared.exception.SystemException;
import com.hn658.framework.shared.exception.UnknowException;

/**
 * 异常日志处理器
 * 
 * @author Shawn
 * 
 */
public class GeneralLoggingHandler implements IExceptionHandler {

	protected static final Log LOG = LogFactory.getLog(ReplaceHandler.class);
	/**
	 * 操作系统
	 */
	public static final String OS_NAME =  System.getProperty("os.name");
	/**
	 * 操作系统构架
	 */
	public static final String OS_ARCH =  System.getProperty("os.arch");
	/**
	 * 操作系统版本
	 */
	public static final String OS_VERSION =  System.getProperty("os.version");
	/**
	 * 当前系统操作用户
	 */
	public static final String USER_NAME =  System.getProperty("user.name");
	/**
	 * 平台独立换行符
	 */
	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");
	/**
	 * 当前服务器节点
	 */
	private String serverNode;

	public GeneralLoggingHandler(){
		try {
			setServerNode(InetAddress.getLocalHost().toString());
		} catch (UnknownHostException e) {
			LOG.error(e);
		}
	}
	
	@Override
	public SystemException handleException(Method method, Object[] args,
			Object sourceObject, Throwable exception) {
		// 记录异常至文件
		LOG.error(formatExceptionMessage(method, args, sourceObject, exception));

		if (SystemException.class.isAssignableFrom(exception.getClass())) {
			return (SystemException) exception;
		} else {
			return new UnknowException(exception.getMessage(),exception);
		}
	}

	/**
	 * 格式化异常方法信息
	 * @param method
	 * @param args
	 * @param values
	 * @param sourceObject
	 * @return
	 */
	protected String formatExceptionMessage(Method method, Object[] args,
			Object sourceObject, Throwable exception){
		StringBuilder formatedMessage = new StringBuilder();
		formatedMessage.append(String.format("SERVER NODE : {0}.{1}", this.serverNode, LINE_SEPARATOR));
		formatedMessage.append(String.format("OS NAME : {0}.{1}", OS_NAME, LINE_SEPARATOR));
		formatedMessage.append(String.format("OS ARCH : {0}.{1}", OS_ARCH, LINE_SEPARATOR));
		formatedMessage.append(String.format("OS VERSION : {0}.{1}", OS_VERSION, LINE_SEPARATOR));
		formatedMessage.append(String.format("USER NAME : {0}.{1}", USER_NAME, LINE_SEPARATOR));
		if(null != method){
			formatedMessage.append(String.format("Method caused exception : {0}.{1}", method.getName(), LINE_SEPARATOR));
		}
		else{
			formatedMessage.append(String.format("Method caused exception is null.{0}", LINE_SEPARATOR));
		}
		
		if(null != args && args.length > 0){
			formatedMessage.append(String.format("Method args : "));
			for(int i = 0; i < args.length; i++){
				Object arg = args[i];
				formatedMessage.append(String.format("{0}={1}", arg.getClass(), arg));
				if(i == (args.length - 1)){
					formatedMessage.append(String.format(".{0}", LINE_SEPARATOR));
				}else{
					formatedMessage.append(";");
				}
			}
		}
		else{
			formatedMessage.append(String.format("Method args is null.{0}", LINE_SEPARATOR));
		}
		if(null != sourceObject){
			formatedMessage.append(String.format("Source object is : {0}.{1}", sourceObject, LINE_SEPARATOR));
		}
		else{
			formatedMessage.append(String.format("Source object is null.{0}", LINE_SEPARATOR));
		}
		formatedMessage.append(String.format("Exception is : {0}.{1}", exception, LINE_SEPARATOR));
		return formatedMessage.toString();
	}

	/**
	 * @param serverNode the serverNode to set
	 */
	public void setServerNode(String serverNode) {
		this.serverNode = serverNode;
	}

	/**
	 * @return the serverNode
	 */
	public String getServerNode() {
		return serverNode;
	}
}
