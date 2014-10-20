package playlists;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;

import com.marjolainevericel.senarai.R;

public class PlaylistListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static PlaylistsAdapter mPlaylistsAdapter;
    private OnPlaylistListListener mListener;
    private Button mAddButton;
    private AbsListView mListView;

    /***************************************************
     * INIT
     ***************************************************/
    public PlaylistListFragment() { }
    public static PlaylistListFragment newInstance(PlaylistsAdapter playlistsAdapter) {
        PlaylistListFragment fragment = new PlaylistListFragment();
        mPlaylistsAdapter = playlistsAdapter;
        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist_list, container, false);

        // List
        mListView = (AbsListView) view.findViewById(R.id.list);
        mListView.setAdapter(mPlaylistsAdapter);
        mListView.setOnItemClickListener(this);

        // Button "add a playlist"
        mAddButton = (Button) view.findViewById(R.id.playlist_list_add_playlist_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onGoToAddPlaylistButtonClicked();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPlaylistListListener) activity;
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (null != mListener) {
            mListener.onPlaylistClicked(mPlaylistsAdapter.getItem(position));
        }
    }


    /***************************************************
     * INTERFACE
     ***************************************************/
    public interface OnPlaylistListListener {
        void onPlaylistClicked(Playlist playlist);
        void onGoToAddPlaylistButtonClicked();
    }
}
