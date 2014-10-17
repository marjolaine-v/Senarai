package playlists;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marjolainevericel.senarai.R;

public class PlaylistCardFragment extends Fragment {

    private static String mTitle;
    private static String mDescription;


    /***************************************************
     * INIT
     ***************************************************/
    public PlaylistCardFragment() { }
    public static PlaylistCardFragment newInstance(String title, String description) {
        PlaylistCardFragment fragment = new PlaylistCardFragment();

        mTitle = title;
        mDescription = description;

        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_playlist, container, false);

        ((TextView) view.findViewById(R.id.playlist_card_title)).setText(mTitle);
        ((TextView) view.findViewById(R.id.playlist_card_description)).setText(mDescription);

        return view;
    }
}
