package com.wjj.download.entity;

/**
 * @author wjj
 * @功能：下载进度消息(由于支持了多线程下载,区别哪一个线程的进度)
 */
public class ProgressMsg {

    private int proId;// 进度条的Id
    private int textId;// 进度百分比textview的id
    private int btnId;// 打开文件的按钮Id
    private int progress;// 下载进度
    private int curPosition;// 下载位置
    private int fileSize;// 文件大小
    private int fileStatus;// 下载状态
    private String urlStr;// 下载文件路径
    private String fileName;// 下载存储的文件名，未下载完的文件

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public int getBtnId() {
        return btnId;
    }

    public void setBtnId(int btnId) {
        this.btnId = btnId;
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCurPosition() {
        return curPosition;
    }

    public void setCurPosition(int curPosition) {
        this.curPosition = curPosition;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(int fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getUrlStr() {
        return urlStr;
    }

    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
