package com.hn658.framework.exception.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "exceptionHandler")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExceptionHandlerConfig {
	
	@XmlElement(name = "handlers")
	private List<HandlersConfig> handlersList;// 异常配置处理器
	
	@XmlElement(name = "exceptions")
	private List<ExceptionsConfig> exceptionsList;// 异常配置处理器
	
	public List<HandlersConfig> getHandlersList() {
		return handlersList;
	}

	public void setHandlersList(List<HandlersConfig> handlersList) {
		this.handlersList = handlersList;
	}

	public List<ExceptionsConfig> getExceptionsList() {
		return exceptionsList;
	}

	public void setExceptionsList(List<ExceptionsConfig> exceptionsList) {
		this.exceptionsList = exceptionsList;
	}

}