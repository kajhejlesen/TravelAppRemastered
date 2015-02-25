package com.itu.kaj.travelappremastered;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Stations extends ListActivity {

    private final String[] stations = {"København", "Nordhavn", "Østerbro", "Nørreport"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> statsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stations);
        setListAdapter(statsAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String value = stations[(int)id];
        Intent intent = new Intent().putExtra(TravelActivity.SELECTED_STATION_NAME, value);
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
