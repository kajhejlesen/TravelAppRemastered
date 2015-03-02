package com.itu.kaj.travelappremastered;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kaj on 25-02-2015.
 */
public class TravelDAO {

    private DbHelper dbHelper;
    private SQLiteDatabase travelDatabase;
    private final static String STATIONS_TABLE = "stations";
    private final static String STATIONS_STATION = "station";
    private final static String TRAVELS_TABLE = "travels";
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

    public Cursor getTravels() {
        Cursor cursor = travelDatabase.query(TRAVELS_TABLE, new String[] {"_id", TRAVEL_START, TRAVEL_DESTINATION}, null,null,null,null, "_id");
        return cursor;
    }

    public void saveTravel(String start, String destination) {
        ContentValues values = new ContentValues();
        values.put(TRAVEL_START, start);
        values.put(TRAVEL_DESTINATION, destination);
        travelDatabase.insert(TRAVELS_TABLE, null, values);
    }

    public Cursor getStations() {
        Cursor cursor = travelDatabase.query(STATIONS_TABLE, new String[] {"_id", STATIONS_STATION}, null,null,null,null, STATIONS_STATION);
        return cursor;
    }

    public void saveStation(String station) {
        Cursor cursor = travelDatabase.rawQuery("SELECT station FROM stations WHERE station LIKE '" + station + "'", null);
        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put(STATIONS_STATION, station);
            travelDatabase.insert(STATIONS_TABLE, null, values);
        }


    }

}
