package com.itu.kaj.travelappremastered;

import android.app.ListActivity;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class HistoryActivity extends ListActivity {

    private TravelDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleCursorAdapter cursorAdapter;
        dao = new TravelDAO(this);
        dao.open();
        Cursor travels = dao.getTravels();
        startManagingCursor(travels);
        cursorAdapter = new SimpleCursorAdapter(this,
                R.layout.activity_history, travels, new String[] { "start", "destination", "distance"},
                new int[] { R.id.history_view_1, R.id.history_view_2, R.id.history_view_3 });

        setListAdapter(cursorAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dao.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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
