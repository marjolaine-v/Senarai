package songs;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.echonest.api.v4.Song;
import com.marjolainevericel.senarai.R;

public class SongsAdapter extends ArrayAdapter<Song> {

    private final LayoutInflater mInflater;

    public SongsAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null)
        {
            view = mInflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        else
        {
            view = convertView;
        }

        Song song = getItem(position);

        // View's elements
        ((TextView) view.findViewById(android.R.id.text1)).setText(song.getTitle());
        ((TextView) view.findViewById(android.R.id.text2)).setText(song.getArtistName());

        return view;
    }
}
