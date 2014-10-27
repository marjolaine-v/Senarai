package songs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.marjolainevericel.senarai.R;

public class SongCardFragment extends Fragment {

    private static Song mSong;
    private OnSongCardListener mListener;
    private TextView mEditDuration;
    private TextView mEditTempo;
    private ProgressBar mPBDancability;
    private ProgressBar mPBEnergy;
    private ProgressBar mPBHotttnesss;

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

        ((TextView) view.findViewById(R.id.card_song_title)).setText(mSong.getTitle());
        mEditDuration = (TextView) view.findViewById(R.id.card_song_duration);
        mEditTempo = (TextView) view.findViewById(R.id.card_song_tempo);
        mPBDancability = (ProgressBar) view.findViewById(R.id.card_song_dancability);
        mPBEnergy = (ProgressBar) view.findViewById(R.id.card_song_energy);
        mPBHotttnesss = (ProgressBar) view.findViewById(R.id.card_song_hotttnesss);

        view.findViewById(R.id.card_song_see_artist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onGoToArtistButtonClicked(mSong);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSongCardListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /***************************************************
     * INTERFACE
     ***************************************************/
    public interface OnSongCardListener {
        void onGoToArtistButtonClicked(Song song);
    }
}
