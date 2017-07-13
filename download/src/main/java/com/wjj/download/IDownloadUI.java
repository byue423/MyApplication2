package com.wjj.download;

import android.os.Message;

/**
 * 
 * @author wjj
 * @功能：通过handler发送来的message，更新UI界面
 */
public interface IDownloadUI {

	/**
	 * 
	 * @功 能：用toast提示下载更新的错误
	 * 
	 * @创建者：wjj 创建日期： 2014年9月23日 上午9:00:18
	 * @return：
	 */
	public void showToastError(String message);

	/**
	 * 
	 * @功 能：文本消息的提示框
	 * 
	 * @创建者：wjj 创建日期： 2014年9月15日 上午11:02:02
	 * @return：
	 */
	public void showNoticeDialog(Message msg);

	/**
	 * 
	 * @功 能：下载时显示的界面
	 * 
	 * @创建者：wjj 创建日期： 2014年9月22日 下午4:18:10
	 * @return：
	 */
	public void showDownloadDialog(Message msg);

	/**
	 * 
	 * @功 能：进度条更新的界面
	 * 
	 * @创建者：wjj 创建日期： 2014年9月15日 上午11:02:47
	 * @return：
	 */
	public void showUpdatePro(Message msg);

	/**
	 * 
	 * @功 能： 下载过程中出错后的提示界面
	 * 
	 * @创建者：wjj 创建日期： 2014年9月22日 下午4:11:19
	 * @return：
	 */
	public void showErrorDialog(Message msg);

	/**
	 * 
	 * @功 能：md5校验下载文件
	 * 
	 * @创建者：wjj 创建日期： 2014年9月15日 上午11:08:29
	 * @return：
	 */
	public void md5Calulate();

	/**
	 * 
	 * @功 能：安装apk
	 * 
	 * @创建者：wjj 创建日期： 2014年9月15日 上午11:06:12
	 * @return：
	 */
	public void installAPK(Message msg);
	
	/**
	 * 
	  * @功  能：附件或图片下载完后的处理
	  *
	  * @创建者：wjj  
	  * 创建日期： 2014年12月5日 下午4:58:18
	  * @return：
	 */
	public void downloadOver(Message msg);

}
