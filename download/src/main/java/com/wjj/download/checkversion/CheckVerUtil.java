package com.wjj.download.checkversion;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Message;

import com.wjj.download.DownloadHandler;
import com.wjj.download.entity.Version;
import com.wjj.download.entity.VersionMsg;
import com.wjj.download.tools.CheckNetwork;

public class CheckVerUtil{

	private static final String tag = "CheckVerThread";

	private Context context;
	private DownloadHandler dHandler;
	private Version ver = null;

	public CheckVerUtil(Context context, DownloadHandler dHandler) {
		this.context = context;
		this.dHandler = dHandler;
	}

	public CheckVerUtil(Context context, DownloadHandler dHandler, Version  ver) {
		this.context = context;
		this.dHandler = dHandler;
		this.ver = ver;

		init();
	}

	public void init() {
		// sendUpdate();

		boolean isConnect = false;
		boolean isDownload = false;

		// 检查网络是否连接
		isConnect = new CheckNetwork().isHttpTest(context);
		if (isConnect) {
			if (isDownload()) {
				sendUpdate();
			} else {
				sendError();
			}
		}
	}

	/**
	 * 
	 * @功 能：是否需要下载
	 * 
	 * @创建者：wjj 创建日期： 2014年9月22日 下午6:24:02
	 * @return：
	 */
	@SuppressWarnings("unused")
	private boolean isDownload() {
		// test
		// ver = getVerInfo();
		// return true;
		boolean flag = false;
		int curVerCode = -1;
		int remoteVerCode = 0;
		curVerCode = getCurrentVer();
	//	ver = getVerInfo();

		if (null == ver) {
			ver = new Version();
			ver.setError("调用webservice接口时出现错误!");
		} else if (null != ver && curVerCode == -1) {
			ver.setError("获得当前应用版本信息出错!");
		} else if (null != ver && ver.isSucc() && null != ver.getVerCode()
				&& !"".equals(ver.getVerCode())) {
			remoteVerCode = Integer.parseInt(ver.getVerCode());
			if (remoteVerCode > curVerCode) {
				if (null != ver.getMd5Str() && !"".equals(ver.getMd5Str())) {
					VersionMsg.md5check = ver.getMd5Str();
				}
				if (null != ver.getIsForce() && !"".equals(ver.getIsForce())) {
					VersionMsg.isForceUpdate = ver.getIsForce();
				}
				flag = true;
			}
		} else {

		}
		return flag;
	}

	/**
	 * 
	 * @功 能：通过接口获取服务器传回来的版本信息
	 * 
	 * @创建者：wjj 创建日期： 2014年9月23日 下午4:40:58
	 * @return：
	 */
	private Version getVerInfo() {
		Version ver = new Version();
		// WebServiceUtil wsu = new WebServiceUtil();
		// InputStream is = null;
		// ParseVersionXml parser = new ParseVersionXml();
		// try {
		// is = wsu.invoke(WebServiceConfig.versionService,
		// ConstantConfig.VERSION_APK_VERSION,
		// new String[] { "checkVersion" });
		// ver = parser.parseXml(is);
		// } catch (Exception e) {
		// ver = null;
		// e.printStackTrace();
		// }
		return ver;
	}

	/**
	 * 
	 * @功 能：获取客户端现在的版本号
	 * 
	 * @创建者：wjj 创建日期： 2014年9月22日 下午6:25:22
	 * @return：
	 */
	public int getCurrentVer() {
		int verCode = 0;
		PackageInfo pkInfo = null;
		PackageManager pkMgr = context.getPackageManager();
		try {
			pkInfo = pkMgr.getPackageInfo(context.getPackageName(), 0);
			verCode = pkInfo.versionCode;
		} catch (NameNotFoundException e) {
			//Log.d(tag, "------ get current version message failed -----");
			e.printStackTrace();
		}
		return verCode;
	}

	public void sendUpdate() {
		Message msg = dHandler.obtainMessage();
		msg.what = DownloadHandler.SHOW_NOTICEDIALOG;
		// 版本更新内容
		msg.obj = VersionMsg.updateMsg;
		// msg.obj = ver.getVerInfo();
		dHandler.sendMessage(msg);
	}

	public void sendError() {
		Message msg = dHandler.obtainMessage();
		msg.what = DownloadHandler.DOWN_ERROR;
		msg.obj = ver.getError();
		dHandler.sendMessage(msg);
	}
}
