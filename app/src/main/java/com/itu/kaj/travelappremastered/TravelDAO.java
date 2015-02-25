package com.itu.kaj.travelappremastered;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kaj on 25-02-2015.
 */
public class TravelDAO {

    private DbHelper dbHelper;
    private SQLiteDatabase travelDatabase;
    private final static String TRAVEL_START = "start";
    private final static String TRAVEL_DESTINATION = "destination";

    public TravelDAO(Context context) {
        dbHelper = new DbHelper(context);
    }


    public void open() {
        travelDatabase = dbHelper.getWritableDatabase();
    }

    public void close() {
        travelDatabase.close();
    }

    public void saveTravel(String start, String destination) {
        ContentValues values = new ContentValues();
    }

}
