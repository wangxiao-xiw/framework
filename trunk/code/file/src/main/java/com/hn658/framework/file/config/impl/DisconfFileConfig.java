package com.hn658.framework.file.config.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import com.hn658.framework.file.config.IFileConfig;
import com.hn658.framework.file.handlers.IFileHandler;

/**
 * 
 * 基础框架配置文件
 * @author ztjie
 * @date 2015-10-22 下午1:39:24
 * @since
 * @version
 */
@DisconfFile(filename = "file.properties")
@DisconfUpdateService(classes = {DisconfFileConfig.class})
public class DisconfFileConfig implements IFileConfig, IDisconfUpdate, ApplicationContextAware{

    protected static final Logger LOGGER = LoggerFactory.getLogger(DisconfFileConfig.class);
	
	//逻辑层Handler 缩略图尺寸100*100
	private String defaultSizeList;
	@Override
	@DisconfFileItem(name = "fileManager.handlers.sizeList", associateField = "sizeList")
	public String getDefaultSizeList() {
		return defaultSizeList;
	}

	public void setDefaultSizeList(String defaultSizeList) {
		this.defaultSizeList = defaultSizeList;
	}
	
	//工具层Util
	//默认文件名前缀（目录）
	private String rootDir;
	@Override
	@DisconfFileItem(name = "fileManager.utils.rootDir", associateField = "rootDir")
	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}
	
	//本地  
	//文件路径转换格式 "/,file:///"  /test.txt-->file:///test.txt
	private String transferDir;
	
	@Override
	@DisconfFileItem(name = "fileManager.utils.transferDir", associateField = "transferDir")
	public String getTransferDir() {
		return transferDir;
	}

	public void setTransferDir(String transferDir) {
		this.transferDir = transferDir;
	}
	
	//阿里云
	//访问id
	private String accessKeyId;
	
	@Override
	@DisconfFileItem(name = "fileManager.utils.accessKeyId", associateField = "accessKeyId")
	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	
	//访问秘钥
	private String accessKeySecret;
	
	@Override
	@DisconfFileItem(name = "fileManager.utils.accessKeySecret", associateField = "accessKeySecret")
	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	//服务器节点 http://开头
	private String endPoint;
	
	@Override
	@DisconfFileItem(name = "fileManager.utils.endPoint", associateField = "endPoint")
	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	
	//图片访问地址
	private String imagePoint;
	
	@Override
	@DisconfFileItem(name = "fileManager.utils.imagePoint", associateField = "imagePoint")
	public String getImagePoint() {
		return imagePoint;
	}

	public void setImagePoint(String imagePoint) {
		this.imagePoint = imagePoint;
	}

	//存储容器
	private String bucketName;
	
	@Override
	@DisconfFileItem(name = "fileManager.utils.bucketName", associateField = "bucketName")
	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	//链接时效
	private Integer timeOut;

	@Override
	@DisconfFileItem(name = "fileManager.utils.timeOut", associateField = "timeOut")
	public Integer getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

	private IFileHandler fileHandler;
	
	private ApplicationContext context;
	
	@Override
	public void reload() throws Exception {
		fileHandler = context.getBean(IFileHandler.class);
		fileHandler.reload(this);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}
		
}
