package com.marjolainevericel.senarai.playlist;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.echonest.api.v4.Playlist;
import com.marjolainevericel.senarai.R;

/**
 * A simple {@link Fragment} subclass.
 *
 *
public class PlaylistFormFragment extends Fragment {

    private Button mAddButton;
    private EditText mEditTitle;
    private EditText mEditDescription;
    OnPlaylistFormListener mListener;
    Context context;


    /***************************************************
     * INITIALISATION
     ***************************************************
    public PlaylistFormFragment() {
    }
    public static PlaylistFormFragment newInstance() {
        PlaylistFormFragment fragment = new PlaylistFormFragment();
        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.playlist_form, container, false);

        mEditTitle = ((EditText) view.findViewById(R.id.playlist_form_title));
        mEditDescription = ((EditText) view.findViewById(R.id.playlist_form_description));

        mAddButton = ((Button) view.findViewById(R.id.playlist_form_launch));
        context = getActivity().getApplicationContext();
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                if(!mEditTitle.getText().toString().matches("")) {
                    mListener.addPlaylistButtonClicked(mEditTitle.getText().toString(), mEditDescription.getText().toString());
                }
                else {
                    Toast.makeText(context, "Merci d'ajouter un titre à votre playlist", Toast.LENGTH_LONG).show();
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

    private void hideKeyboard(View view) {
        view.clearFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        InputMethodManager inputManager = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /***************************************************
     * LISTENERS
     ***************************************************
    public interface OnPlaylistFormListener {
        void addPlaylistButtonClicked(String title, String description);
    }
}
*/