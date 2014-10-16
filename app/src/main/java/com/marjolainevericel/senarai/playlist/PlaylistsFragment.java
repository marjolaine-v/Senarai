package com.marjolainevericel.senarai.playlist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.echonest.api.v4.Playlist;
import com.marjolainevericel.senarai.R;


public class PlaylistsFragment extends Fragment implements AbsListView.OnItemClickListener, android.support.v4.app.LoaderManager.LoaderCallbacks<Playlist> {

    private static final String ARG_RESULTS = "results";
    private static final String ARG_ARTIST = "artist";
    private static final int PLAYLISTS_LOADER_ID = 12;

    private int mResults;
    private String mArtist;

    private OnPlaylistClickedListener mListener;
    private AbsListView mListView;
    private PlaylistsAdapter mAdapter;


    /***************************************************
     * INITIALISATION
     ***************************************************/
    public PlaylistsFragment() {
    }

    public static PlaylistsFragment newInstance(int results, String artist) {
        PlaylistsFragment fragment = new PlaylistsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RESULTS, results);
        args.putString(ARG_ARTIST, artist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mResults = getArguments().getInt(ARG_RESULTS);
            mArtist = getArguments().getString(ARG_ARTIST);
        }

        mAdapter = new PlaylistsAdapter(getActivity());
        getLoaderManager().initLoader(PLAYLISTS_LOADER_ID, null, this);
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_playlists, container, false);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPlaylistClickedListener) activity;
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
    public android.support.v4.content.Loader<Playlist> onCreateLoader(int i, Bundle bundle) {
        PlaylistsLoader loader = new PlaylistsLoader(getActivity());
        loader.setArtist(mArtist);
        loader.setResults(mResults);
        return loader;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Playlist> playlistLoader, Playlist playlist) {
        mAdapter.clear();
        if (playlist != null) {
            mAdapter.addAll(playlist);
        } else {
            // API error
            Toast.makeText(getActivity(), "Errer API !", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Playlist> playlistLoader) {
        mAdapter.clear();
    }


    /***************************************************
     * LISTENERS
     ***************************************************/
    public interface OnPlaylistClickedListener {
        void onPlaylistClicked(Playlist playlist);
    }


    /***************************************************
     * CLICKS
     ***************************************************/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            mListener.onPlaylistClicked(mAdapter.getItem(position));
        }
    }
}
