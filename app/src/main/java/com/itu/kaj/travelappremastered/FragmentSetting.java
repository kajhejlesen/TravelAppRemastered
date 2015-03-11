package com.itu.kaj.travelappremastered;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.preference.*;
import android.view.View;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSetting extends PreferenceFragment {

    TravelDAO dao;

    public FragmentSetting() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = new TravelDAO(getActivity());
        dao.open();
        Preference clearHistory = findPreference("clr_history");
        clearHistory.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                dao.clearTravels();
                Toast.makeText(getActivity(), "Travel History deleted.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        Preference clearStations = findPreference("clr_stations");
        clearStations.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                dao.clearStations();
                Toast.makeText(getActivity(), "Stations deleted.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.close();
    }
}
