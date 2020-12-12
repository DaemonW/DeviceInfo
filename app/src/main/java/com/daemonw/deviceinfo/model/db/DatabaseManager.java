package com.daemonw.deviceinfo.model.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by daemonw on 17-3-12.
 */

public class DatabaseManager {
    
    private static DatabaseManager mInstance;
    private SQLiteOpenHelper mDbHelper;
    private SQLiteDatabase mDb;
    private String mDbKey;//for SQLCipher


    private DatabaseManager(Context context, String dbFile, String dbKey) {
        mDbHelper = new DatabaseHelper(context, dbFile);
        this.mDbKey = dbKey;
    }

    public static synchronized void initialize(Context context, String secret) throws SQLException {
        initialize(context, secret, null);
    }

    public static synchronized void initialize(Context context, String secret, String dbFile) throws SQLException {
        if (mInstance == null) {
            mInstance = new DatabaseManager(context, dbFile, secret);
        }
        SQLiteDatabase conn = null;
        try {
            conn = mInstance.getWritableDatabase(mInstance.mDbKey);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    private SQLiteDatabase getWritableDatabase(String secret) {
        return mDbHelper.getWritableDatabase();
    }

    public synchronized static DatabaseManager get() {
        if (mInstance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() + " is not initialized, call initialize(..) method first");
        }
        return mInstance;
    }

    public synchronized SQLiteDatabase getConnection() throws SQLException {
        if (mDb == null) {
            mDb = mDbHelper.getWritableDatabase();
        }
        return mDb;
    }

    public synchronized void close() {
        if (mDb != null) {
            mDb.close();
            mDb = null;
        }
        mInstance = null;
    }
}
