package com.hn658.framework.compress;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 
 * @author davidcun
 *
 */
public class GZipObjectOutputStream {
	// 对象输出流
	private ObjectOutputStream objectOutputStream;
	
	/**
	 * 构造方法，用于根据输出流初始化对象输出流
	 * @param out
	 * @throws IOException
	 */
	public GZipObjectOutputStream(OutputStream out) throws IOException {
		objectOutputStream = new ObjectOutputStream(new GZIPOutputStream(out));
	}
	
	public void writeObject(Object obj) throws IOException {
		objectOutputStream.writeObject(obj);
	}
	
	public void write(byte[] buf) throws IOException {
		objectOutputStream.write(buf);
	}
	
	public void write(byte[] buf, int off, int len) throws IOException {
		objectOutputStream.write(buf, off, len);
	}
}
