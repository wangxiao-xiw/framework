package com.hn658.framework.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * zip解压缩实现
 * @author davidcun
 *
 */
public class ZipCompressor extends AbstractCompressor {
	private String dir = null;
	
	@Override
	public void compress(List<File> files, File file) {
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		
		File tmpDir = file.getParentFile();
		if(!tmpDir.exists()){
			tmpDir.mkdirs();
		}
		
		try {
			fos = new FileOutputStream(file);
			zos = new ZipOutputStream(fos);
			for (File f : files) {
				InputStream inStream = new FileInputStream(f);
				String filePath = f.getAbsolutePath();
				
				filePath = (dir==null)?f.getName():filePath.substring(dir.length() + 1);
				
				ZipEntry ze = null;
				
				ze = new ZipEntry(filePath);
				ze.setSize(f.length());
				zos.putNextEntry(ze);
				byte[] bt = new byte[1024 * 2];
				int len = -1;
				while ((len = inStream.read(bt)) > -1) {
					zos.write(bt, 0, len);
				}
				inStream.close();
				zos.closeEntry();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (zos != null) {
				try {
					zos.finish();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void compress(File srcDir, File desFile) {
		List<File> files = null;
		files = getFiles(srcDir);
		if(srcDir.isDirectory()) {
			dir = srcDir.getParent();
		}
		compress(files, desFile);
	}
	
	public void compress(String srcDir, String desFilePath) {
		File f1 = new File(srcDir);
		String str = desFilePath.endsWith(".zip") ? desFilePath : (desFilePath + ".zip");
		File f2 = new File(str);
		compress(f1, f2);
	}
	
	@Override
	public void decompress(File file, File dir) {
		decompress(file.getAbsolutePath(), dir.getAbsolutePath());
	}
	
	@Override
	public List<File> decompress(File file) {
		return decompress(file.getAbsolutePath(),System.getProperty("java.io.tmpdir"));
	}
	
	public List<File> decompress(String zipFile, String desDir) {
		List<File> files  = new ArrayList<File>();
		if (desDir.endsWith(File.separator)) {
			desDir = desDir.substring(0,desDir.lastIndexOf(File.separator));
		}
		ZipInputStream zis = null;
		
		ZipEntry ze = null;
		try {
			zis = new ZipInputStream(new FileInputStream(zipFile));
			int len = -1;
			byte[] bt = new byte[1024 * 2];
			while ((ze = zis.getNextEntry()) != null) {
				File zeFile = new File(desDir+File.separator+ze.getName());
				zeFile.getParentFile().mkdirs();
				
				OutputStream os = new FileOutputStream(zeFile);
				while (zis.available() > 0) {
					len = zis.read(bt);
					if (len > -1) {
						os.write(bt, 0, len);
					}
				}
				files.add(zeFile);
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (zis!=null) {
					zis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return files;
	}
}