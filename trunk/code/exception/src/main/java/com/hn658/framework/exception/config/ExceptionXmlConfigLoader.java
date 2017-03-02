package com.hn658.framework.exception.config;

import java.io.File;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hn658.framework.exception.context.ExceptionConstants;

public final class ExceptionXmlConfigLoader {
	private static Log log = LogFactory.getLog(ExceptionXmlConfigLoader.class);
	
	private ExceptionHandlerConfig exceptionHandlerConfig = null;

	private static ExceptionXmlConfigLoader instance = new ExceptionXmlConfigLoader();

	// 不允许外部实例化对象
	private ExceptionXmlConfigLoader() {
		URL url = this.getClass().getClassLoader().getResource(ExceptionConstants.EXCEPTION_XML);
		if (url == null) {
			log.error("未找到"+ExceptionConstants.EXCEPTION_XML+"配置文件");
		}

		exceptionHandlerConfig = loadFromXml(url);
	}

	/**
	 * 得到SSOResource的实例
	 * 
	 * @return
	 */
	public static ExceptionXmlConfigLoader getInstance() {
		return instance;
	}

	public ExceptionHandlerConfig getExceptionHandlerConfig() {
		return exceptionHandlerConfig;
	}

	/**
	 * 根据指定的文件解析exception.xml文件
	 * 
	 * @param file
	 *            要解析的sso配置文件
	 * @return
	 */
	public static ExceptionHandlerConfig loadFromXml(File file) {
		try {
			JAXBContext jc = JAXBContext.newInstance(ExceptionHandlerConfig.class);
			Unmarshaller u = jc.createUnmarshaller();
			return (ExceptionHandlerConfig) u.unmarshal(file);
		} catch (JAXBException e) {
			log.error("解析"+ExceptionConstants.EXCEPTION_XML+"出错", e);
			return null;
		}
	}

	/**
	 * 根据指定的url解析exception.xml文件
	 * 
	 * @param url
	 * @return
	 */
	public static ExceptionHandlerConfig loadFromXml(URL url) {
		try {
			JAXBContext jc = JAXBContext.newInstance(ExceptionHandlerConfig.class);
			Unmarshaller u = jc.createUnmarshaller();
			return (ExceptionHandlerConfig) u.unmarshal(url);
		} catch (JAXBException e) {
			log.error("解析"+ExceptionConstants.EXCEPTION_XML+"出错", e);
			return null;
		}
	}
}
 