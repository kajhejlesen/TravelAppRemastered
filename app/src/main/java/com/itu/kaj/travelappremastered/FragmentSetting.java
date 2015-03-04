package com.itu.kaj.travelappremastered;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.preference.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
                return true;
            }
        });

        Preference clearStations = findPreference("clr_stations");
        clearHistory.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                dao.clearStations();
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
