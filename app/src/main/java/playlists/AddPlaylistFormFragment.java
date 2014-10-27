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
import android.widget.SeekBar;
import android.widget.Toast;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.marjolainevericel.senarai.R;

import java.util.List;

import server.EchoNestWrapper;

public class AddPlaylistFormFragment extends Fragment {

    private OnPlaylistAddFormListener mListener;
    private EditText mEditTitle;
    private String mTitle;
    private EditText mEditDescription;
    private String mDescription;
    private EditText mEditArtist;
    private String mArtist;
    private EditText mEditSong;
    private String mSong;
    /*private Spinner mType;*/
    private SeekBar mSBDancability;
    private int mDancability;
    private SeekBar mSBTempo;
    private int mTempo;
    private SeekBar mSBEnergy;
    private int mEnergy;
    private SeekBar mSBMode;
    private int mMode;
    private EditText mEditNumberResults;
    private int mNumberResults;
    private Button mAddButton;
    Context context;


    /***************************************************
     * INIT
     ***************************************************/
    public AddPlaylistFormFragment() { }
    public static AddPlaylistFormFragment newInstance(Context context) {
        AddPlaylistFormFragment fragment = new AddPlaylistFormFragment();
        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist_add_form, container, false);

        context = getActivity().getApplicationContext();

        mEditTitle = ((EditText) view.findViewById(R.id.playlist_add_form_title));
        mEditDescription = ((EditText) view.findViewById(R.id.playlist_add_form_description));
        mEditArtist = ((EditText) view.findViewById(R.id.playlist_add_form_artist));
        mEditSong = ((EditText) view.findViewById(R.id.playlist_add_form_song));
        /*mType = ((Spinner) view.findViewById(R.id.playlist_add_form_spinner_types));*/
        mSBDancability = ((SeekBar) view.findViewById(R.id.playlist_add_form_seekbar_dancability));
        mSBTempo = ((SeekBar) view.findViewById(R.id.playlist_add_form_seekbar_tempo));
        mSBEnergy = ((SeekBar) view.findViewById(R.id.playlist_add_form_seekbar_energy));
        mSBMode = ((SeekBar) view.findViewById(R.id.playlist_add_form_seekbar_mode));
        mEditNumberResults = ((EditText) view.findViewById(R.id.playlist_add_form_number_results));

        mAddButton = (Button) view.findViewById(R.id.playlist_add_form_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                if(mEditTitle.getText().toString().matches("")) {
                    Toast.makeText(context, "Merci d'ajouter un titre à votre playlist", Toast.LENGTH_LONG).show();
                }
                else if(mEditArtist.getText().toString().matches("")) {
                    Toast.makeText(context, "Merci d'ajouter un artiste dont la playlist s'inspirera", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Appel d'un loader", Toast.LENGTH_LONG).show();

                    mTitle = mEditTitle.getText().toString();
                    mDescription = mEditDescription.getText().toString();
                    mArtist = mEditArtist.getText().toString();
                    mSong = mEditSong.getText().toString();
                    mDancability = mSBDancability.getProgress();
                    mTempo = mSBTempo.getProgress();
                    mEnergy = mSBEnergy.getProgress();
                    mMode = mSBMode.getProgress();
                    mNumberResults = Integer.valueOf(mEditNumberResults.getText().toString());

                    new PlaylistAsyncTask().execute();
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
        void onAddPlaylistButtonClicked(String title, String description, List<Song> list);
    }


    /***************************************************
     * ASYNCTASK
     ***************************************************/
    public class PlaylistAsyncTask extends AsyncTask<Void, Void, Void> {
        List<Song> list;
        @Override
        protected Void doInBackground(Void... params) {
            try{
                list = EchoNestWrapper.with(getActivity().getApplicationContext()).getCustomPlaylist(mArtist, mSong, mDancability, mTempo, mEnergy, mMode, mNumberResults);
            } catch (EchoNestException e) {
                list = null;
                e.printStackTrace();
            }
            mListener.onAddPlaylistButtonClicked(mTitle, mDescription, list);
            return null;
        }
    }
}
