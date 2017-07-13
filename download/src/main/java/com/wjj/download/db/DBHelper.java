package com.wjj.download.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static String DB_Name = "app_download.db";
    public static int DB_Version = 1;

    public DBHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    public DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    String t_download = "CREATE TABLE t_download (id  INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "curposition varchar(50), fileSize  varchar(50),fileStatus  varchar(100)" +
            "urlStr  varchar(50),fileName  varchar(100))";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(t_download);
    }

    /**
     * 判断某张表是否存在
     *
     * @param tableName 表名
     * @return
     */
    public boolean tabbleIsExist(String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String sql = "select count(*) as c from Sqlite_master where type ='table' and name ='" + tableName.trim()
                    + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
        } finally {
            cursor.close();
            db.close();
        }
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(DROP TABLE IF EXISTS question);
        //onCreate(db);
    }

}
