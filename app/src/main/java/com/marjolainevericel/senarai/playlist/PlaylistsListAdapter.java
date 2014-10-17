package com.marjolainevericel.senarai.playlist;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.echonest.api.v4.Playlist;
import com.echonest.api.v4.Song;
import com.marjolainevericel.senarai.R;

import java.util.HashMap;

/**
 * Created by Marjolaine on 16/10/2014.
 *
public class PlaylistsListAdapter extends ArrayAdapter<Playlist> {

    private final LayoutInflater mInflater;
    HashMap<Integer, String> fragmentMap;

    public PlaylistsListAdapter(Context context) {
        super(context, R.layout.playlist_card);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.playlist_card, parent, false);
        } else {
            view = convertView;
        }

        Playlist playlist = getItem(position);

        ((TextView) view.findViewById(R.id.playlist_card_title)).setText("Titre " + position);
        ((TextView) view.findViewById(R.id.playlist_card_description)).setText("Description");

        return view;
    }
/*public class PlaylistsAdapter extends FragmentPagerAdapter {
    HashMap<Integer, String> fragmentMap;

    public PlaylistsAdapter(FragmentManager fm) {
        super(fm);
        fragmentMap = new HashMap<Integer, String>();
        fragmentMap.put(0, "Alec Empire");
        fragmentMap.put(1, "U2");
        fragmentMap.put(2, "Linkin Park");
    }

    @Override
    public Fragment getItem(int i) {
        return PlaylistsFragment.newInstance(10, fragmentMap.get(i));
    }

    @Override
    public int getCount() {
        return 3;
    }*
}*/