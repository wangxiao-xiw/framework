package com.hn658.framework.exception.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExceptionsConfig {
	
	@XmlElement(name = "exception")
	private List<ExceptionConfig> exceptionList;// 异常配置处理器

	public List<ExceptionConfig> getExceptionList() {
		return exceptionList;
	}

	public void setExceptionList(List<ExceptionConfig> exceptionList) {
		this.exceptionList = exceptionList;
	}

}