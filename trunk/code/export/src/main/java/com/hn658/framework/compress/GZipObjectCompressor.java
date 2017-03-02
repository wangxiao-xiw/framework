package com.hn658.framework.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Gzip压缩算法
 * @author davidcun
 *
 */
public class GZipObjectCompressor {

	/**
	 * 通过GZIP算法把一个对象压缩并序列化到指定的输出流
	 * @param object
	 * @param outputStream
	 */
	public void compressObject(Object object, OutputStream outputStream){
		try {
			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
			objectOutputStream.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过GZIP算法把输入流中的对象解压并反序列化为对象
	 * @param inputStream
	 * @return
	 */
	
	public Object decompressObject(InputStream inputStream){
		
		Object object=null;
		try {
			GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
			
			ObjectInputStream objectInputStream = new ObjectInputStream(gzipInputStream);
			
			object = objectInputStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * 压缩后的字节数组可以用来序列化到文件也可以用来在网络中传输
	 * @param obj
	 * @return
	 */
	public byte[] compressObjectToByte(Object obj) {
		byte[] bt = null;
		
		try {
			ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
			
			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
			
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
			
			objectOutputStream.writeObject(obj);
			bt = byteArrayOutputStream.toByteArray();
			
			objectOutputStream.close();
			gzipOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bt;
	}
	
	public Object decompressByteToObject(byte[] date) {
		Object object = null;
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(date);
			GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
			
			ObjectInputStream objectInputStream = new ObjectInputStream(gzipInputStream);
			
			object = objectInputStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}
}
