package com.marjolainevericel.senarai.playlist;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.echonest.api.v4.Playlist;
import com.marjolainevericel.senarai.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PlaylistFormFragment extends Fragment {

    private Button mAddButton;
    private EditText mEditTitle;
    private EditText mEditDescription;
    OnPlaylistFormListener mListener;
    Fragment self;


    /***************************************************
     * INITIALISATION
     ***************************************************/
    public PlaylistFormFragment() {
    }
    public static PlaylistFormFragment newInstance() {
        PlaylistFormFragment fragment = new PlaylistFormFragment();
        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.playlist_form, container, false);

        mEditTitle = ((EditText) view.findViewById(R.id.playlist_form_title));
        mEditDescription = ((EditText) view.findViewById(R.id.playlist_form_description));

        mAddButton = ((Button) view.findViewById(R.id.playlist_form_launch));
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditTitle.clearFocus();
                Log.d("APP", ">> mTitle >>>>>>>>>>>>>>>>>> " + mEditTitle.getText());
                if(!mEditTitle.getText().toString().matches("")) {
                    mListener.onButtonAddPlaylistClicked(mEditTitle.getText().toString(), mEditDescription.getText().toString());
                }
                else {
                    Log.d("APP", ">> >> >> Merci d'ajouter un titre.");
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPlaylistFormListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    /***************************************************
     * LISTENERS
     ***************************************************/
    public interface OnPlaylistFormListener {
        void onButtonAddPlaylistClicked(String title, String description);
    }
}
