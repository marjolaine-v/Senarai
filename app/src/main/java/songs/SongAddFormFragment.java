package songs;

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
import android.widget.EditText;
import android.widget.Toast;

import com.marjolainevericel.senarai.R;

public class SongAddFormFragment extends Fragment {

    private EditText mEditTitle;
    private EditText mEditArtist;
    private static Button mSearchButton;
    private static OnSongAddFormListener mListener;
    Context context;


    /***************************************************
     * INIT
     ***************************************************/
    public SongAddFormFragment() { }
    public static SongAddFormFragment newInstance() {
        SongAddFormFragment fragment = new SongAddFormFragment();
        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_add_form, container, false);

        context = getActivity().getApplicationContext();

        mEditTitle = ((EditText) view.findViewById(R.id.song_add_form_title));
        mEditArtist = ((EditText) view.findViewById(R.id.song_add_form_artist_name));

        mSearchButton = ((Button) view.findViewById(R.id.song_add_button));
        mSearchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                String title = mEditTitle.getText().toString();
                String artist = mEditArtist.getText().toString();

                if(!title.matches("") || !artist.matches("")) {
                    mListener.onAddSongButtonClicked(title, artist);
                }
                else {
                    Toast.makeText(context, "Merci de remplir au moins un champ", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSongAddFormListener) activity;
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
    public interface OnSongAddFormListener {
        public void onAddSongButtonClicked(String title, String artist);
    }
}
