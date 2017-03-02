package com.hn658.framework.compress;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

/**
 * 
 * @author davidcun
 *
 */
public class GZipObjectInputStream {
	// 对象输入流
	private ObjectInputStream objectInputStream;
	
	public GZipObjectInputStream(InputStream in) throws IOException {
		objectInputStream = new ObjectInputStream(new GZIPInputStream(in));
	}
	
	public Object readObject() throws IOException, ClassNotFoundException {
		return objectInputStream.readObject();
	}
	
	public int read() throws IOException {
		return objectInputStream.read();
	}
	
	public int read(byte[] bt) throws IOException {
		return objectInputStream.read(bt);
	}
	
	public int read(byte[] buf, int off, int len) throws IOException {
		return objectInputStream.read(buf, off, len);
	}
}
