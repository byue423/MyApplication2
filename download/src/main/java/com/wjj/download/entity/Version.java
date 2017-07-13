package com.wjj.download.entity;

/**
 * @author wjj
 * @功能：下载更新中，服务器返回的xml解析实体
 */
public class Version {

    private String verCode; // 下载的版本
    private String verName; // 下载的版本名称
    private String updateMsg;// 下载更新信息
    private String md5Str; // md5校验值
    private String isForce;// 是否强制更新
    private String apkUrl; // 下载的apk路径
    private boolean isSucc; // 解析是否成功
    private String error;// 解析的错误信息

    /**
     * @return the verCode
     */
    public String getVerCode() {
        return verCode;
    }

    /**
     * @param verCode the verCode to set
     */
    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    /**
     * @return the verName
     */
    public String getVerName() {
        return verName;
    }

    /**
     * @param verName the verName to set
     */
    public void setVerName(String verName) {
        this.verName = verName;
    }

    /**
     * @return the updateMsg
     */
    public String getUpdateMsg() {
        return updateMsg;
    }

    /**
     * @param updateMsg the md5Str to set
     */
    public void setUpdateMsg(String updateMsg) {
        this.updateMsg = updateMsg;
    }

    /**
     * @return the md5Str
     */
    public String getMd5Str() {
        return md5Str;
    }

    /**
     * @param md5Str the md5Str to set
     */
    public void setMd5Str(String md5Str) {
        this.md5Str = md5Str;
    }

    /**
     * @return the isForce
     */
    public String getIsForce() {
        return isForce;
    }

    /**
     * @param isForce the isForce to set
     */
    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }

    /**
     * @return the apkUrl
     */
    public String getApkUrl() {
        return apkUrl;
    }

    /**
     * @param apkUrl the apkUrl to set
     */
    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    /**
     * @return the isSucc
     */
    public boolean isSucc() {
        return isSucc;
    }

    /**
     * @param isSucc the isSucc to set
     */
    public void setSucc(boolean isSucc) {
        this.isSucc = isSucc;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

}
