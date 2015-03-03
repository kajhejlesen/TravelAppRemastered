package com.itu.kaj.travelappremastered;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TravelActivity extends ActionBarActivity {
    public static final String SELECTED_STATION_NAME = "selected_station";
    public static final String LAST_START = "start";
    public static final String LAST_DESTINATION = "destination";
    public static final String CHECK_IN_BUTTON = "check_in_button";
    public static final String CHECK_OUT_BUTTON = "check_out_button";
    public static final String CHECK_IN_ENABLED = "check_in_enabled";
    public static final String CHECK_OUT_ENABLED = "check_out_enabled";
    public static final String CHECK_IN_TEXT = "check_in_text";
    public static final String CHECK_OUT_TEXT = "check_out_text";
    public static final String RECEIPT = "receipt";
    public static final int SELECT_IN = 1;
    public static final int SELECT_OUT = 2;

    private static String startStation = "";
    private static String endStation = "";

    private static String[] receipt = new String[2];

    private Button checkInButton, checkOutButton, selectInButton, selectOutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        checkInButton = (Button) findViewById(R.id.check_in_button);
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText checkInText = (EditText) findViewById(R.id.check_in_input);
                TravelActivity.startStation = checkInText.getText().toString();
                if (TravelActivity.startStation.equals(""))
                    Toast.makeText(TravelActivity.this, "Please enter input", Toast.LENGTH_SHORT).show();
                else {
                    TravelDAO dao = new TravelDAO(TravelActivity.this);
                    dao.open();
                    dao.saveStation(startStation);
                    dao.close();
                    Button checkOutButton = (Button) findViewById(R.id.check_out_button);
                    Button checkInButton = (Button) findViewById(R.id.check_in_button);
                    EditText checkOutText = (EditText) findViewById(R.id.check_out_input);
                    checkInText.setEnabled(false);
                    checkOutButton.setEnabled(true);
                    checkInButton.setEnabled(false);
                    checkOutText.setEnabled(true);
                    selectInButton.setEnabled(false);
                    selectOutButton.setEnabled(true);
                }
            }
        });

        checkOutButton = (Button) findViewById(R.id.check_out_button);
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText checkOutText = (EditText) findViewById(R.id.check_out_input);
                TravelActivity.endStation = checkOutText.getText().toString();
                if (TravelActivity.endStation.equals(""))
                    Toast.makeText(TravelActivity.this, "Please enter input", Toast.LENGTH_SHORT).show();
                else {
                    TravelDAO dao = new TravelDAO(TravelActivity.this);
                    dao.open();
                    dao.saveStation(endStation);
                    dao.saveTravel(startStation, endStation);
                    dao.close();
                    EditText checkInText = (EditText) findViewById(R.id.check_in_input);
                    checkOutText.setText("");
                    checkInText.setText("");
                    Button checkOutButton = (Button) findViewById(R.id.check_out_button);
                    Button checkInButton = (Button) findViewById(R.id.check_in_button);
                    checkInButton.setEnabled(true);
                    checkOutButton.setEnabled(false);
                    checkInText.setEnabled(true);
                    checkOutText.setEnabled(false);
                    selectInButton.setEnabled(true);
                    selectOutButton.setEnabled(false);
                    TravelActivity.receipt[0] = TravelActivity.startStation;
                    TravelActivity.receipt[1] = TravelActivity.endStation;

                    Toast.makeText(TravelActivity.this, "You are hopefully at your destination", Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectInButton = (Button) findViewById(R.id.selectInButton);
        selectInButton.setOnClickListener(new ButtonListener());

        selectOutButton = (Button) findViewById(R.id.selectOutButton);
        selectOutButton.setOnClickListener(new ButtonListener());

    }

    public class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(TravelActivity.this, Stations.class);
            if (v.getId() == R.id.selectInButton)
                startActivityForResult(intent, SELECT_IN);
            else if (v.getId() == R.id.selectOutButton)
                startActivityForResult(intent, SELECT_OUT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) throw new IllegalStateException("RESULT_OK returned not ok");
        String station = data.getStringExtra(SELECTED_STATION_NAME);
        EditText checkInText = (EditText) findViewById(R.id.check_in_input);
        EditText checkOutText = (EditText) findViewById(R.id.check_out_input);
        if (requestCode == SELECT_IN) checkInText.setText(station);
        else if (requestCode == SELECT_OUT) checkOutText.setText(station);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_travel, menu);
        MenuItem item = menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "History");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == Menu.FIRST) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(LAST_START, startStation);
        outState.putString(LAST_DESTINATION, endStation);
        outState.putStringArray(RECEIPT, receipt);
        outState.putBoolean(CHECK_IN_BUTTON, checkInButton.isEnabled());
        outState.putBoolean(CHECK_OUT_BUTTON, checkOutButton.isEnabled());

        EditText checkInText = (EditText) findViewById(R.id.check_in_input);
        outState.putBoolean(CHECK_IN_ENABLED, checkInText.isEnabled());
        outState.putString(CHECK_IN_TEXT, checkInText.getText().toString());

        EditText checkOutText = (EditText) findViewById(R.id.check_out_input);
        outState.putBoolean(CHECK_OUT_ENABLED, checkOutText.isEnabled());
        outState.putString(CHECK_OUT_TEXT, checkOutText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        startStation = savedInstanceState.getString(LAST_START);
        endStation = savedInstanceState.getString(LAST_DESTINATION);
        receipt = savedInstanceState.getStringArray(RECEIPT);
        checkInButton.setEnabled(savedInstanceState.getBoolean(CHECK_IN_BUTTON));
        checkOutButton.setEnabled(savedInstanceState.getBoolean(CHECK_OUT_BUTTON));

        EditText checkInText = (EditText) findViewById(R.id.check_in_input);
        checkInText.setEnabled(savedInstanceState.getBoolean(CHECK_IN_ENABLED));
        checkInText.setText(savedInstanceState.getString(CHECK_IN_TEXT));

        EditText checkOutText = (EditText) findViewById(R.id.check_out_input);
        checkOutText.setEnabled(savedInstanceState.getBoolean(CHECK_OUT_ENABLED));
        checkOutText.setText(savedInstanceState.getString(CHECK_OUT_TEXT));
    }

}
