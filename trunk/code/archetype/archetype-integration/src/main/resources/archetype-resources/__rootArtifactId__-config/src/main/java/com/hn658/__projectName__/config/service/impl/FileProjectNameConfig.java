package com.hn658.${projectName}.config.service.impl;

import org.springframework.beans.factory.annotation.Value;

import com.hn658.${projectName}.config.service.IProjectNameConfig;

/**
 * 
 * 基础框架配置文件
 * @author ztjie
 * @date 2015-10-22 下午1:39:24
 * @since
 * @version
 */
public class FileProjectNameConfig implements IProjectNameConfig {

	//#####################5173短信发送配置信息
	//接入服务的客户端IP
	@Value("${5173.client.ip}")
    private String client5173IP;

	//接入服务的客户端key
	@Value("${5173.client.key}")
	private String client5173key;

    //接入服务的短信类型
	@Value("${5173.client.category}")
    private String client5173Category;
    
    //web服务请求url
	@Value("${5173.client.requestUrl}")
    private String client5173RequestUrl;

    /**
     * 5173短信平台接入客户端服务器IP, 分布式文件配置
     *
     * @return
     */
    @Override
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
    public String getClient5173RequestUrl() {
		return client5173RequestUrl;
	}

	public void setClient5173RequestUrl(String client5173RequestUrl) {
		this.client5173RequestUrl = client5173RequestUrl;
	}
	
	/**
	 * 分布式文件框架
	 */
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
	
	@Value("${httpclient.connection.timeout}")
	private int httpClientTimeout;
	
	@Value("${httpclient.connection.sotimeout}")
	private int httpClientSotimeout;
	
	@Value("${httpclient.connection.maxconnection}")
	private int httpClientMaxConnection;
	
	@Value("${httpclient.connection.maxrouteconnection}")
	private int httpClientMaxRouteConnection;
	
	@Value("${httpclient.connection.socketbuffersize}")
	private int httpClientSocketBuffersize;
	
	@Value("${httpclient.connection.maxretry}")
	private int httpClientMaxRetry;

	@Value("${sina.validate.ip.url}")
	private String sinaValidateIpUrl;
	
	@Value("${validate.pass.address}")
	private String passAddress;
	
	@Value("${validate.pass.ip}")
	private String passIp;
	
	@Override
	public int getHttpClientTimeout() {
		return httpClientTimeout;
	}

	public void setHttpClientTimeout(int httpClientTimeout) {
		this.httpClientTimeout = httpClientTimeout;
	}

	@Override
	public int getHttpClientSotimeout() {
		return httpClientSotimeout;
	}

	public void setHttpClientSotimeout(int httpClientSotimeout) {
		this.httpClientSotimeout = httpClientSotimeout;
	}

	@Override
	public int getHttpClientMaxConnection() {
		return httpClientMaxConnection;
	}

	public void setHttpClientMaxConnection(int httpClientMaxConnection) {
		this.httpClientMaxConnection = httpClientMaxConnection;
	}

	@Override
	public int getHttpClientMaxRouteConnection() {
		return httpClientMaxRouteConnection;
	}

	public void setHttpClientMaxRouteConnection(int httpClientMaxRouteConnection) {
		this.httpClientMaxRouteConnection = httpClientMaxRouteConnection;
	}

	@Override
	public int getHttpClientSocketBuffersize() {
		return httpClientSocketBuffersize;
	}

	public void setHttpClientSocketBuffersize(int httpClientSocketBuffersize) {
		this.httpClientSocketBuffersize = httpClientSocketBuffersize;
	}

	@Override
	public int getHttpClientMaxRetry() {
		return httpClientMaxRetry;
	}

	public void setHttpClientMaxRetry(int httpClientMaxRetry) {
		this.httpClientMaxRetry = httpClientMaxRetry;
	}

	@Override
	public String getSinaValidateIpUrl() {
		return sinaValidateIpUrl;
	}

	public void setSinaValidateIpUrl(String sinaValidateIpUrl) {
		this.sinaValidateIpUrl = sinaValidateIpUrl;
	}

	@Override
	public String getPassAddress() {
		return passAddress;
	}

	public void setPassAddress(String passAddress) {
		this.passAddress = passAddress;
	}

	@Override
	public String getPassIp() {
		return passIp;
	}

	public void setPassIp(String passIp) {
		this.passIp = passIp;
	}
	
	/**
	 * 图片存放根路径
	 */
	@Value("${__projectName__.image.rootdir}")
	private String imageRootDir = "/srv/__projectName__/imagefile";
	
	/**
	 * 图片缩略图规格
	 */
	@Value("${__projectName__.image.gensizelist}")
	private String imageSizeList="64x64,400x400";

	@Override
	public String getImageRootDir() {
		return imageRootDir;
	}

	public void setImageRootDir(String imageRootDir) {
		this.imageRootDir = imageRootDir;
	}

	@Override
	public String getImageSizeList() {
		return imageSizeList;
	}

	public void setImageSizeList(String imageSizeList) {
		this.imageSizeList = imageSizeList;
	}
	
}
