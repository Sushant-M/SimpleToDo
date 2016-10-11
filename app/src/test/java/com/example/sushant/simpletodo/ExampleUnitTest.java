package com.example.sushant.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.InstrumentationInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.AndroidTestCase;

import com.example.sushant.simpletodo.data.ItemDatabase;
import com.example.sushant.simpletodo.data.ItemsContract;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest extends AndroidTestCase {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void database_working() throws Exception{
        SQLiteOpenHelper sqLiteOpenHelper = new ItemDatabase(getContext());
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemsContract.COLUMN_ITEM_NAME,"answer");
        sqLiteDatabase.insert(ItemsContract.TABLE_NAME,
                null,
                contentValues);

        String[] projections = {"test"};

        Cursor cursor = sqLiteDatabase.query(ItemsContract.TABLE_NAME,
                projections,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        int indexId = cursor.getColumnIndex(ItemsContract.COLUMN_ITEM_NAME);
        String read = cursor.getString(indexId);
        assertEquals("answer",read);
    }
}