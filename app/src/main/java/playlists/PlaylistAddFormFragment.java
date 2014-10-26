package playlists;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.marjolainevericel.senarai.R;

public class PlaylistAddFormFragment extends Fragment {

    private OnPlaylistAddFormListener mListener;
    private Button mAddButton;
    private EditText mEditTitle;
    private EditText mEditDescription;
    private CheckBox mCheckboxRandom;
    Context context;


    /***************************************************
     * INIT
     ***************************************************/
    public PlaylistAddFormFragment() { }
    public static PlaylistAddFormFragment newInstance() {
        PlaylistAddFormFragment fragment = new PlaylistAddFormFragment();
        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist_add_form, container, false);

        context = getActivity().getApplicationContext();

        mEditTitle = ((EditText) view.findViewById(R.id.playlist_form_title));
        mEditDescription = ((EditText) view.findViewById(R.id.playlist_form_description));
        mCheckboxRandom = ((CheckBox) view.findViewById(R.id.playlist_form_checked));

        mAddButton = (Button) view.findViewById(R.id.playlist_add_form_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                if(!mEditTitle.getText().toString().matches("")) {
                    mListener.onAddPlaylistButtonClicked(mEditTitle.getText().toString(), mEditDescription.getText().toString(), mCheckboxRandom.isChecked());
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
            mListener = (OnPlaylistAddFormListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /***************************************************
     * HIDE KEYBOARD
     ***************************************************/
    private void hideKeyboard(View view) {
        view.clearFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        InputMethodManager inputManager = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /***************************************************
     * INTERFACE
     ***************************************************/
    public interface OnPlaylistAddFormListener {
        void onAddPlaylistButtonClicked(String title, String description, Boolean isRandomPlaylist);
    }
}
