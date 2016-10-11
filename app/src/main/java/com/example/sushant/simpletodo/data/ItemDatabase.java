package com.example.sushant.simpletodo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sushant on 11/10/16.
 */

public class ItemDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "item.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + ItemsContract.TABLE_NAME + " ("
                    + ItemsContract._ID + " INTEGER PRIMARY KEY,"
                    + ItemsContract.COLUMN_ITEM_NAME + TEXT_TYPE +COMMA_SEP
                    + ItemsContract.COLUMN_ITEM_STATUS + TEXT_TYPE
                    + " )";

    private static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + ItemsContract.TABLE_NAME;

    public ItemDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }
}
