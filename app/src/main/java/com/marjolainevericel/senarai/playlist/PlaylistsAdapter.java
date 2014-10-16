package com.marjolainevericel.senarai.playlist;

import android.content.Context;
import android.database.DataSetObserver;
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

/**
 * Created by Marjolaine on 16/10/2014.
 */
public class PlaylistsAdapter extends ArrayAdapter<Playlist> {

    private final LayoutInflater mInflater;

    public PlaylistsAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
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
}
