package com.hn658.framework.logging;

import org.slf4j.Marker;

import com.hn658.framework.logging.context.CurrentLogContext;

public class Logger {
	
	private org.slf4j.Logger logger;

	public Logger(org.slf4j.Logger logger) {
		this.logger = logger;
	}

	public String getName() {
		return logger.getName();
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public void trace(String category, String msg) {
		CurrentLogContext.setLogCategory(category);
		logger.trace(msg);
	}

	public void trace(String category, String format, Object arg) {
		CurrentLogContext.setLogCategory(category);
		logger.trace(format, arg);
	}

	public void trace(String category, String format, Object arg1, Object arg2) {
		CurrentLogContext.setLogCategory(category);
		logger.trace(format, arg1, arg2);
	}

	public void trace(String category, String format, Object[] argArray) {
		CurrentLogContext.setLogCategory(category);
		logger.trace(format, argArray);
	}

	public void trace(String category, String msg, Throwable t) {
		CurrentLogContext.setLogCategory(category);
		logger.trace(msg, t);
	}

	public boolean isTraceEnabled(Marker marker) {
		return logger.isTraceEnabled(marker);
	}

	public void trace(String category, Marker marker, String msg) {
		CurrentLogContext.setLogCategory(category);
		logger.trace(marker, msg);
	}

	public void trace(String category, Marker marker, String format, Object arg) {
		CurrentLogContext.setLogCategory(category);
		logger.trace(marker, format, arg);
	}

	public void trace(String category, Marker marker, String format, Object arg1, Object arg2) {
		CurrentLogContext.setLogCategory(category);
		logger.trace(marker, format, arg1, arg2);
	}

	public void trace(String category, Marker marker, String format, Object[] argArray) {
		CurrentLogContext.setLogCategory(category);
		logger.trace(marker, format, argArray);
	}

	public void trace(String category, Marker marker, String msg, Throwable t) {
		CurrentLogContext.setLogCategory(category);
		logger.trace(marker, msg, t);
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public void debug(String category, String msg) {
		CurrentLogContext.setLogCategory(category);
		logger.debug(msg);
	}

	public void debug(String category, String format, Object arg) {
		CurrentLogContext.setLogCategory(category);
		logger.debug(format, arg);
	}

	public void debug(String category, String format, Object arg1, Object arg2) {
		CurrentLogContext.setLogCategory(category);
		logger.debug(format, arg1, arg2);
	}

	public void debug(String category, String format, Object[] argArray) {
		CurrentLogContext.setLogCategory(category);
		logger.debug(format, argArray);
	}

	public void debug(String category, String msg, Throwable t) {
		CurrentLogContext.setLogCategory(category);
		logger.debug(msg, t);
	}

	public boolean isDebugEnabled(Marker marker) {
		return logger.isDebugEnabled();
	}

	public void debug(String category, Marker marker, String msg) {
		CurrentLogContext.setLogCategory(category);
		logger.debug(marker, msg);
	}

	public void debug(String category, Marker marker, String format, Object arg) {
		CurrentLogContext.setLogCategory(category);
		logger.debug(marker, format, arg);
	}

	public void debug(String category, Marker marker, String format, Object arg1, Object arg2) {
		CurrentLogContext.setLogCategory(category);
		logger.debug(marker, format, arg1, arg2);
	}

	public void debug(String category, Marker marker, String format, Object[] argArray) {
		CurrentLogContext.setLogCategory(category);
		logger.debug(marker, format, argArray);
	}

	public void debug(String category, Marker marker, String msg, Throwable t) {
		CurrentLogContext.setLogCategory(category);
		logger.debug(marker, msg, t);
	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public void info(String category, String msg) {
		CurrentLogContext.setLogCategory(category);
		logger.info(msg);
	}

	public void info(String category, String format, Object arg) {
		CurrentLogContext.setLogCategory(category);
		logger.info(format, arg);
	}

	public void info(String category, String format, Object arg1, Object arg2) {
		CurrentLogContext.setLogCategory(category);
		logger.info(format, arg1, arg1);
	}

	public void info(String category, String format, Object[] argArray) {
		CurrentLogContext.setLogCategory(category);
		logger.info(format, argArray);
	}

	public void info(String category, String msg, Throwable t) {
		CurrentLogContext.setLogCategory(category);
		logger.info(msg, t);
	}

	public boolean isInfoEnabled(Marker marker) {
		return logger.isInfoEnabled(marker);
	}

	public void info(String category, Marker marker, String msg) {
		CurrentLogContext.setLogCategory(category);
		logger.info(marker, msg);
	}

	public void info(String category, Marker marker, String format, Object arg) {
		CurrentLogContext.setLogCategory(category);
		logger.info(marker, format, arg);
	}

	public void info(String category, Marker marker, String format, Object arg1, Object arg2) {
		CurrentLogContext.setLogCategory(category);
		logger.info(marker, format, arg1, arg2);
	}

	public void info(String category, Marker marker, String format, Object[] argArray) {
		CurrentLogContext.setLogCategory(category);
		logger.info(marker, format, argArray);
	}

	public void info(String category, Marker marker, String msg, Throwable t) {
		CurrentLogContext.setLogCategory(category);
		logger.info(marker, msg, t);
	}

	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	public void warn(String category, String msg) {
		CurrentLogContext.setLogCategory(category);
		logger.warn(msg);
	}

	public void warn(String category, String format, Object arg) {
		CurrentLogContext.setLogCategory(category);
		logger.warn(format, arg);
	}

	public void warn(String category, String format, Object[] argArray) {
		CurrentLogContext.setLogCategory(category);
		logger.warn(format, argArray);
	}

	public void warn(String category, String format, Object arg1, Object arg2) {
		CurrentLogContext.setLogCategory(category);
		logger.warn(format, arg1, arg2);
	}

	public void warn(String category, String msg, Throwable t) {
		CurrentLogContext.setLogCategory(category);
		logger.warn(msg, t);
	}

	public boolean isWarnEnabled(Marker marker) {
		return logger.isWarnEnabled(marker);
	}

	public void warn(String category, Marker marker, String msg) {
		CurrentLogContext.setLogCategory(category);
		logger.warn(marker, msg);
	}

	public void warn(String category, Marker marker, String format, Object arg) {
		CurrentLogContext.setLogCategory(category);
		logger.warn(marker, format, arg);
	}

	public void warn(String category, Marker marker, String format, Object arg1, Object arg2) {
		CurrentLogContext.setLogCategory(category);
		logger.warn(marker, format, arg1, arg2);
	}

	public void warn(String category, Marker marker, String format, Object[] argArray) {
		CurrentLogContext.setLogCategory(category);
		logger.warn(marker, format, argArray);
	}

	public void warn(String category, Marker marker, String msg, Throwable t) {
		CurrentLogContext.setLogCategory(category);
		logger.warn(marker, msg, t);
	}

	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	public void error(String category, String msg) {
		CurrentLogContext.setLogCategory(category);
		logger.error(msg);
	}

	public void error(String category, String format, Object arg) {
		CurrentLogContext.setLogCategory(category);
		logger.error(format, arg);
	}

	public void error(String category, String format, Object arg1, Object arg2) {
		CurrentLogContext.setLogCategory(category);
		logger.error(format, arg1, arg2);
	}

	public void error(String category, String format, Object[] argArray) {
		CurrentLogContext.setLogCategory(category);
		logger.error(format, argArray);
	}

	public void error(String category, String msg, Throwable t) {
		CurrentLogContext.setLogCategory(category);
		logger.error(msg, t);
	}

	public boolean isErrorEnabled(Marker marker) {
		return logger.isDebugEnabled(marker);
	}

	public void error(String category, Marker marker, String msg) {
		CurrentLogContext.setLogCategory(category);
		logger.error(marker, msg);
	}

	public void error(String category, Marker marker, String format, Object arg) {
		CurrentLogContext.setLogCategory(category);
		logger.error(marker, format, arg);
	}

	public void error(String category, Marker marker, String format, Object arg1, Object arg2) {
		CurrentLogContext.setLogCategory(category);
		logger.error(marker, format, arg1, arg2);
	}

	public void error(String category, Marker marker, String format, Object[] argArray) {
		CurrentLogContext.setLogCategory(category);
		logger.error(marker, format, argArray);
	}

	public void error(String category, Marker marker, String msg, Throwable t) {
		CurrentLogContext.setLogCategory(category);
		logger.error(marker, msg, t);
	}

}
