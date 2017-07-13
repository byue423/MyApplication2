package com.wjj.download;

import android.content.Context;
import android.os.Message;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.wjj.download.db.DownloadDao;
import com.wjj.download.entity.DownloadFile;
import com.wjj.download.entity.ProgressMsg;
import com.wjj.download.entity.VersionMsg;
import com.wjj.download.tools.CalculateMD5Util;
import com.wjj.download.tools.FileTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * @author wjj
 * @功能：单线程的断点续传
 */
public class DownloadThread extends Thread {
	/* 日志类 */
	private static final Logger logger = LoggerFactory
			.getLogger(DownloadThread.class);
	private static final int BUFFER_SIZE = 4096;
	private DownloadFile dFile;
	private DownloadHandler handler;
	private Context context;// contentprovider使用的
	private int downloadType;

	private int endPosition = 0;
	private int curPosition = 0;

	InputStream is = null;
	// BufferedInputStream bis = null;
	RandomAccessFile fos = null;
	HttpURLConnection con = null;
	private int fileStatus = 0;
	private boolean ishas;
	private DownloadDao dao;
	private String fileName = "";// 下载读写的文件名，存放到手机端
	private String downFileName = "";// 未下载完的文件名称
	private boolean retryFlag = false; // 读取抛出IOException异常时使用
	private int retryNum = 0;// 统计读取抛出ioException异常时，重新连接的次数

	public DownloadThread(DownloadHandler handler, DownloadFile dFile,
			int endPosition, Context context, int downloadType) {
		this.handler = handler;
		this.dFile = dFile;
		this.endPosition = endPosition;
		this.context = context;
		this.downloadType = downloadType;
	}

	@Override
	public void run() {

		logger.debug("----------查看手机是否已有该文件名-------");
		dao = DownloadDao.getInstance(context);
		ishas = dao.isHas(dFile.getDownloadURL());
		// 获取断点下载的位置，下载文件的大小（即文件断点下载的结束位置）
		if (ishas) {
			logger.debug("该文件在数据库t_download表中是否存在：" + ishas);
			curPosition = 0;
		} else {
			curPosition = dao.getInfo(dFile.getDownloadURL()).getCurPosition();
			endPosition = dao.getInfo(dFile.getDownloadURL()).getFileSize();
			downFileName = dao.getInfo(dFile.getDownloadURL()).getFileName();// 未下载完的文件名
		}
		// 查看手机是否有sd卡，是否已经有该文件
		if (FileTools.isHaveSDCard()) {
			fileName = getFileName();
			File file = new File(fileName);
			if (!file.exists()) {
				try {
					file.createNewFile();
					// 避免用户把本地未下载完的文件删除了
					curPosition = 0;
				} catch (IOException e) {
					// updateError("SDCard无法创建文件!");
					logger.debug("------file is exists:" + fileName);
					if (file.exists()) {
						file.delete();
						file = new File(fileName);
						curPosition = 0;
					}
				}
			}
			logger.debug("----------------fileName:" + fileName);
			urlConnection(file);
			// downloadFile(file, "");
		} else {
			updateError("设备不存在SDCard,请插入SDCard后再重试!");
		}
	}

	/**
	 * 
	 * @功 能：建立HttpUrlContection连接
	 * 
	 * @创建者：wjj 创建日期： 2015年1月16日 上午8:45:55
	 * @return：
	 */
	private void urlConnection(File file) {
		String downLoadUrl = "";
		try {
			downLoadUrl = dFile.getDownloadURL();
			logger.info("-------------downLoadUrl:" + downLoadUrl);
			URL url = new URL(downLoadUrl);
			con = (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			// e.printStackTrace();
			// retryThree(file, "无法连接到服务器,请稍后重试!");
			updateError("无法连接到服务器,请稍后重试!");
		} catch (IOException e) {
			// e.printStackTrace();
			// retryThree(file, "连接服务器失败,网络问题,请重试!");
			updateError("连接服务器失败,网络问题,请重试!");
		}
		// 设置连接超时和读超时
		con.setConnectTimeout(DownloadConfig.CONNECTTIMEOUT);
		con.setReadTimeout(DownloadConfig.READTIMEOUT);
		con.setAllowUserInteraction(true);
		logger.info(downLoadUrl + "---文件当前下载的位置:" + curPosition);
		con.setRequestProperty("Range", "bytes=" + curPosition + "-"
				+ endPosition);
		
		try {
			// 连接服务器
			con.connect();
			// 获取连接返回的消息头
			int code = con.getResponseCode();
			logger.info("连接服务器返回的 code:" + code);
			if (404 == code) {
				// retryThree(file, "在服务器端找不到该文件,请稍后重试!");
				updateError("在服务器端找不到该文件,请稍后重试!");
			}
			if (206 != code) {
			    // retryThree(file, "无法与服务器正常连接并获取数据,请稍后重试!");
				updateError("无法与服务器正常连接并获取数据,请稍后重试!");
			}
			if (206 == code) {
				// retryFlag = false;
				downloadFile(file, downLoadUrl);
			}
		} catch (IOException e) {
			retryThree(file, "连接服务器失败,请稍后重试!");
			//updateError("连接服务器失败,请稍后重试!");
		}
	}
	
	/**
	 * 
	 * @功 能：子下载线程，支持单线程的断点续传，不支持一个文件多线程的断点下载
	 * 
	 * @创建者：wjj 创建日期： 2014年9月19日 下午4:14:26
	 * @return：
	 */
	private void downloadFile(File file, String downLoadUrl) {
		logger.debug("------启动下载文件的线程------");
		byte[] buf = new byte[BUFFER_SIZE];
		try {
			fos = new RandomAccessFile(file, "rw");
			fos.seek(curPosition);
			// logger.debug("----------fos:" + fos);
			// 通过con获取下载的数据流
			is = con.getInputStream();
			// logger.debug("----------is:" + is);
			// bis = new BufferedInputStream(is);
			// logger.debug("----------bis:" + bis);
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			updateError("本地文件已删除,请重新下载!");
		} catch (SocketTimeoutException s) {
			// s.printStackTrace();
			updateError("连接服务器超时,网络问题,请重试!");
		} catch (IOException e) {
			// e.printStackTrace();
			updateError("网路问题,请重试!");
		}

		// 下载文件
		int speed = 0;
		while (curPosition < endPosition) {
			// logger.debug("file write curPosition:" + curPosition);
			if (1 == fileStatus) {
				// 取消下载后，终止线程
				break;
			}
			if (is == null) {
				// retryThree(file, "网络传送数据时出错，请重试!");
				updateError("网络传送数据时出错，请重试!");
				break;
			}
			/*
			 * if (bis == null){ // 服务器端正在努力下载该文件,请稍后重试!
			 * updateError("网络传送数据时出错，请重试!"); //
			 * updateError("文件保存目录路径不存在,请重新下载!"); break; }
			 */
			int len = 0;
			try {
				len = is.read(buf, 0, BUFFER_SIZE);
				// len = bis.read(buf, 0, BUFFER_SIZE);
				// 下载的附件加密
				// buf = encrypt(buf);
				// buf = new EncryptUtil(LoopNumber.ONE).encrypt(buf);
				if (len == -1) {
					break;
				}
				fos.write(buf, 0, len);
				/*只有在读写都正常的情况下，重试次数才归0*/
				if(retryFlag){
					retryFlag = false;
					retryNum = 0;
				}
				curPosition += len;
				speed = (Double
						.valueOf((curPosition * 1.0 / endPosition * 100)))
						.intValue();
				// Log.d("DownloadThread", "下载的curPosition:" + curPosition
				// + ",文件大小:" + endPosition + ",进度百分比：" + speed);
				// 通知handler去更新视图组件
				sendUpdatePro(speed);

				// 记录文件读取的位置
				if (dao.isHas(dFile.getDownloadURL())) {
					dao.insert(curPosition, endPosition, downLoadUrl, fileName);
				} else {
					fileStatus = dao.getInfo(dFile.getDownloadURL())
							.getFileStatus();
					dao.update(curPosition, dFile.getDownloadURL());
				}
			} catch (IOException e) {
				// e.printStackTrace();
				// 如果抛出异常，重试三次连接
				retryFlag = true;
				retryThree(file, "网络原因，无法下载，请稍后重试!");
				break;
			}
		}
		// 下载完成后
		closeIO();
		if (1 == fileStatus) {
			// 取消下载后，把下载的文件状态该为可再次下载
			dao.updateFileStatus(dFile.getDownloadURL(), 0);
		} else {
			// 删除表中该线程的信息
			if (curPosition >= endPosition) {
				dao.delete(dFile.getDownloadURL());
				// 下载更新完
				md5Calulate();
			}
		}
	}

	private void retryThree(File file, String error){
		// && retryFlag
		if (retryNum < 3) {
			closeIO();
			retryNum +=1;
			urlConnection(file);
		} else {
			retryFlag = false;
			retryNum = 0;
			updateError(error);
		}
	}

	/**
	 * 
	 * @功 能：获取下载文件名
	 * 
	 * @创建者：wjj 创建日期： 2014年9月19日 下午4:13:30
	 * @return：
	 */
	private String getFileName() {

		String newFileName = "";
		String saveName = dFile.getSaveName();
		logger.debug("getFileName saveName:" + saveName);
		String basePath = FileTools.getBasePath();
		if (saveName == null || saveName.equals("")) {
			String fileName = FileTools.getFileName(dFile.getDownloadURL());
			if (ishas) {
				newFileName = FileTools.fileRename(basePath + fileName);
			} else {
				newFileName = downFileName;
			}
		} else {
			if (ishas) {
				newFileName = FileTools.fileRename(saveName);
			} else {
				newFileName = downFileName;
			}
		}
		logger.debug("getFileName newFileName:" + newFileName);
		return newFileName;
	}

	/**
	 * @功 能：更新进度条
	 * 
	 * @创建者：wjj 创建日期： 2014年9月19日 下午4:10:43
	 * @return：
	 */
	private void sendUpdatePro(int speed) {
		Message msg = handler.obtainMessage();
		msg.what = DownloadHandler.DOWN_UPDATE;
		ProgressMsg pMsg = new ProgressMsg();
		pMsg.setProId(dFile.getProId());
		pMsg.setTextId(dFile.getTextId());
		pMsg.setBtnId(dFile.getBtnId());
		pMsg.setUrlStr(dFile.getDownloadURL());
		pMsg.setProgress(speed);
		pMsg.setCurPosition(curPosition);
		pMsg.setFileSize(endPosition);
		msg.obj = pMsg;
		handler.handleMessage(msg);
	}

	/**
	 * 
	 * @功 能：下载出错，弹出错误提示窗口或信息，重新下载
	 * 
	 * @创建者：wjj 创建日期： 2014年9月19日 下午4:12:05
	 * @return：
	 */
	private void updateError(String error) {
		closeIO();
		if (!"".equals(downloadType)) {
			if (downloadType == DownloadConfig.DOWNLOADAPK
					|| downloadType == DownloadConfig.DOWNLOADDIALOGFILE) {
				Message msg = handler.obtainMessage();
				msg.what = DownloadHandler.DOWN_ERROR;
				msg.obj = error;
				handler.handleMessage(msg);
			} else {
				Message msg = handler.obtainMessage();
				msg.what = DownloadHandler.DOWN_ERROR;
				msg.arg1 = dFile.getTextId();
				msg.arg2 = downloadType;
				msg.obj = error;
				handler.sendMessage(msg);
			}
		}
	}

	/**
	 * 
	 * @功 能：关闭流
	 * 
	 * @创建者：wjj 创建日期： 2014年9月19日 下午4:12:55
	 * @return：
	 */
	private void closeIO() {
		try {
			if (con != null) {
				con.disconnect();
			}
			if (null != is) {
				is.close();
			}
			// if (null != bis) {
			// bis.close();
			// }
			if (null != fos) {
				fos.close();
			}
		} catch (IOException e) {
			// logger.info("关闭流失败");
			e.printStackTrace();
		}
	}

	public void md5Calulate() {

		if (!"".equals(downloadType)) {
			if (downloadType == DownloadConfig.DOWNLOADAPK) {
				// 弹出md5校验apk的提示
				final String filePath = fileName;
				logger.debug("filePath:" + filePath);
				// Toast.makeText(context, "xxx:"+fileName,
				// Toast.LENGTH_LONG).show();
				if (DownloadConfig.ISVALIDATEAPK){
					Message msg = handler.obtainMessage();
					msg.what = DownloadHandler.DM5CALCUATESUCC;
					msg.obj = fileName;
					handler.sendMessage(msg);
				} else {
					try {
						String md5Str = CalculateMD5Util.getFileMD5String(filePath);
						if (null != md5Str && !"".equals(md5Str)
								&& md5Str.length() > 0) {
							logger.info("对下载后的更新包校验后的md:" + md5Str);
							logger.info("客户端中传回来的md5：" + VersionMsg.md5check);
							if (md5Str.equals(VersionMsg.md5check)) {
								Message msg = handler.obtainMessage();
								msg.what = DownloadHandler.DM5CALCUATESUCC;
								msg.obj = fileName;
								handler.sendMessage(msg);
							} else {
								updateError("下载的安装包不完整,请重新下载！");
							}
						}
					} catch (Exception e) {
						updateError("下载的安装包不完整,请重新下载！");
					}
				}
			} else if (downloadType == DownloadConfig.DOWNLOADDIALOGFILE
					|| downloadType == DownloadConfig.DOWNLOADCUSTOMFILES) {
				// 附件下载完暂时不做处理
				Message msg = handler.obtainMessage();
				msg.what = DownloadHandler.DOWN_OVER;
				msg.arg1 = dFile.getBtnId();
				msg.obj = fileName;
				handler.sendMessage(msg);
				// Message msg = handler.obtainMessage();
				// msg.what = DownloadHandler.SHOWERRORTOAST;
				// msg.obj = DownloadConfig.DOWNOVER;
				// handler.sendMessage(msg);
			} else if (downloadType == DownloadConfig.DOWNLOADCUSTOMIMAGES) {
				// 图片下载完暂时不做处理
			}
		}
	}

	/**
	 * @功 能：对文档做相应的加密算法
	 * 
	 * @创建者：wjj 创建日期： 2014年12月23日 上午9:34:32
	 * @return：
	 */
	/*
	 * private byte[] encrypt(byte[] buf){ return new
	 * EncryptUtil(LoopNumber.ONE).encrypt(buf); String lower =
	 * fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	 * System.out.println("--------------lower:"+lower); if(".doc".equals(lower)
	 * || ".docx".equals(lower) || ".txt".equals(lower) || ".xls".equals(lower)
	 * || ".xlsx".equals(lower) || ".ppt".equals(lower) || ".pdf".equals(lower)
	 * || ".pptx".equals(lower)){ return new
	 * EncryptUtil(LoopNumber.ONE).encrypt(buf); } return buf; }
	 */
}
