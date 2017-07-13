package com.byue.okgo.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

import com.example.okgoutils.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * @author:wjj
 * @date: 2017/6/27 11:03.
 * @description: 判断当前手机联网的类型
 */
public class NetUtil
{
	/**
	 * 检查当前手机网络
	 *
	 * @param context
	 * @return
	 */
	public static boolean checkNet(Context context)
	{
		// 判断连接方式
		boolean wifiConnected = isWIFIConnected(context);
		boolean mobileConnected = isMOBILEConnected(context);
		if (wifiConnected == false && mobileConnected == false)
		{
			// 如果都没有连接返回false，提示用户当前没有网络
			return false;
		}
		return true;
	}

	// 判断手机使用是wifi还是mobile
	/**
	 * 判断手机是否采用wifi连接
	 */
	public static boolean isWIFIConnected(Context context)
	{
		// Context.CONNECTIVITY_SERVICE).

		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null && networkInfo.isConnected())
		{
			return true;
		}
		return false;
	}

	public static boolean isMOBILEConnected(Context context)
	{
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null && networkInfo.isConnected())
		{
			return true;
		}
		return false;
	}

    /**
     * @author:wjj
     * @date: 2017/6/27 10:03.
     * @description:
     */

    public static class WebConfig {

        //基础URL
    //	public static final String BASE_URL = "http://192.168.9.51:8183/";
        public static final String VERIFY_URL = "http://123.57.136.110:8181/"; //外网认证服务器
        //	public static final String BASE_URL = "http://192.168.0.188:8081/";
        public static final String BASE_URL = "http://123.57.136.110:8180/";//公网地址
        //随机获取高清图片
        public static final String IMAGE_URL = "https://unsplash.it/600/320/?random";

        /** 登录*/
        public static final String USERLOGIN = BASE_URL + "c-mis/api/user/userLogin";

        /**
         * 处理响应结果的响应码
         */
        public static final String POST_ERROR = "服务器响应错误";
        public static final String POST_NULL_TYPE = "请添加接口调用方法类型！";
        public static final String POST_JSON_ERROR = "接口参数为空,请检查接口方法";
        public static final String NETWORK_ERROR = "网络连接错误";
        public static final String NO_MORE_DATA = "没有更多数据了";
        public static final String NO_USER = "请输入账户名";
        public static final String NO_PWD = "请输入密码";
        public static final String PWD_ERROR = "用户名密码错误";

        /**
         * 调用接口的方法类型
         */
        public static final String GET = "get";
        public static final String POST = "post";
        public static final String JSON = "json";


        /** 调用接口成功*/
        public static final int SUCESS_MSG = 0x01;
        /** 调用接口失败*/
        public static final int FAIL_MSG = 0x02;
        /** 调用接口是出现错误 */
        public static final int ERROR_MSG = 0x03;
        /** 登录 */
        public static final int GET_LOGIN = 0x04;

        /**
         * @author:wjj
         * @date: 2017/6/27 15:03.
         * @description:
         */
        public static class BaseHandler extends Handler {

            private WeakReference<BaseActivity> reference;

            public BaseHandler(BaseActivity activity) {
                reference = new WeakReference<BaseActivity>(activity);
            }

            @Override
            public void handleMessage(Message msg) {
                if (reference != null && reference.get() != null) {
                    reference.get().handlerMessage(msg);
                }
            }

        }
    }
}
