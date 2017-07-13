package com.wjj.download;

import android.os.Environment;

public class DownloadConfig {

    // 网络连接失败
    public static final String ISNOTWIFI = "手机网络连接失败,请检查网络!";
    // 文件下载状态
    public static final int FILEISDOWNLOAD = 0;// 文件可下
    public static final int FILEISNOTDOWNLOAD = 1;// 文件不可下载，如取消下载后，停止线程使用
    // 下载类型: 暂时只支持单个文件下载时，弹出的提示框，显示单个进度条下载；多个文件下载时，不支持弹出框，支持用户自定义界面
    public static final int DOWNLOADDIALOGFILE = 0;// 下载单个文件，进度条在弹出框显示
    public static final int DOWNLOADAPK = 1; // 下载更新apk，进度条在弹出框显示
    public static final int DOWNLOADCUSTOMFILES = 2;// 批量下载文件，进度条是用户自定义的界面显示
    public static final int DOWNLOADCUSTOMIMAGES = 3;// 批量下载图片， 图片的显示界面为用户自定义
    // URLConnection连接设置
    public static final int CONNECTTIMEOUT = 5000;// 连接超时的时间
    public static final int READTIMEOUT = 3000;// 读超时的时间
    // 提示语言
    public static final String DOWNOVER = "下载完成，请查看!"; // 下载完成

    // 下载的apk，是否需要验证md5值
    public static boolean ISVALIDATEAPK = false;

    // 应用下载的根路径
    public static String BASEPATH = "yidongOA";
    // 应用下载的附件文件夹
    public static String DOWNDIR = "/yidongOA/attachment/";
    // 软件更新包存储文件名
    public static final String APKDOWNLOADFILENAME = Environment.getExternalStorageDirectory().getAbsolutePath()
            + DOWNDIR + "yidongOA.apk";
}
