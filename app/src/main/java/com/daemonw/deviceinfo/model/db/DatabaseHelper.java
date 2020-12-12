package com.daemonw.deviceinfo.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by daemonw on 17-3-11.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_DEVICES = "support_devices";

    private static final String CREATE_TABLE_DEVICES = "CREATE TABLE " +
            TABLE_DEVICES + "(brand text NOT NULL, model text NOT NULL, device text, " +
            "market_name text, PRIMARY KEY(brand, model))";


    public DatabaseHelper(Context context, String dbFile) {
        super(context, dbFile, null, 1);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DEVICES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db, TABLE_DEVICES);
    }

    public void dropTable(SQLiteDatabase db, String tableName) {
        String sqlState = String.format("drop table %s if exists", tableName);
        db.execSQL(sqlState);
    }
}
