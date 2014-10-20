package playlists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.echonest.api.v4.Song;
import com.marjolainevericel.senarai.R;


public class PlaylistsAdapter extends ArrayAdapter<Playlist> {

    private final LayoutInflater mInflater;

    public PlaylistsAdapter(Context context, int resource) {
        super(context, resource);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null)
        {
            view = mInflater.inflate(R.layout.card_playlist, parent, false);
        }
        else
        {
            view = convertView;
        }

        Playlist playlist = getItem(position);

        ((TextView) view.findViewById(R.id.playlist_card_title)).setText(playlist.getTitle());
        ((TextView) view.findViewById(R.id.playlist_card_description)).setText(playlist.getDescription());

        return view;
    }
}
