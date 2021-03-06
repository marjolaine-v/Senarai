package playlists;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.marjolainevericel.senarai.R;

public class PlaylistCardFragment extends Fragment {

    private static PlaylistCustom mPlaylist;
    private static Button mRemoveButton;
    private OnPlaylistCardListener mListener;


    /***************************************************
     * INIT
     ***************************************************/
    public PlaylistCardFragment() { }
    public static PlaylistCardFragment newInstance(PlaylistCustom playlist) {
        PlaylistCardFragment fragment = new PlaylistCardFragment();
        mPlaylist = playlist;
        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_playlist, container, false);

        // View's elements
        ((TextView) view.findViewById(R.id.card_playlist_title)).setText(mPlaylist.getTitle());
        ((TextView) view.findViewById(R.id.card_playlist_description)).setText(mPlaylist.getDescription());
        if(mPlaylist.getSongs() != null) {
            String _songs = "chansons";
            if(mPlaylist.getSongs().getCount() <= 1) { _songs = "chanson"; }
            ((TextView) view.findViewById(R.id.card_playlist_number_songs)).setText(mPlaylist.getSongs().getCount() + " " + _songs);
        }

        // Button "REMOVE"
        mRemoveButton = (Button) view.findViewById(R.id.card_playlist_remove_button);
        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onRemovePlaylistButtonClicked(mPlaylist);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPlaylistCardListener) activity;
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
    public interface OnPlaylistCardListener {
        void onRemovePlaylistButtonClicked(PlaylistCustom playlist);
    }
}
