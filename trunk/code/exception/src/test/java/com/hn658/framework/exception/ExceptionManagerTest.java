package com.hn658.framework.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import com.hn658.framework.shared.exception.BusinessException;
import com.hn658.framework.shared.exception.SystemException;

public class ExceptionManagerTest {
	
	private static Log log = LogFactory.getLog(ExceptionManagerTest.class);

	@Test
	public void test() {
		ExceptionManager exceptionManager = ExceptionManager.getInstance();
		try{
			exceptionManager.getExceptionPolicy().handleException(null, null, null, new NullPointerException("NullPointerException"));
		} catch (Exception e) {
			log.error("NullPointerException", e);
		}
		
		boolean result = exceptionManager.getExceptionPolicy().handleException(null, null, null, new SystemException("SystemException"));
		Assert.assertEquals(result, true);
		
		result = exceptionManager.getExceptionPolicy().handleException(null, null, null, new BusinessException("BusinessException"));
		Assert.assertEquals(result, false);
	}

}
