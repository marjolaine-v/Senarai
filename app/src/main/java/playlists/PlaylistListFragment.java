package playlists;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.marjolainevericel.senarai.R;

public class PlaylistListFragment extends Fragment {

    private OnPlaylistListListener mListener;
    private Button mAddButton;

    /***************************************************
     * INIT
     ***************************************************/
    public PlaylistListFragment() { }
    public static PlaylistListFragment newInstance() {
        PlaylistListFragment fragment = new PlaylistListFragment();
        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_playlist_list, container, false);

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


    /***************************************************
     * LISTENERS
     ***************************************************/
    public interface OnPlaylistListListener {
        void onGoToAddPlaylistButtonClicked();
    }
}
