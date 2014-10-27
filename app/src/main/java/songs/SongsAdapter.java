package songs;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.echonest.api.v4.Song;
import com.marjolainevericel.senarai.R;

public class SongsAdapter extends ArrayAdapter<Song> {

    private OnSongsAdapterListener mListener;
    private final LayoutInflater mInflater;

    public SongsAdapter(Context context, Activity activity) {
        super(context, android.R.layout.simple_list_item_2);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try {
            mListener = (OnSongsAdapterListener) activity;
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

        final Song song = getItem(position);

        // View's elements
        view.findViewById(R.id.simle_list_item_container).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mListener.onSongListClicked(song);
            }
        });
        ((TextView) view.findViewById(R.id.text1)).setText(song.getTitle());
        ((TextView) view.findViewById(R.id.text2)).setText(song.getArtistName());

        return view;
    }


    /***************************************************
     * INTERFACE
     ***************************************************/
    public interface OnSongsAdapterListener {
        public void onSongListClicked(Song song);
    }
}
