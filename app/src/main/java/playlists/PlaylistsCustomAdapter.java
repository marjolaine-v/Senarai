package playlists;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.marjolainevericel.senarai.R;


public class PlaylistsCustomAdapter extends ArrayAdapter<PlaylistCustom> {

    private OnPlaylistsCustomAdapterListener mListener;
    private final LayoutInflater mInflater;

    public PlaylistsCustomAdapter(Context context, int resource, Activity activity) {
        super(context, resource);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try {
            mListener = (OnPlaylistsCustomAdapterListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSongsAdapterListener");
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null)
        {
            view = mInflater.inflate(R.layout.simple_list_item, parent, false);
        }
        else
        {
            view = convertView;
        }

        final PlaylistCustom playlist = getItem(position);

        // View's elements
        view.findViewById(R.id.simle_list_item_container).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mListener.onPlaylistClicked(playlist);
            }
        });
        ((TextView) view.findViewById(R.id.text1)).setText(playlist.getTitle());
        ((TextView) view.findViewById(R.id.text2)).setText(playlist.getDescription());

        return view;
    }


    /***************************************************
     * INTERFACE
     ***************************************************/
    public interface OnPlaylistsCustomAdapterListener {
        public void onPlaylistClicked(PlaylistCustom playlistCustom);
    }
}
