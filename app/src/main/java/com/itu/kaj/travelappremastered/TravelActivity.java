package com.itu.kaj.travelappremastered;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TravelActivity extends ActionBarActivity {
    private static String startStation = "";
    private static String endStation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);



        Button checkInButton = (Button) findViewById(R.id.check_in_button);
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText checkInText = (EditText) findViewById(R.id.check_in_input);
                TravelActivity.startStation = checkInText.getText().toString();
                if (TravelActivity.startStation.equals(""))
                    Toast.makeText(TravelActivity.this, "Please enter input", Toast.LENGTH_SHORT).show();
                else {
                    Button checkOutButton = (Button) findViewById(R.id.check_out_button);
                    Button checkInButton = (Button) findViewById(R.id.check_in_button);
                    EditText checkOutText = (EditText) findViewById(R.id.check_out_input);
                    checkInText.setEnabled(false);
                    checkOutButton.setEnabled(true);
                    checkInButton.setEnabled(false);
                    checkOutText.setEnabled(true);
                    TravelActivity.endStation = "";
                }
            }
        });

        Button checkOutButton = (Button) findViewById(R.id.check_out_button);
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText checkOutText = (EditText) findViewById(R.id.check_out_input);
                TravelActivity.endStation = checkOutText.getText().toString();
                if (TravelActivity.endStation.equals(""))
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

                    Toast.makeText(TravelActivity.this, "You are hopefully at your destination", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_travel, menu);
        MenuItem item = menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Reciept");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
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
