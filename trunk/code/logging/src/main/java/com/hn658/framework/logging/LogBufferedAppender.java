package com.hn658.framework.logging;

import java.sql.Timestamp;
import java.util.Date;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

import com.hn658.framework.logging.context.CurrentLogContext;
import com.hn658.framework.logging.enums.LogCategory;
import com.hn658.framework.logging.model.LogInfoEO;
import com.hn658.framework.logging.model.LogType;

/**
 * 
 * logback的日志输出器
 * 
 * @author ztjie
 * @date 2014-7-21 下午6:28:27
 * @since
 * @version
 */
public class LogBufferedAppender<E> extends UnsynchronizedAppenderBase<E> {

	private LogBuffer logBuffer;

	protected Layout<ILoggingEvent> layout;

	public Layout<ILoggingEvent> getLayout() {
		return layout;
	}

	public void setLayout(Layout<ILoggingEvent> layout) {
		this.layout = layout;
	}

	public LogBuffer getLogBuffer() {
		return logBuffer;
	}

	public void setLogBuffer(LogBuffer logBuffer) {
		this.logBuffer = logBuffer;
	}

	private LogInfoEO createLogInfo(E eventObject) {
		ILoggingEvent event = null;
		if (eventObject instanceof ILoggingEvent) {
			event = (ILoggingEvent) eventObject;
		} else {
			addError("参数类型不是ILoggingEvent");
		}
		// 日志对象
		LogInfoEO logInfo = new LogInfoEO();
		// 设置当前请求ID到当前线程中
		String requestId = CurrentLogContext.getThreadId();
		logInfo.setRequestId(requestId);
		// 设置日志类型
		logInfo.setLogType(LogType.BUSINESS);
		// 设置日志分类
		String category = CurrentLogContext.getLogCategory();
		if(category!=null){
			logInfo.setCategory(category);			
		}else{
			logInfo.setCategory(LogCategory.APPLICATION);
		}
		CurrentLogContext.cleanCategory();
		// 设置日志级别
		Level level = event.getLevel();
		logInfo.setLevel(level.levelStr);
		// 设置记录日志时间
		logInfo.setCreatedDateTime(new Timestamp(new Date().getTime()));
		// 设置日志信息
		String msgObj = layout.doLayout(event);
		logInfo.setMessage(msgObj);
		// 是否异常
		if (event.getThrowableProxy() != null) {
			StringBuilder builder = new StringBuilder(msgObj == null ? ""
					: String.valueOf(msgObj));
			StackTraceElementProxy[] stackTraceElementProxyArray = event
					.getThrowableProxy().getStackTraceElementProxyArray();
			// 设置日志类型为异常
			logInfo.setLogType(LogType.EXCEPTION);
			// // 记录异常堆栈
			// // 取前面10个堆栈信息,并且只取前3000个字符串
			for (StackTraceElementProxy step : stackTraceElementProxyArray) {
				String string = step.toString();
				builder.append(CoreConstants.TAB).append(string);
				ThrowableProxyUtil.subjoinPackagingData(builder, step);
				builder.append(CoreConstants.LINE_SEPARATOR);
			}
			logInfo.setMessage(builder.length() > 3000 ? builder.subSequence(0,
					3000).toString() : builder.toString());
		}
		return logInfo;
	}

	@Override
	protected void append(E eventObject) {
		if (!isStarted()) {
			return;
		}
		LogInfoEO logInfo = createLogInfo(eventObject);
		// 记录日志信息
		logBuffer.write(logInfo);
	}

}
