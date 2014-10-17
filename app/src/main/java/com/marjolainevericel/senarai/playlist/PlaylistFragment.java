package com.marjolainevericel.senarai.playlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marjolainevericel.senarai.R;

public class PlaylistFragment extends Fragment {

    private static String mTitle;
    private static String mDescription;

    public PlaylistFragment() { }

    public static PlaylistFragment newInstance(String title, String description) {
        PlaylistFragment fragment = new PlaylistFragment();

        mTitle = title;
        mDescription = description;

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_playlist, container, false);

        ((TextView) view.findViewById(R.id.playlist_title)).setText(mTitle);

        return view;
    }


}
