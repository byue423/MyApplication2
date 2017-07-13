package com.wjj.download.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wjj.download.entity.ProgressMsg;

/**
 * @author wjj
 * @功能：对文件下载的相关操作的业务类
 */
public class DownloadDao {
    private static DownloadDao dao = null;
    private DBHelper db = null;

    private DownloadDao(Context context) {
        db = new DBHelper(context);
    }

    public static DownloadDao getInstance(Context context) {
        if (dao == null) {
            dao = new DownloadDao(context);
        }
        return dao;
    }

    /**
     * 查看数据库中是否有数据 true, 表里不存在该线程记录， false,表里存在
     */
    public synchronized boolean isHas(String urlStr) {
        SQLiteDatabase database = db.getReadableDatabase();
        int count = -1;
        Cursor cursor = null;
        try {
            String sql = "select count(*)  from t_download where urlStr = ?";
            cursor = database.rawQuery(sql, new String[]{urlStr});
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.close();
            }
            if (null != cursor) {
                cursor.close();
            }
        }
        return count == 0;
    }

    /**
     * 保存 下载的具体信息:fileStatus = 0,可下载，1，不可下载
     */
    public synchronized void insert(int curPosition, int fileSize,
                                    String urlStr, String fileName) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        try {
            String sql = "insert into t_download(curposition, fileSize,"
                    + " fileStatus, urlStr, fileName) values (?,?,?,?,?)";
            database.execSQL(sql, new Object[]{curPosition, fileSize, 0,
                    urlStr, fileName});
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.endTransaction();
                database.close();
            }
        }
    }

    /**
     * 得到下载具体信息
     */
    public synchronized ProgressMsg getInfo(String urlStr) {
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = null;
        ProgressMsg pro = null;
        try {
            if (urlStr != null && !"".equals(urlStr)) {
                String sql = "select * from t_download where urlStr = ?";
                cursor = database.rawQuery(sql, new String[]{urlStr});
                while (cursor.moveToNext()) {
                    pro = new ProgressMsg();
                    pro.setProId(cursor.getInt(0));
                    pro.setCurPosition(cursor.getInt(1));
                    pro.setFileSize(cursor.getInt(2));
                    pro.setFileStatus(cursor.getInt(3));
                    pro.setUrlStr(cursor.getString(4));
                    pro.setFileName(cursor.getString(5));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.close();
            }
            if (null != cursor) {
                cursor.close();
            }
        }
        return pro;
    }

    /**
     * 更新数据库中的下载信息
     */
    public synchronized void update(int curPosition, String urlStr) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        try {
            if (urlStr != null && !"".equals(urlStr)) {
                String sql = "update t_download set curPosition=? where urlStr = ?";
                database.execSQL(sql, new Object[]{curPosition, urlStr});
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.endTransaction();
                database.close();
            }
        }
    }

    /**
     * 更新所有下载文件的下载状态
     */
    public synchronized void updateAllStatus(int status) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        try {
            String sql = "update t_download set fileStatus=? where downloadId != -1";
            database.execSQL(sql, new Object[]{status});
            database.setTransactionSuccessful();
        } catch (Exception e) {
            //Log.d("DownloadDao", "t_download表中更新所有下载文件的下载状态");
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.endTransaction();
                database.close();
            }
        }
    }

    /**
     * 更新所有下载文件的下载状态
     */
    public synchronized void updateOtherAllStatus(String urlStr) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        try {
            if (urlStr != null && !"".equals(urlStr)) {
                String sql = "update t_download set fileStatus=? where urlStr = ?";
                database.execSQL(sql, new Object[]{1, urlStr});
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.endTransaction();
                database.close();
            }
        }
    }

    /**
     * 更新下载文件的下载状态
     */
    public synchronized void updateFileStatus(String urlStr, int status) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        try {
            if (urlStr != null && !"".equals(urlStr)) {
                String sql = "update t_download set fileStatus=? where urlStr = ?";
                database.execSQL(sql, new Object[]{status, urlStr});
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.endTransaction();
                database.close();
            }
        }
    }

    /**
     * 下载完成后删除数据库中的数据
     */
    public synchronized void delete(String urlStr) {
        //Log.d("DownloadDao", "删除该文件的信息：" + urlStr);
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        try {
            if (urlStr != null && !"".equals(urlStr)) {
                database.delete("t_download", "urlStr = ?",
                        new String[]{urlStr});
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.endTransaction();
                database.close();
            }
        }
    }
}