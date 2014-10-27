package playlists;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
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

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.marjolainevericel.senarai.R;

import java.util.List;

import server.EchoNestWrapper;

public class AddPlaylistRandomFormFragment extends Fragment {

    private AddPlaylistFormFragment.OnPlaylistAddFormListener mListener;
    private EditText mEditTitle;
    private String mTitle;
    private EditText mEditDescription;
    private String mDescription;
    private EditText mEditNumberResults;
    private int mNumberResults;
    private Button mAddButton;
    static Context mContext;


    /***************************************************
     * INIT
     ***************************************************/
    public AddPlaylistRandomFormFragment() { }
    public static AddPlaylistRandomFormFragment newInstance(Context context) {
        AddPlaylistRandomFormFragment fragment = new AddPlaylistRandomFormFragment();
        mContext = context;
        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist_random_add_form, container, false);

        mEditTitle = ((EditText) view.findViewById(R.id.playlist_empty_add_form_title));
        mEditDescription = ((EditText) view.findViewById(R.id.playlist_empty_add_form_description));
        mEditNumberResults = ((EditText) view.findViewById(R.id.playlist_empty_add_form_number_results));

        mAddButton = (Button) view.findViewById(R.id.playlist_empty_add_form_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                if(mEditTitle.getText().toString().matches("")) {
                    Toast.makeText(mContext, "Merci d'ajouter un titre à votre playlist", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Enregistrement de la playlist", Toast.LENGTH_LONG).show();

                    mTitle = mEditTitle.getText().toString();
                    mDescription = mEditDescription.getText().toString();
                    mNumberResults = Integer.valueOf(mEditNumberResults.getText().toString());

                    new PlaylistRandomAsyncTask().execute();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AddPlaylistFormFragment.OnPlaylistAddFormListener) activity;
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
     * ASYNCTASK
     ***************************************************/
    public class PlaylistRandomAsyncTask extends AsyncTask<Void, Void, Void> {
        List<Song> list;
        @Override
        protected Void doInBackground(Void... params) {
            try{
                list = EchoNestWrapper.with(getActivity().getApplicationContext()).getRandomPlaylist(mNumberResults);
            } catch (EchoNestException e) {
                list = null;
                e.printStackTrace();
            }
            mListener.onAddPlaylistButtonClicked(mTitle, mDescription, list);
            return null;
        }
    }
}
