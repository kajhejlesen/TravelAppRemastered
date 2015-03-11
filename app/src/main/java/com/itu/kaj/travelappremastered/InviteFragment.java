package com.itu.kaj.travelappremastered;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.ListFragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.support.v4.widget.SimpleCursorAdapter;

import com.itu.kaj.travelappremastered.R;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 */
public class InviteFragment extends ListFragment {


    private CursorLoader loader;
    private SimpleCursorAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public InviteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // TODO: Change Adapter to display your content

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] queryFields = new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME };
        Cursor c = getActivity().getContentResolver().query(uri, queryFields, null, null, null);
        c.moveToFirst();

        int[] TO_IDS = { android.R.id.text1 };

        mAdapter = new android.support.v4.widget.SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1, c, new String[] {ContactsContract.Contacts.DISPLAY_NAME},
                new int[] { android.R.id.text1 }, 0);

        setListAdapter(mAdapter);


        return super.onCreateView(inflater, container, savedInstanceState);
    }



}
