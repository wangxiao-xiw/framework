/************************************************************************************
 *  Copyright 2011 WZITech Corporation. All rights reserved.
 *	
 *	模        块：	ExceptionPolicy
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
import java.util.Map;

/**
 * 异常处理策略
 * 
 * @author Shawn
 * 
 */
public class ExceptionPolicy {
	/**
	 * 策略项集合
	 */
	private Map<Class<?>, ExceptionPolicyEntry> policyEntries;

	public ExceptionPolicy() {

	}

	public ExceptionPolicy(Map<Class<?>, ExceptionPolicyEntry> policyEntries) {
		if (policyEntries == null)
			throw new NullPointerException("policyEntries");
		this.policyEntries = policyEntries;
	}

	/**
	 * 处理异常
	 * 
	 * @param exceptionToHandle
	 *            需要处理的异常
	 * @return 是否需要rethrow
	 */
	public boolean handleException(Method method, Object[] args,
			Object sourceObject, Throwable exceptionToHandle) {
		if (exceptionToHandle == null)
			throw new NullPointerException("exceptionToHandler");

		ExceptionPolicyEntry entry = GetPolicyEntry(exceptionToHandle);

		if (entry == null) {
			return true;
		}

		return entry.handle(method, args, sourceObject, exceptionToHandle);
	}

	private ExceptionPolicyEntry GetPolicyEntry(Throwable ex) {
		Class<?> exceptionType = ex.getClass();
		ExceptionPolicyEntry entry = this
				.FindExceptionPolicyEntry(exceptionType);
		return entry;
	}

	private ExceptionPolicyEntry GetPolicyEntry(Class<?> exceptionClass) {
		if (policyEntries.containsKey(exceptionClass)) {
			return policyEntries.get(exceptionClass);
		}
		return null;
	}

	private ExceptionPolicyEntry FindExceptionPolicyEntry(Class<?> exceptionType) {
		ExceptionPolicyEntry entry = null;

		// 从子类开始遍历查找对应异常类型处理策略
		while (exceptionType != Object.class) {
			entry = this.GetPolicyEntry(exceptionType);

			if (entry == null) {
				exceptionType = exceptionType.getSuperclass();
			} else {
				// we've found the handlers, now continue on
				break;
			}
		}

		return entry;
	}

	/**
	 * @param policyEntries
	 *            the policyEntries to set
	 */
	public void setPolicyEntries(
			Map<Class<?>, ExceptionPolicyEntry> policyEntries) {
		this.policyEntries = policyEntries;
	}

	/**
	 * @return the policyEntries
	 */
	public Map<Class<?>, ExceptionPolicyEntry> getPolicyEntries() {
		return policyEntries;
	}
}
