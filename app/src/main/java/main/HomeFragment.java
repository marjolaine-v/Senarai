package main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.marjolainevericel.senarai.R;

public class HomeFragment extends Fragment {

    private Button mPlaylistsListButton;
    private Button mPlaylistAddButton;
    private Button mSearchButton;
    private OnHomeListener mListener;


    /***************************************************
     * INIT
     ***************************************************/
    public HomeFragment() {}
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Button "Créer une playlist"
        mPlaylistAddButton = (Button) view.findViewById(R.id.home_add_playlist_button);
        mPlaylistAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onGoToAddPlaylistButtonClicked();
            }
        });

        // Button "Mes Playlists"
        mPlaylistsListButton = (Button) view.findViewById(R.id.home_playlists_list_button);
        mPlaylistsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onGoToPlaylistsListButtonClicked();
            }
        });

        // Button "Recherche"
        mSearchButton = (Button) view.findViewById(R.id.home_search_button);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onGoToSearchButtonClicked();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnHomeListener) activity;
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
    public interface OnHomeListener {
        void onGoToAddPlaylistButtonClicked();
        void onGoToPlaylistsListButtonClicked();
        void onGoToSearchButtonClicked();
    }
}
