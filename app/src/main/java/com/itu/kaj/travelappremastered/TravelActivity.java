package com.itu.kaj.travelappremastered;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;


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
    TravelDAO dao;

    private TextView getLocation;
    private Button checkInButton, checkOutButton, selectInButton, selectOutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        dao = new TravelDAO(this);
        dao.open();

        getLocation = (TextView) findViewById(R.id.get_location);
        getLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LocationManager lm;
                // not finished
            }
        });

        checkInButton = (Button) findViewById(R.id.check_in_button);
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText checkInText = (EditText) findViewById(R.id.check_in_input);
                TravelActivity.startStation = checkInText.getText().toString();
                if (TravelActivity.startStation.equals(""))
                    Toast.makeText(TravelActivity.this, "Please enter input", Toast.LENGTH_SHORT).show();
                else {
                    dao.saveStation(startStation);
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
                endStation = checkOutText.getText().toString();
                if (endStation.equals(""))
                    Toast.makeText(TravelActivity.this, "Please enter input", Toast.LENGTH_SHORT).show();
                else {

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
                    TravelActivity.receipt[0] = startStation;
                    TravelActivity.receipt[1] = endStation;

                    double distance = 0.0;


                    if (Geocoder.isPresent()) {
                        Geocoder geoCoder = new Geocoder(TravelActivity.this);
                        Location locationStart = null;
                        Location locationEnd = null;
                        try {
                            List<Address> aStart = geoCoder.getFromLocationName(TravelActivity.startStation, 1);
                            List<Address> aEnd = geoCoder.getFromLocationName(TravelActivity.endStation, 1);
                            if (aStart.size() > 0) {
                                Address start = aStart.get(0);
                                locationStart = new Location(start.getAddressLine(0));
                                locationStart.setLatitude(start.getLatitude());
                                locationStart.setLongitude(start.getLongitude());
                            }
                            if (aEnd.size() > 0) {
                                Address end = aEnd.get(0);
                                locationEnd = new Location(end.getAddressLine(0));
                                locationEnd.setLatitude(end.getLatitude());
                                locationEnd.setLongitude(end.getLongitude());

                            }
                        } catch (IOException ioe) {
                            System.out.println("Wrong locationdata");
                        }

                        if (locationStart != null && locationEnd != null) {
                            distance = locationEnd.distanceTo(locationStart) / 1000.0;
                        }
                    }
                    dao.saveStation(endStation);
                    dao.saveTravel(startStation, endStation, distance);

                    Toast.makeText(TravelActivity.this, "You have travelled " + distance + " km", Toast.LENGTH_SHORT).show();
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
        if (resultCode == RESULT_OK) {
            String station = data.getStringExtra(SELECTED_STATION_NAME);
            EditText checkInText = (EditText) findViewById(R.id.check_in_input);
            EditText checkOutText = (EditText) findViewById(R.id.check_out_input);
            if (requestCode == SELECT_IN) checkInText.setText(station);
            else if (requestCode == SELECT_OUT) checkOutText.setText(station);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_travel, menu);
        MenuItem item = menu.add(Menu.NONE, 1, Menu.FIRST, "History");
        MenuItem item2 = menu.add(Menu.NONE, 2, Menu.FIRST, "Invite");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        getMenuInflater().inflate(R.menu.menu_travel,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == 1) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }

        if (id == 2) {
            Intent intent = new Intent(this, InviteActivity.class);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dao.close();
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
