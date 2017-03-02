package com.hn658.framework.file.config;

/**
 * 
 * 文件系统框架配置信息
 * @author ztjie
 * @date 2015-10-26 下午4:35:23
 * @since
 * @version
 */
public interface IFileConfig {

	/**
	 * 分布式文件框架
	 */
	//handler
	//缩略图尺寸100*100
	public String getDefaultSizeList();
	
	//util
	//默认文件名前缀（目录）
	public String getRootDir();
	
	//本地
	//文件路径转换格式 "/,file:///"  /test.txt-->file:///test.txt
	public String getTransferDir();
	
	//阿里云
	//访问id
	public String getAccessKeyId();
	
	//访问秘钥
	public String getAccessKeySecret();
	
	//服务器节点 http://开头
	public String getEndPoint();
	
	//图片访问地址
	public String getImagePoint();
	
	//存储容器
	public String getBucketName();
	
	//链接时效
	public Integer getTimeOut();
}
