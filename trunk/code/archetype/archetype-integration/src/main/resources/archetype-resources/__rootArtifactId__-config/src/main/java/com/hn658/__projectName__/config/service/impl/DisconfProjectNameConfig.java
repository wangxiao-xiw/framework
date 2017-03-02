package com.hn658.${projectName}.config.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import com.hn658.${projectName}.config.service.IProjectNameConfig;

/**
 * 
 * 统一用户系统配置文件
 * @author ztjie
 * @date 2015-10-22 下午1:39:24
 * @since
 * @version
 */
@DisconfFile(filename = "${projectName}.properties")
@DisconfUpdateService(classes = {DisconfProjectNameConfig.class})
public class DisconfProjectNameConfig implements IDisconfUpdate, IProjectNameConfig {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DisconfProjectNameConfig.class);

	//#####################5173短信发送配置信息
	//接入服务的客户端IP
    private String client5173IP;

	//接入服务的客户端key
    private String client5173key;

    //接入服务的短信类型
    private String client5173Category;
    
    //web服务请求url
    private String client5173RequestUrl;

    /**
     * 5173短信平台接入客户端服务器IP, 分布式文件配置
     *
     * @return
     */
    @Override
    @DisconfFileItem(name = "5173.client.ip", associateField = "client5173IP")
    public String getClient5173IP() {
    	return client5173IP;
    }
    
    public void setClient5173IP(String client5173ip) {
    	client5173IP = client5173ip;
    }
    

	/**
     * 5173短信平台接入客户端服务器key, 分布式文件配置
     *
     * @return
     */
    @Override
    @DisconfFileItem(name = "5173.client.key", associateField = "client5173key")
	public String getClient5173key() {
		return client5173key;
	}

	public void setClient5173key(String client5173key) {
		this.client5173key = client5173key;
	}

	/**
     * 5173短信平台接入服务的短信类型, 分布式文件配置
     *
     * @return
     */
	@Override
    @DisconfFileItem(name = "5173.client.category", associateField = "category")
    public String getClient5173Category() {
		return client5173Category;
	}

	public void setClient5173Category(String client5173Category) {
		this.client5173Category = client5173Category;
	}
	
	/**
     * 5173短信平台接入服务请求URL, 分布式文件配置
     *
     * @return
     */
	@Override
    @DisconfFileItem(name = "5173.client.requestUrl", associateField = "requestUrl")
    public String getClient5173RequestUrl() {
		return client5173RequestUrl;
	}

	public void setClient5173RequestUrl(String client5173RequestUrl) {
		this.client5173RequestUrl = client5173RequestUrl;
	}

	@Override
    public void reload() throws Exception {
        
    }
	
	/**
	 * 分布式文件框架
	 */
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

	@Override
	public int getHttpClientMaxRetry() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHttpClientSocketBuffersize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHttpClientMaxRouteConnection() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHttpClientMaxConnection() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHttpClientSotimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHttpClientTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSinaValidateIpUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassIp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getImageRootDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getImageSizeList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
		
}
