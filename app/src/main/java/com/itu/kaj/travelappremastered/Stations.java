package com.itu.kaj.travelappremastered;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Stations extends ListActivity {

    private TravelDAO dao;
    //private final String[] stations = {"København", "Nordhavn", "Østerbro", "Nørreport"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimpleCursorAdapter cursorAdapter;
        dao = new TravelDAO(this);
        dao.open();
        Cursor stations = dao.getStations();
        startManagingCursor(stations);
        cursorAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, stations, new String[] { "station" },
                new int[] { android.R.id.text1 });

        setListAdapter(cursorAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Cursor cursor = (Cursor) l.getItemAtPosition(position);
        Intent intent = new Intent().putExtra(
                TravelActivity.SELECTED_STATION_NAME,
                cursor.getString(cursor.getColumnIndexOrThrow("station")));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stations, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dao.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
