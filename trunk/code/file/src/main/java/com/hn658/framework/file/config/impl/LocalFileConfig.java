package com.hn658.framework.file.config.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.hn658.framework.file.config.IFileConfig;

/**
 * 
 * 基础框架配置文件
 * @author ztjie
 * @date 2015-10-22 下午1:39:24
 * @since
 * @version
 */
public class LocalFileConfig implements IFileConfig {

    protected static final Logger LOGGER = LoggerFactory.getLogger(LocalFileConfig.class);

	/**
	 * 分布式文件框架
	 */
	
	//逻辑层Handler
	//缩略图尺寸100*100
	@Value("${fileManager.handlers.defaultSizeList}")
	private String defaultSizeList;
	@Override
	public String getDefaultSizeList() {
		return defaultSizeList;
	}

	public void setDefaultSizeList(String defaultSizeList) {
		this.defaultSizeList = defaultSizeList;
	}
	
	//工具层Util
	//默认文件名前缀（目录）
	@Value("${fileManager.utils.rootDir}")
	private String rootDir;

	@Override
	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}
	//本地  
	//文件路径转换格式 "/,file:///"  /test.txt-->file:///test.txt
	@Value("${fileManager.utils.transferDir}")
	private String transferDir;
	@Override
	public String getTransferDir() {
		return transferDir;
	}

	public void setTransferDir(String transferDir) {
		this.transferDir = transferDir;
	}
	
	//阿里云
	//访问id
	@Value("${fileManager.utils.accessKeyId}")
	private String accessKeyId;
	@Override
	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	//访问秘钥
	@Value("${fileManager.utils.accessKeySecret}")
	private String accessKeySecret;
	@Override
	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	//服务器节点 http://开头
	@Value("${fileManager.utils.endPoint}")
	private String endPoint;
	@Override
	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	
	//图片访问地址
	@Value("${fileManager.utils.imagePoint}")
	private String imagePoint;
	
	@Override
	public String getImagePoint() {
		return imagePoint;
	}

	public void setImagePoint(String imagePoint) {
		this.imagePoint = imagePoint;
	}

	//存储容器
	@Value("${fileManager.utils.bucketName}")
	private String bucketName;
	@Override
	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	//链接时效
	@Value("${fileManager.utils.timeOut}")
	private Integer timeOut;

	@Override
	public Integer getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}
	
		
}
