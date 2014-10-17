package com.marjolainevericel.senarai.playlist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.echonest.api.v4.Playlist;
import com.marjolainevericel.senarai.R;

import java.util.HashMap;


public class PlaylistsListFragment extends Fragment implements AbsListView.OnItemClickListener, android.support.v4.app.LoaderManager.LoaderCallbacks<Playlist> {

    private static final String ARG_RESULTS = "results";
    private static final String ARG_ARTIST = "artist";
    private static final int PLAYLISTS_LOADER_ID = 12;

    private int mResults;
    private String mArtist;
    private Button mAddButton;

    private OnPlaylistsListListener mListener;
    private AbsListView mListView;
    private PlaylistsListAdapter mAdapter;


    /***************************************************
     * INITIALISATION
     ***************************************************/
    public PlaylistsListFragment() {
    }

    public static PlaylistsListFragment newInstance() {
        PlaylistsListFragment fragment = new PlaylistsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RESULTS, 10);
        args.putString(ARG_ARTIST, "Linkin Park");
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

        mAdapter = new PlaylistsListAdapter(getActivity());
        getLoaderManager().initLoader(PLAYLISTS_LOADER_ID, null, this);
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_playlists_list, container, false);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        mAddButton = ((Button) view.findViewById(R.id.button_playlist_add));
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonGoToFormAddPlaylistClicked();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPlaylistsListListener) activity;
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
        PlaylistsListLoader loader = new PlaylistsListLoader(getActivity());
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
     * CLICKS
     ***************************************************/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            mListener.onPlaylistClicked(mAdapter.getItem(position));
        }
    }


    /***************************************************
     * LISTENERS
     ***************************************************/
    public interface OnPlaylistsListListener {
        void onPlaylistClicked(Playlist playlist);
        void onButtonGoToFormAddPlaylistClicked();
    }
}
