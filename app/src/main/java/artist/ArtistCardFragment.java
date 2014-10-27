package artist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.marjolainevericel.senarai.R;

import java.util.List;

public class ArtistCardFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Artist>> {

    private static final int ARTIST_LOADER_ID = 16;
    private static Song mSong;
    static Artist mArtist;
    static String mName;
    static double mHotttness;
    static List<String> mGenres;


    /***************************************************
     * INIT
     ***************************************************/
    public ArtistCardFragment() { }
    public static ArtistCardFragment newInstance(Song song) {
        ArtistCardFragment fragment = new ArtistCardFragment();
        mSong = song;
        mName = song.getArtistName();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getLoaderManager().initLoader(ARTIST_LOADER_ID, null, this);
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_artist, container, false);

        ((TextView) view.findViewById(R.id.card_artist_name)).setText(mName);

        return view;
    }


    /***************************************************
     * LOADER
     ***************************************************/
    @Override
    public Loader<List<Artist>> onCreateLoader(int i, Bundle bundle) {
        ArtistLoader loader = new ArtistLoader(getActivity());
        loader.setName(mName);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Artist>> listLoader, List<Artist> artists) {
        if (artists != null) {
            mArtist = artists.get(0);
            try {
                mHotttness = artists.get(0).getHotttnesss();
            } catch (EchoNestException e) {
                e.printStackTrace();
            }
        } else {
            // API error
            Toast toast = Toast.makeText(getActivity(), getString(R.string.error_api), Toast.LENGTH_LONG);
            toast.show();
            mArtist = null;
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Artist>> listLoader) {
        mArtist = null;
    }
}
