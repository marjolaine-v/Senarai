package songs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.marjolainevericel.senarai.R;

public class SongCardFragment extends Fragment {

    private static Song mSong;

    /***************************************************
     * INIT
     ***************************************************/
    public SongCardFragment() { }
    public static SongCardFragment newInstance(Song song) {
        SongCardFragment fragment = new SongCardFragment();

        mSong = song;

        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_song, container, false);

        ((TextView) view.findViewById(R.id.song_card_title)).setText(mSong.getTitle());

        return view;
    }
}
