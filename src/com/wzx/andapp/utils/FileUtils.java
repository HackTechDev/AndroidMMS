package com.wzx.andapp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;
import android.os.StatFs;

public class FileUtils {
	private String SDPATH;

	public String getSDPATH() {
		return SDPATH;
	}

	public FileUtils() {
		// 得到当前外部存储设备的目录/SDCARD
		SDPATH = Environment.getExternalStorageDirectory() + File.separator;
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @throws IOException
	 */
	public File creatSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 */
	public File creatSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 */
	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 */
	public File write2SDFromInput(String path, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			creatSDDir(path);
			file = creatSDFile(path + fileName);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[10 * 1024];
			int rec = 0;
			while ((rec = input.read(buffer)) != -1) {
				output.write(buffer,0,rec);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	// SDcard 操作
	public void SDCardTest() {
		// 获取扩展SD卡设备状态
		String sDStateString = Environment.getExternalStorageState();

		// 拥有可读可写权限
		if (sDStateString.equals(Environment.MEDIA_MOUNTED)) {
			try {
				// 获取扩展存储设备的文件目录
				File SDFile = Environment.getExternalStorageDirectory();
				// 打开文件
				File myFile = new File(SDFile.getAbsolutePath()
						+ File.separator + "MyFile.txt");
				// 判断是否存在,不存在则创建
				if (!myFile.exists()) {
					myFile.createNewFile();
				}
				// 写数据
				String szOutText = "Hello, World!";
				FileOutputStream outputStream = new FileOutputStream(myFile);
				outputStream.write(szOutText.getBytes());
				outputStream.close();
			} catch (Exception e) {
				// TODO: handle exception
			}// end of try

		}// end of if(MEDIA_MOUNTED)
			// 拥有只读权限
		else if (sDStateString.endsWith(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			// 获取扩展存储设备的文件目录
			File SDFile = Environment.getExternalStorageDirectory();

			// 创建一个文件
			File myFile = new File(SDFile.getAbsolutePath() + File.separator
					+ "MyFile.txt");

			// 判断文件是否存在
			if (myFile.exists()) {
				try {
					// 读数据
					FileInputStream inputStream = new FileInputStream(myFile);
					byte[] buffer = new byte[1024];
					inputStream.read(buffer);
					inputStream.close();
				} catch (Exception e) {
					// TODO: handle exception
				}// end of try
			}// end of if(myFile)
		}// end of if(MEDIA_MOUNTED_READ_ONLY)
	}// end of func

	public void SDCardSizeTest() {

		// 取得SDCard当前的状态
		String sDcString = Environment.getExternalStorageState();

		if (sDcString.equals(Environment.MEDIA_MOUNTED)) {
			// 取得sdcard文件路径
			File pathFile = Environment.getExternalStorageDirectory();
			StatFs statfs = new StatFs(pathFile.getPath());
			// 获取SDCard上BLOCK总数
			long nTotalBlocks = statfs.getBlockCount();

			// 获取SDCard上每个block的SIZE
			long nBlocSize = statfs.getBlockSize();

			// 获取可供程序使用的Block的数量
			long nAvailaBlock = statfs.getAvailableBlocks();

			// 获取剩下的所有Block的数量(包括预留的一般程序无法使用的块)
			long nFreeBlock = statfs.getFreeBlocks();

			// 计算SDCard 总容量大小MB
			long nSDTotalSize = nTotalBlocks * nBlocSize / 1024 / 1024;

			// 计算 SDCard 剩余大小MB
			long nSDFreeSize = nAvailaBlock * nBlocSize / 1024 / 1024;
		}// end of if
	}// end of func
}