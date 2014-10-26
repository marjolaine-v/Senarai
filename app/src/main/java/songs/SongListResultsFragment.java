package songs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.echonest.api.v4.Song;
import com.marjolainevericel.senarai.R;

import java.util.List;

public class SongListResultsFragment extends Fragment implements AbsListView.OnItemClickListener, android.support.v4.app.LoaderManager.LoaderCallbacks<List<Song>> {


    private static SongsAdapter mSongsAdapter;
    private OnSongListResultsListener mListener;
    private AbsListView mListView;
    private static final int SONGS_LOADER_ID = 12;

    static String mTitle;
    static String mArtist;


    /***************************************************
     * INIT
     ***************************************************/
    public SongListResultsFragment() { }
    public static SongListResultsFragment newInstance(String title, String artist) {
        SongListResultsFragment fragment = new SongListResultsFragment();
        mTitle = title;
        mArtist = artist;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSongsAdapter = new SongsAdapter(getActivity());
        getLoaderManager().initLoader(SONGS_LOADER_ID, null, this);
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getLoaderManager().initLoader(SONGS_LOADER_ID, null, this);
        View view = inflater.inflate(R.layout.fragment_song_list_results, container, false);

        // List
        mListView = (AbsListView) view.findViewById(R.id.list);
        mListView.setAdapter(mSongsAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSongListResultsListener) activity;
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
     * CLICKS
     ***************************************************/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            mListener.onSongListResultsClicked(mSongsAdapter.getItem(position));
        }
    }


    /***************************************************
     * LOADER
     ***************************************************/
    @Override
    public Loader<List<Song>> onCreateLoader(int i, Bundle bundle) {
        SongsLoader loader = new SongsLoader(getActivity());
        loader.setTitle(mTitle);
        loader.setArtist(mArtist);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Song>> listLoader, List<Song> songs) {
        mSongsAdapter.clear();
        if (songs != null) {
            mSongsAdapter.addAll(songs);
        } else {
            // API error
            Toast toast = Toast.makeText(getActivity(), getString(R.string.error_api), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Song>> listLoader) {
        mSongsAdapter.clear();
    }


    /***************************************************
     * INTERFACE
     ***************************************************/
    public interface OnSongListResultsListener {
        public void onSongListResultsClicked(Song song);
    }
}
