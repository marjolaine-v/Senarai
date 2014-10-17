package playlists;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.marjolainevericel.senarai.R;

public class PlaylistAddFormFragment extends Fragment {

    private OnPlaylistAddFormListener mListener;
    private Button mAddButton;
    private EditText mEditTitle;
    private EditText mEditDescription;
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
        View view;
        view = inflater.inflate(R.layout.fragment_playlist_add_form, container, false);

        context = getActivity().getApplicationContext();

        mEditTitle = ((EditText) view.findViewById(R.id.playlist_form_title));
        mEditDescription = ((EditText) view.findViewById(R.id.playlist_form_description));

        mAddButton = (Button) view.findViewById(R.id.playlist_add_form_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mEditTitle.getText().toString().matches("")) {
                    mListener.onAddPlaylistButtonClicked(mEditTitle.getText().toString(), mEditDescription.getText().toString());
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
    public interface OnPlaylistAddFormListener {
        void onAddPlaylistButtonClicked(String title, String description);
    }
}
