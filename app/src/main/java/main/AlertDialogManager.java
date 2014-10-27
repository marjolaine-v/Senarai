package main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import playlists.PlaylistCustom;

public class AlertDialogManager {

    private AlertDialog.Builder mAlertDialogBuilder;
    private OnAlertListener mListener;

    public void newInstance(Activity activity) {
        mAlertDialogBuilder = new AlertDialog.Builder(activity);
        try {
            mListener = (OnAlertListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public AlertDialog.Builder getBuilder() {
        return mAlertDialogBuilder;
    }

    public void setAlert(int type) {
        switch (type) {
            case 0:
                mAlertDialogBuilder.setTitle("Quel type de playlist voulez-vous ajouter ?");
                mAlertDialogBuilder.setCancelable(true)
                        .setNegativeButton("Vide", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mListener.onAlertPlaylistEmptyAddClicked();
                            }
                        })
                        .setNeutralButton("Aléatoire", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mListener.onAlertPlaylistRandomAddClicked();
                            }
                        })
                        .setPositiveButton("Personnalisée", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mListener.onAlertPlaylistAddClicked();
                            }
                        });
        }
    }


    /***************************************************
     * INTERFACE
     ***************************************************/
    public interface OnAlertListener {
        void onAlertPlaylistEmptyAddClicked();
        void onAlertPlaylistRandomAddClicked();
        void onAlertPlaylistAddClicked();
    }
}
