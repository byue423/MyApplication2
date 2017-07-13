package com.wjj.download;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 
 * @author wjj
 * @功能：发送消息，更新UI
 */
public class DownloadHandler extends Handler {

	// Toast提示错误信息
	public static final int SHOWERRORTOAST = -1;
	// 弹出提示框：只文本显示
	public static final int SHOW_NOTICEDIALOG = 0;
	// 下载更新进度条
	public static final int DOWN_UPDATE = 1;
	// 下载出错
	public static final int DOWN_ERROR = 2;
	// 下载完成
	public static final int DOWN_OVER = 3;
	// 对下载的文件校验md5值，校验结果为一致
	public static final int DM5CALCUATESUCC = 4;
	// 下载时显示的界面
	public static final int DOWN_LOADING = 5;

	public IDownloadUI ui = null;

	public DownloadHandler(IDownloadUI ui) {
		this.ui = ui;
	}

	public void handleMessage(Message msg) {
		switch (msg.what) {
		case SHOWERRORTOAST:
			showToastError(msg);
			break;
		case SHOW_NOTICEDIALOG:
			showNoticeDialog(msg);
			break;
		case DOWN_UPDATE:
			downloadProgress(msg);
			break;
		case DOWN_ERROR:
			downloadError(msg);
			break;
		case DOWN_OVER:
			downloadOver(msg);
			break;
		case DM5CALCUATESUCC:
			MD5CalSucc(msg);
			break;
		case DOWN_LOADING:
			downloadUI(msg);
			break;
		}
	}

	private void showToastError(Message msg) {
		String error = (String) msg.obj;
		ui.showToastError(error);
	}

	private void showNoticeDialog(Message msg) {
		ui.showNoticeDialog(msg);
	}

	private void downloadProgress(Message msg) {
		ui.showUpdatePro(msg);
	}

	private void downloadOver(Message msg) {
		// 附件或图片下载完后
		ui.downloadOver(msg);
	}

	private void downloadError(Message msg) {
		int type = msg.arg2;
		if(!"".equals(type)){
			if(type == DownloadConfig.DOWNLOADAPK
					|| type == DownloadConfig.DOWNLOADDIALOGFILE){
				 Looper.prepare();
				 ui.showNoticeDialog(msg);
				 Looper.loop();
			}else {
				 ui.showErrorDialog(msg);
			}
		}
		// ui.showErrorDialog(msg);
	}

	private void MD5CalSucc(Message msg) {
		ui.installAPK(msg);
	}
	
	private void downloadUI(Message msg){
		ui.showDownloadDialog(msg);
	}
}
