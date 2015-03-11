package com.itu.kaj.travelappremastered;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
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
    private final static String TRAVEL_DISTANCE = "distance";
    private SharedPreferences preferences;

    public TravelDAO(Context context) {
        dbHelper = new DbHelper(context);
        preferences = context.getSharedPreferences(context.getPackageName()
                + "_preferences", Context.MODE_PRIVATE);
    }


    public void open() {
        travelDatabase = dbHelper.getWritableDatabase();
    }

    public void close() {
        travelDatabase.close();
    }

    public Cursor getTravels() {
        String limit = preferences.getString("history_length", "10");
        Cursor cursor = travelDatabase.query(TRAVELS_TABLE, new String[] {"_id", TRAVEL_START, TRAVEL_DESTINATION, TRAVEL_DISTANCE}, null,null,null,null, "_id DESC", limit);
        return cursor;
    }

    public void saveTravel(String start, String destination, double distance) {
        ContentValues values = new ContentValues();
        values.put(TRAVEL_START, start);
        values.put(TRAVEL_DESTINATION, destination);
        values.put(TRAVEL_DISTANCE, distance);
        travelDatabase.insert(TRAVELS_TABLE, null, values);
    }

    public Cursor getStations() {
        Cursor cursor = travelDatabase.query(STATIONS_TABLE, new String[] {"_id", STATIONS_STATION}, null,null,null,null, STATIONS_STATION);
        return cursor;
    }

    public void saveStation(String station) {
        Cursor cursor = travelDatabase.rawQuery("SELECT station FROM stations WHERE station LIKE '" + station + "'", null);
        if (cursor.getCount() == 0 || !preferences.getBoolean("add_station_check", true)) {
            ContentValues values = new ContentValues();
            values.put(STATIONS_STATION, station);
            travelDatabase.insert(STATIONS_TABLE, null, values);
        }
    }

    public void clearStations() {
        travelDatabase.delete(STATIONS_TABLE, null, null);
    }

    public void clearTravels() {
        travelDatabase.delete(TRAVELS_TABLE, null, null);
    }

}
