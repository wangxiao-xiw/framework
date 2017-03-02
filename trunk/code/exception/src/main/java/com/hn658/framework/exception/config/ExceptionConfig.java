package com.hn658.framework.exception.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExceptionConfig {
	
	@XmlAttribute(name="postHandlingAction")
	private String postHandlingAction;//处理器处理完成后的处理办法
	
	@XmlAttribute(name="class")
	private String clazz;//处理器全类名
	
	@XmlElement(name = "refHandler")
	private List<RefHandler> refHandlerList;// 应用列表

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public List<RefHandler> getRefHandlerList() {
		return refHandlerList;
	}

	public void setRefHandlerList(List<RefHandler> refHandlerList) {
		this.refHandlerList = refHandlerList;
	}

	public String getPostHandlingAction() {
		return postHandlingAction;
	}

	public void setPostHandlingAction(String postHandlingAction) {
		this.postHandlingAction = postHandlingAction;
	}
}