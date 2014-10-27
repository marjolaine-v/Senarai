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
import android.widget.EditText;
import android.widget.Toast;

import com.marjolainevericel.senarai.R;

public class AddPlaylistEmptyFormFragment extends Fragment {

    private AddPlaylistFormFragment.OnPlaylistAddFormListener mListener;
    private EditText mEditTitle;
    private String mTitle;
    private EditText mEditDescription;
    private String mDescription;
    private Button mAddButton;
    Context context;


    /***************************************************
     * INIT
     ***************************************************/
    public AddPlaylistEmptyFormFragment() { }
    public static AddPlaylistEmptyFormFragment newInstance() {
        AddPlaylistEmptyFormFragment fragment = new AddPlaylistEmptyFormFragment();
        return fragment;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist_empty_add_form, container, false);

        context = getActivity().getApplicationContext();

        mEditTitle = ((EditText) view.findViewById(R.id.playlist_empty_add_form_title));
        mEditDescription = ((EditText) view.findViewById(R.id.playlist_empty_add_form_description));

        mAddButton = (Button) view.findViewById(R.id.playlist_empty_add_form_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                if(mEditTitle.getText().toString().matches("")) {
                    Toast.makeText(context, "Merci d'ajouter un titre à votre playlist", Toast.LENGTH_LONG).show();
                }
                else {
                    mTitle = mEditTitle.getText().toString();
                    mDescription = mEditDescription.getText().toString();

                    mListener.onAddPlaylistButtonClicked(mTitle, mDescription, null);
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
}
