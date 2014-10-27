package songs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.echonest.api.v4.Song;
import com.marjolainevericel.senarai.R;

import playlists.PlaylistCustom;

public class SongListFragment extends Fragment implements AbsListView.OnItemClickListener {

    private static PlaylistCustom mPlaylistCustom;
    private OnSongListListener mListener;
    //private AbsListView mListView;
    private LinearLayout mLayout;
    private static SongsAdapter mSongs;

    // My vars
    private static Button mAddButton;


    /***************************************************
     * INIT
     ***************************************************/
    public SongListFragment() { }
    public static SongListFragment newInstance(Context context, Activity activity, PlaylistCustom playlist) {
        SongListFragment fragment = new SongListFragment();
        mPlaylistCustom = playlist;
        mSongs = new SongsAdapter(context, activity);
        if(playlist.getSongs() != null) {
            mSongs = playlist.getSongs();
        }
        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_list, container, false);

        // Set the adapter
        /*mListView = (AbsListView) view.findViewById(R.id.list);
        mListView.setAdapter(mSongs);
        mListView.setOnItemClickListener(this);*/
        mLayout = (LinearLayout)view.findViewById(R.id.list);
        for (int i = 0 ; i < mSongs.getCount() ; i++) {
            View item = mSongs.getView(i, null, null);
            mLayout.addView(item);
        }

        mAddButton = ((Button) view.findViewById(R.id.song_list_add_song_button));
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onGoToAddSongButtonClicked();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSongListListener) activity;
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

        }
    }


    /***************************************************
     * INTERFACE
     ***************************************************/
    public interface OnSongListListener {
        public void onSongListClicked(Song song);
        public void onGoToAddSongButtonClicked();
    }

}
