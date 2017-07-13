/************************* 版权声明 *********************************
 * 
 * @版权所有：太原优联科技有限公司
 * 
 ************************* 项目信息 *********************************
 *
 * @所属项目：wxpt_android
 *
 ************************* 变更记录 *********************************
 *
 * @创建者：wjj   创建日期：2014年9月16日 上午10:54:39
 * @创建记录：创建类结构。
 * 
 * @修改者：       修改日期：
 * @修改记录：
 *
 ************************* To  Do *********************************
 *
 * @
 * 
 ************************* 随   笔 *********************************
 *
 * @
 * 
 ******************************************************************
 */
package com.wjj.download.tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.wjj.download.DownloadConfig;

import java.io.File;

/**
 * @author wjj
 * @功能：把应用交给系统进行安装
 */
public class InstallAPK {

	/* 日志类 */
	private static final Logger logger = LoggerFactory
			.getLogger(InstallAPK.class);

	private Context context;

	public InstallAPK(Context context) {
		this.context = context;
	}

	/**
	 * 安装apk
	 */
	public void installApk() {
		logger.info("---------进入软件安装方法----------");
		File apkfile = new File(DownloadConfig.APKDOWNLOADFILENAME);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		context.startActivity(i);
	}
}
