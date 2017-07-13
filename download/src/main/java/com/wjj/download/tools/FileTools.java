package com.wjj.download.tools;

import android.os.Environment;

import com.wjj.download.DownloadConfig;

import java.io.File;

/**
 * 文件工具类,主要负责SDCard的检查和目录的创建
 *
 * @author yc
 * @date 2013年8月23日
 *
 */
public class FileTools {
	private static String fileName = "";
	private static String basePath = DownloadConfig.BASEPATH;
	private static String downDir = DownloadConfig.DOWNDIR;

	/**
	 * 检查SDCard的存在性
	 *
	 * @author yc
	 * @date 2013年8月23日
	 *
	 * @return
	 */
	public static boolean isHaveSDCard() {
		return Environment.getExternalStorageDirectory().exists();
	}

	/**
	 *
	 * @功 能：检查指定的目录是否存在,不存在则创建
	 *
	 * @创建者：wjj 创建日期： 2014年12月10日 下午4:26:44
	 * @return：
	 */
	private static synchronized void checkBasePath() {
		//Log.d("FileTools", "----------------------downDir:" + downDir);
		String SDCardRoot = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		File path = new File(SDCardRoot + File.separator + downDir
				+ File.separator);
		if (!path.exists() || !path.isDirectory()) {
			path.mkdirs();
		}
		basePath = path + File.separator;
		//Log.d("FileTools", "----------------------basePath:" + basePath);
	}

	/**
	 *
	 * @功 能：获得存放下载文件的目录
	 *
	 * @创建者：wjj 创建日期： 2014年12月10日 下午4:26:31
	 * @return：
	 */
	public static String getBasePath() {
		checkBasePath();
		return basePath;
	}

	/**
	 * 根据URL地址获取文件名
	 *
	 * @author yc
	 * @date 2013年9月1日
	 *
	 * @param url
	 *            要分析的URL
	 * @return 如果有则返回文件名,没有则原样返回
	 */
	public static String getFileName(String url) {
		fileName = url;
		fileName = fileName.replaceAll("\\\\", File.separator);
		if (fileName.lastIndexOf(File.separator) > 0) {
			fileName = fileName.replaceAll("/", File.separator);
			fileName = fileName
					.substring(fileName.lastIndexOf(File.separator) + 1);
		}
		return fileName;
	}

	/**
	 *
	 * @功 能：文件重命名，在原文件名后加（i）
	 *
	 * @创建者：wjj 创建日期： 2014年9月19日 下午4:25:34
	 * @param:fileName 必须有后缀名的文件名
	 * @return：
	 */
	public static String fileRename(String fileName) {
		int i = 0;
		while ((new File(fileName)).exists()) {
			i++;
			if (-1 != fileName.lastIndexOf("(")) {
				fileName = fileName.substring(0, fileName.lastIndexOf("("))
						+ "(" + i + ")"
						+ fileName.substring(fileName.lastIndexOf("."));
			} else {
				fileName = fileName.substring(0, fileName.lastIndexOf("."))
						+ "(" + i + ")"
						+ fileName.substring(fileName.lastIndexOf("."));
			}

		}
		return fileName;
	}
}