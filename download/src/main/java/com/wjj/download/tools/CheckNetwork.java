package com.wjj.download.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.wjj.download.DownloadConfig;

/**
 * 
 * @author wjj
 * @功能：检查网络连接的工具类
 */
public class CheckNetwork {

	private static final String tag = "CheckNetwork";

	/**
	 * 检测网络是否存在
	 */
	public static boolean isHttpTest(final Context context) {
		if (!isNetworkAvailable(context)) {
			Toast.makeText(context, DownloadConfig.ISNOTWIFI,
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断网络连接方法
	 */
	public static boolean isNetworkAvailable(Context context) {
		//Log.d(tag, "---------------进入网络连接判断方法------------------");
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						//Log.d(tag, "-----------------网络连接成功------------------");
						return true;
					}
				}
			}
		}
		return false;
	}
}
