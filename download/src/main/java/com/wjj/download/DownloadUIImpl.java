package com.wjj.download;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.hnpolice.xiaoke.downloadfile.R;
import com.wjj.download.checkversion.CheckVerThread;
import com.wjj.download.db.DownloadDao;
import com.wjj.download.entity.DownloadFile;
import com.wjj.download.entity.ProgressMsg;
import com.wjj.download.entity.VersionMsg;

import java.io.File;
import java.util.List;

public class DownloadUIImpl implements IDownloadUI {

	/* 日志类 */
	private static final Logger logger = LoggerFactory
			.getLogger(DownloadUIImpl.class);
	UseActivity useActivity;
	private Context context;
	private DownloadHandler dHandler;
	private List<DownloadFile> dList;
	private int downloadType;

	private Object isForceUpdate = VersionMsg.isForceUpdate;
	private ProgressBar mProgress;
	private AlertDialog downloadDialog;

	private Handler handler = new Handler();

	public static boolean isFinish = false;

	public void setUseActivity(UseActivity useActivity) {
		this.useActivity = useActivity;
	}

	public DownloadUIImpl(Context context, List<DownloadFile> dList,
			int downloadType) {
		this.context = context;
		this.dList = dList;
		this.downloadType = downloadType;
	}

	public void setHandler(DownloadHandler dHandler) {
		this.dHandler = dHandler;
	}

	public void start() {
		System.out.println("downloadType:" + downloadType);
		if (DownloadConfig.DOWNLOADAPK == downloadType) {
			// 启动一下检查下载更新版本的线程
			// wxpt忽略版本检查
			new CheckVerThread(context, dHandler).start();
		} else if (DownloadConfig.DOWNLOADDIALOGFILE == downloadType) {
			Message msg = dHandler.obtainMessage();
			msg.obj = "正在努力下载中，请您耐心等待...";
			showDownloadDialog(msg);
		} else if (DownloadConfig.DOWNLOADCUSTOMFILES == downloadType) {
			// 下载开始
			new DownloadManage(dHandler, dList, context, downloadType).start();
		}
	}

	public void showToastError(String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public void showNoticeDialog(Message msg) {
		if (null != downloadDialog) {
			downloadDialog.dismiss();
		}
		String message = (String) msg.obj;
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("软件版本更新");
		if (message != null && !"".equals(message)) {
			builder.setMessage(message);
		} else {
			builder.setMessage("有最新的软件包哦，亲快下载吧~");
		}
		builder.setCancelable(false);
		builder.setPositiveButton("立即更新", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//
				Message mes = dHandler.obtainMessage();
				mes.obj = "正在努力下载中，请您耐心等待...";
				showDownloadDialog(mes);
			}
		});
		if ("force".equals(isForceUpdate)) {
			builder.setNegativeButton("退出", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					((Activity) context).finish();
				}
			});
		} else {
			builder.setNegativeButton("稍后再说", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//DownloadHandler.DOWN_QUXIAO=true;
					dialog.dismiss();
					//调用接口方法
					useActivity.userAc();
				}
			});
		}
		builder.create();
		builder.show();
	}

	public void showDownloadDialog(Message msg) {
		String message = (String) msg.obj;
		logger.debug("----------showDownloadDialog:" + message);
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(message);
		final LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.progress);
		builder.setView(v);
		builder.setCancelable(false);
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				DownloadDao dao = DownloadDao.getInstance(context);
				dao.updateAllStatus(1);
				((Activity) context).finish();
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();
		// 进度条的id集合
		// List<Integer> proIdList = new ArrayList<Integer>();
		// proIdList.add(R.id.progress);
		// 下载开始
		new DownloadManage(dHandler, dList, context, downloadType).start();
	}

	public void showUpdatePro(Message msg) {
		ProgressMsg pro = (ProgressMsg) msg.obj;
		//int proId = pro.getProId();
		//int textId = pro.getTextId();
		int progress = pro.getProgress();
		// String urlStr = pro.getUrlStr();
		long downloadedSize = pro.getCurPosition();
		long fileSize = pro.getFileSize();
		if (mProgress != null) {
			mProgress.setProgress(progress);
		}
		if (downloadedSize >= fileSize) {
			if (null != downloadDialog) {
				downloadDialog.dismiss();
			}
		}
	}

	public void showErrorDialog(Message msg) {
		// showNoticeDialog(msg);
		showError(msg);
	}

	public void md5Calulate() {
		if (null != downloadDialog) {
			downloadDialog.dismiss();
		}
	}

	public void installAPK(Message msg) {
		if (null != downloadDialog) {
			downloadDialog.dismiss();
		}
		// Toast.makeText(context, "校验完成，开始安装...", Toast.LENGTH_SHORT)
		// .show();
		String filePath = (String) msg.obj;
		File apkfile = new File(filePath);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 防止应用不显示‘完成’和‘打开’界面
		context.startActivity(i);
	}

	private void showError(Message msg) {
		if (null != downloadDialog) {
			downloadDialog.dismiss();
		}
		String message = (String) msg.obj;
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("软件版本更新");
		if (message != null && !"".equals(message)) {
			builder.setMessage(message);
		} else {
			builder.setMessage("有最新的软件包哦，亲快下载吧~");
		}
		builder.setCancelable(false);
		builder.setPositiveButton("立即更新", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//
				handler.post(new Runnable() {
					@Override
					public void run() {
						Message mes = dHandler.obtainMessage();
						mes.obj = "正在努力下载中，请您耐心等待...";
						showDownloadDialog(mes);
					}
				});
			}
		});
		if ("force".equals(isForceUpdate)) {
			builder.setNegativeButton("退出", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					((Activity) context).finish();
				}
			});
		} else {
			builder.setNegativeButton("稍后再说", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		}
		builder.create();
		builder.show();
	}

	@Override
	public void downloadOver(Message msg) {
		isFinish = true;
	}
	
	/**
	 * 提供接口用来在p01_LoginActivity实现判断自登陆
	 */
	public interface UseActivity{
		public void userAc();
	}
}
