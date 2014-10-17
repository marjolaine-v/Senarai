package songs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marjolainevericel.senarai.R;

public class SongCardFragment extends Fragment {

    private static String mId;

    /***************************************************
     * INIT
     ***************************************************/
    public SongCardFragment() { }
    public static SongCardFragment newInstance(String id) {
        SongCardFragment fragment = new SongCardFragment();

        mId = id;

        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_song, container, false);

        ((TextView) view.findViewById(R.id.song_card_title)).setText("Chanson n°" + mId);

        return view;
    }
}
