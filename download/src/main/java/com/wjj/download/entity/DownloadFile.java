package com.wjj.download.entity;

/**
 * @author wjj
 * @功能：下载的文件实体类
 */
public class DownloadFile {
    private int proId;// 进度条id
    private int textId;// 进度条百分比
    private int btnId;// 打开文件的按钮Id
    private int imageId;// 图片id
    private String downloadURL;// 下载的文件路径
    private String saveName;// 下载后要保存的文件名字 (以默认名称保存,此字段可以不设置)

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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

}
