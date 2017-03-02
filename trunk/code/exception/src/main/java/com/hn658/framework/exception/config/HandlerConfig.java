package com.hn658.framework.exception.config;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * 
 * 处理器配置
 * @author ztjie
 * @date 2014-7-28 下午6:07:34
 * @since
 * @version
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class HandlerConfig {

	@XmlAttribute(name="id")
	private String id;//处理器ID
	
	@XmlAttribute(name="class")
	private String clazz;//处理器全类名
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
}
 