package songs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.marjolainevericel.senarai.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SongCardFragment extends Fragment  implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Song>> {

    private static final int SONG_LOADER_ID = 20;
    private View mView;
    private static Song mSong;
    private static String mTitle;
    private static String mArtist;
    private OnSongCardListener mListener;
    private TextView mTVDuration;
    private TextView mTVTempo;
    private ProgressBar mPBDancability;
    private ProgressBar mPBEnergy;
    private ProgressBar mPBHotttnesss;
    private ImageView mIVCover;

    /***************************************************
     * INIT
     ***************************************************/
    public SongCardFragment() { }
    public static SongCardFragment newInstance(Song song) {
        SongCardFragment fragment = new SongCardFragment();
        mSong = song;
        mTitle = song.getTitle();
        mArtist = song.getArtistName();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(SONG_LOADER_ID, null, this);
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.card_song, container, false);

        ((TextView) mView.findViewById(R.id.card_song_title)).setText(mSong.getTitle());
        mTVDuration = (TextView) mView.findViewById(R.id.card_song_duration);
        mTVTempo = (TextView) mView.findViewById(R.id.card_song_tempo);
        mPBDancability = (ProgressBar) mView.findViewById(R.id.card_song_dancability);
        mPBEnergy = (ProgressBar) mView.findViewById(R.id.card_song_energy);
        mPBHotttnesss = (ProgressBar) mView.findViewById(R.id.card_song_hotttnesss);
        ((TextView) mView.findViewById(R.id.card_song_artist)).setText(mSong.getArtistName());
        mIVCover = (ImageView) mView.findViewById(R.id.card_song_cover);

        mView.findViewById(R.id.card_song_see_artist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onGoToArtistButtonClicked(mSong);
            }
        });

        return mView;
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
     * LOADER
     ***************************************************/
    @Override
    public Loader<List<Song>> onCreateLoader(int i, Bundle bundle) {
        SongLoader loader = new SongLoader(getActivity());
        loader.setTitle(mTitle);
        loader.setArtist(mArtist);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Song>> listLoader, List<Song> songs) {
        if (songs != null) {
            try {
                double mDuration = songs.get(0).getDuration();
                double min = mDuration / 60;
                double sec = mDuration - (((int) min) * 60);
                mTVDuration.setText("Durée : " + String.valueOf((int) min) + " min " + String.valueOf((int) sec));
            } catch (EchoNestException e) {
                e.printStackTrace();
            }
            try {
                double mTempo = songs.get(0).getTempo();
                mTVTempo.setText("Tempo : " + String.valueOf((int) mTempo));
            } catch (EchoNestException e) {
                e.printStackTrace();
            }
            try {
                double mDancability = songs.get(0).getDanceability();
                mPBDancability.setProgress((int)(mDancability * 100));
            } catch (EchoNestException e) {
                e.printStackTrace();
            }
            try {
                double mEnergy = songs.get(0).getEnergy();
                mPBEnergy.setProgress((int)(mEnergy * 100));
            } catch (EchoNestException e) {
                e.printStackTrace();
            }
            try {
                double mHotttnesss = songs.get(0).getSongHotttnesss();
                mPBHotttnesss.setProgress((int)(mHotttnesss * 100));
            } catch (EchoNestException e) {
                e.printStackTrace();
            }
            String mCover = songs.get(0).getString("tracks[0].release_image");
            mCover.toString();
            if(mCover != null && mCover != "") {
                Picasso.with(getActivity().getApplicationContext()).load(mCover).into(mIVCover);
            }
        } else {
            // API error
            Toast toast = Toast.makeText(getActivity(), getString(R.string.error_api), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Song>> listLoader) {

    }


    /***************************************************
     * INTERFACE
     ***************************************************/
    public interface OnSongCardListener {
        void onGoToArtistButtonClicked(Song song);
    }
}
