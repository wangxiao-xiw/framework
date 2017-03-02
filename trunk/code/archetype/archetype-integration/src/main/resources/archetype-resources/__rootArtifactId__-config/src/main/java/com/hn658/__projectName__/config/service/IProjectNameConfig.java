package com.hn658.${projectName}.config.service;

/**
 * 
 * 基础框架配置信息
 * @author Think
 * @date 2015-10-26 下午4:35:23
 * @since
 * @version
 */
public interface IProjectNameConfig {

	/**
     * 5173短信平台接入客户端服务器IP, 分布式文件配置
     *
     * @return
     */
	public String getClient5173IP();

	/**
     * 5173短信平台接入客户端服务器key, 分布式文件配置
     *
     * @return
     */
	public String getClient5173key();

	/**
	 * 5173短信平台接入服务的短信类型, 分布式文件配置
	 *
	 * @return
	 */
	public String getClient5173Category();
	
	/**
	 * 5173短信平台接入客户端服务器key, 分布式文件配置
	 *
	 * @return
	 */
	public String getClient5173RequestUrl();

	//阿里云
	//访问id
	public String getAccessKeyId();
	//访问秘钥
	public String getAccessKeySecret();
	//服务器节点 http://开头
	public String getEndPoint();
	//存储容器
	public String getBucketName();
	//链接时效
	public Integer getTimeOut();

	int getHttpClientMaxRetry();

	int getHttpClientSocketBuffersize();

	int getHttpClientMaxRouteConnection();

	int getHttpClientMaxConnection();

	int getHttpClientSotimeout();

	int getHttpClientTimeout();

	String getSinaValidateIpUrl();

	String getPassAddress();

	String getPassIp();

	String getImageRootDir();

	String getImageSizeList();
	
}
