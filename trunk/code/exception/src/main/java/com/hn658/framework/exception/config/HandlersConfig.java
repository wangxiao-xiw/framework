package com.hn658.framework.exception.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class HandlersConfig {
	
	@XmlElement(name = "handler")
	private List<HandlerConfig> handlerList;// 异常配置处理器

	public List<HandlerConfig> getHandlerList() {
		return handlerList;
	}

	public void setHandlerList(List<HandlerConfig> handlerList) {
		this.handlerList = handlerList;
	}

}