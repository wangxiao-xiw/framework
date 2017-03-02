/************************************************************************************
 *  Copyright 2011 WZITech Corporation. All rights reserved.
 *	
 *	模        块：	ExceptionPolicyEntry
 *	包        名：	com.wzitech.iboxpayment.framework.exception
 *	项目名称：	iboxpayment-framework-exception 
 *	作        者： 	Shawn
 *	创建时间：	2011-10-17
 *	描        述：
 *	更新纪录：
 * 
 ************************************************************************************/
package com.hn658.framework.exception;

import java.lang.reflect.Method;
import java.util.List;

import com.hn658.framework.exception.handler.IExceptionHandler;
import com.hn658.framework.shared.exception.SystemException;

/**
 * 异常处理策略项
 * 
 * @author Shawn
 * 
 */
public class ExceptionPolicyEntry {
	/**
	 * 异常处理后行为
	 */
	private PostHandlingAction postHandlingAction;
	
	/**
	 * 异常处理器集合
	 */
	private List<IExceptionHandler> handlers;

	public ExceptionPolicyEntry(){
		
	}
	
	public ExceptionPolicyEntry(PostHandlingAction postHandlingAction,
			List<IExceptionHandler> handlers) {
		if (handlers == null)
			throw new NullPointerException("handlers");

		this.setPostHandlingAction(postHandlingAction);
		this.setHandlers(handlers);
	}

	/**
	 * 处理异常
	 * @param exceptionToHandle 需要处理的异常
	 * @return 是否需要rethrow
	 */
	public boolean handle(Method method, Object[] args,
			Object sourceObject, Throwable exceptionToHandle) {
		if (exceptionToHandle == null)
			throw new NullPointerException("exceptionToHandler");

		SystemException chainException = ExecuteHandlerChain(method, args,
				sourceObject, exceptionToHandle);

		return RethrowRecommended(chainException, exceptionToHandle);
	}

	/**
	 * 判断是否需要rethrow
	 * @param chainException
	 * @param originalException
	 * @return
	 */
	private boolean RethrowRecommended(SystemException chainException,
			Throwable originalException) {
		if (getPostHandlingAction() == PostHandlingAction.None)
			return false;

		if (getPostHandlingAction() == PostHandlingAction.ThrowNewException) {
			throw IntentionalRethrow(chainException, originalException);
		}
		return true;
	}

	private SystemException IntentionalRethrow(SystemException chainException,
			Throwable originalException) {
		if (chainException != null) {
			throw chainException;
		}

		SystemException wrappedException = new SystemException(
				"Unable to rethrow exception: The exception to throw is null.");

		return wrappedException;
	}

	private SystemException ExecuteHandlerChain(Method method, Object[] args,
			Object sourceObject, Throwable ex) {
		String lastHandlerName = "";

		try {
			// 按照处理器循环处理异常
			for (IExceptionHandler handler : getHandlers()) {
				lastHandlerName = handler.getClass().getName();
				ex = handler.handleException(method, args, sourceObject, ex);
			}
		} catch (Exception handlingException) {
			throw new SystemException(String.format(
					"Unable to handle exception: '{0}'.", lastHandlerName));
		}

		return (SystemException) ex;
	}

	/**
	 * @param postHandlingAction the postHandlingAction to set
	 */
	public void setPostHandlingAction(PostHandlingAction postHandlingAction) {
		this.postHandlingAction = postHandlingAction;
	}

	/**
	 * @return the postHandlingAction
	 */
	public PostHandlingAction getPostHandlingAction() {
		return postHandlingAction;
	}

	/**
	 * @param handlers the handlers to set
	 */
	public void setHandlers(List<IExceptionHandler> handlers) {
		this.handlers = handlers;
	}

	/**
	 * @return the handlers
	 */
	public List<IExceptionHandler> getHandlers() {
		return handlers;
	}
}
