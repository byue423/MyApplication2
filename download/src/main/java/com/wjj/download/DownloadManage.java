package com.wjj.download;

import android.content.Context;
import android.os.Message;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.wjj.download.entity.DownloadFile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DownloadManage extends Thread {

	/* 日志类 */
	private static final Logger logger = LoggerFactory
			.getLogger(DownloadManage.class);

	private DownloadHandler dhHandler;
	private List<? extends DownloadFile> files;
	private Context context;
	private int downloadType;

	public DownloadManage(DownloadHandler dhHandler,
			List<? extends DownloadFile> files, Context context,
			int downloadType) {
		this.dhHandler = dhHandler;
		this.files = files;
		this.context = context;
		this.downloadType = downloadType;
	}

	@Override
	public void run() {
		for (int i = 0; i < files.size(); i++) {
			HttpURLConnection con = null;
			DownloadFile dFile = files.get(i);
			int length = 0;
			try {
				String downLoadUrl = dFile.getDownloadURL();
				if (null != downLoadUrl && !"".equals(downLoadUrl)) {
					logger.debug("downLoadUrl:" + downLoadUrl);
					URL url = new URL(downLoadUrl);
					con = (HttpURLConnection) url.openConnection();
					// 设置连接超时和读超时
					con.setConnectTimeout(DownloadConfig.CONNECTTIMEOUT);
					con.setReadTimeout(DownloadConfig.READTIMEOUT);
					length = con.getContentLength();
					// 设置连接超时和读超时
					//length = 47236526;
					logger.info("length:" + length);
					new DownloadThread(dhHandler, files.get(i), length,
							context, downloadType).start();
				} else {

				}
			} catch (MalformedURLException e) {
				// e.printStackTrace();
				updateError("下载路径不对，请检查下载路径后，重新下载!");
			} catch (IOException e) {
				// e.printStackTrace();
				updateError("连接服务器失败，请重新下载!");
			}
		}
	}

	private void updateError(String error) {
		if (!"".equals(downloadType)) {
			if (downloadType == DownloadConfig.DOWNLOADAPK
					|| downloadType == DownloadConfig.DOWNLOADDIALOGFILE) {
				Message msg = dhHandler.obtainMessage();
				msg.what = DownloadHandler.DOWN_ERROR;
				msg.obj = error;
				dhHandler.handleMessage(msg);
			} else {
				Message msg = dhHandler.obtainMessage();
				msg.what = DownloadHandler.SHOWERRORTOAST;
				msg.obj = error;
				dhHandler.sendMessage(msg);
			}
		}
	}
}
