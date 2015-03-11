package com.itu.kaj.travelappremastered;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kaj on 25-02-2015.
 */
public class DbHelper extends android.database.sqlite.SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "travel", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE travels (_id integer primary key autoincrement, start text, destination text, distance real)");
       db.execSQL("CREATE TABLE stations (_id integer primary key autoincrement, station text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
