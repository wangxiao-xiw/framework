package com.hn658.framework.file.handlers.remote;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FilenameUtils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.hn658.framework.file.config.IFileConfig;
import com.hn658.framework.file.enums.FileType;
import com.hn658.framework.file.exception.FileException;
import com.hn658.framework.logging.Logger;
import com.hn658.framework.logging.LoggerFactory;
import com.hn658.framework.logging.enums.LogCategory;
import com.hn658.framework.shared.constants.CommonConstants;

/**
 * 阿里云OSS分布式文件处理
 *
 */
public class AliossFileDealUtil{
	
	private static final Logger LOG = LoggerFactory.getLogger(RemoteFileHandler.class);

	private OSSClient client;
	
	private IFileConfig config;
	
	private Lock lock =new ReentrantLock();
	
	public void reload(IFileConfig config) throws Exception {
		lock.lock();
		try {
			if(client!=null){
				client.shutdown();	
				LOG.debug(LogCategory.SYSTEM, "配置文件重新加载,配置文件信息");
			}
			this.config = config;
			ClientConfiguration conf = new ClientConfiguration();
			client = new OSSClient(config.getEndPoint(), config.getAccessKeyId(), config.getAccessKeySecret(), conf);
		}finally{
			lock.unlock();
		}
	}

	public AliossFileDealUtil(IFileConfig config) {
		try {
			this.reload(config);
		} catch (Exception e) {
			throw new FileException("文件处理工具初始化异常");
		}
	}

	public void deleteFile(String path) {
		client.deleteObject(config.getBucketName(), path);
	}

	public void deleteDirs(String path) {
		String pathPart = getPathPart(path);
		client.deleteBucket(pathPart);
	}

	public String saveFile(byte[] data, String fileName, FileType fileType) {
		fileName = fileName+"."+fileType.getType();
		ByteArrayInputStream input = new ByteArrayInputStream(data);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(data.length);
		objectMetadata.setContentType(fileType.getMediaType());
		PutObjectResult result = client.putObject(config.getBucketName(), fileName, input,
				objectMetadata);
		LOG.debug(LogCategory.SYSTEM, "上传文件成功,返回结果:"+result.getETag());
		if(fileType.getMediaType().contains("image")){
			return getImageUrl(fileName);
		}else{			
			return getUrl(fileName);
		}
	}

	private String getImageUrl(String fileName) {
		StringBuffer url = new StringBuffer(config.getImagePoint());
		url.append(CommonConstants.HTTP_INTERVAL).append(fileName);
		String imageUrl = url.toString();
		return imageUrl;
	}

	public String getUrl(String fileName) {
		URL url = client.generatePresignedUrl(config.getBucketName(),
				fileName,
				new Date(new Date().getTime() + config.getTimeOut()));
		return url.toString();
	}

	public String readContext(String filename) {
		InputStream in = this.readFile(filename);
		StringBuffer strBuffer = new StringBuffer();
		byte[] buffer = new byte[10240];
		try {
			while (in.read(buffer) != -1) {
				strBuffer.append(new String(buffer));
			}
		} catch (IOException e) {
			throw new FileException("文件名所对应的文件不存在");
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		return new String(strBuffer);
	}
	
	public InputStream readFile(String filename) {
		try {
			OSSObject object = client.getObject(config.getBucketName(), filename);
			InputStream in = object.getObjectContent();
			return in;
		} catch (Exception e) {
			throw new FileException("文件名所对应的文件不存在");
		}
	}

	public boolean makeDirectory(String fileName) {
		return false;
	}

	public boolean isFile(String path) {
		try {
			client.getObject(config.getBucketName(), path);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public String getPureFileName(String fullPath) {
		String baseName = FilenameUtils.getBaseName(fullPath);
		return baseName;
	}

	public String getTypePart(String fileName) {
		return FilenameUtils.getExtension(fileName);
	}

	public String trimType(String filename) {
		String pureName = getPureFileName(filename);
		return filename.substring(0,
				filename.lastIndexOf(pureName) + pureName.length());
	}

	public String changeType(String fileName, String suffix) {
		if (suffix != null) {
			fileName = trimType(fileName);
			if (suffix != "") {
				fileName += "." + suffix;
			}
		}
		return fileName;
	}

	public String getNamePart(String fileName) {
		String name = FilenameUtils.getName(fileName);
		return name;
	}

	public String getPathPart(String fileName) {
		String path = FilenameUtils.getFullPath(fileName);
		return path;
	}

}
