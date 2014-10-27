package artist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.echonest.api.v4.Image;
import com.marjolainevericel.senarai.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistCardFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Artist>> {

    private static final int ARTIST_LOADER_ID = 16;
    private LayoutInflater mInflater;
    static String mName;
    private ProgressBar mPBHotttnesss;
    private LinearLayout mLayoutImages;
    private LinearLayout mLayoutSongs;


    /***************************************************
     * INIT
     ***************************************************/
    public ArtistCardFragment() { }
    public static ArtistCardFragment newInstance(Song song) {
        ArtistCardFragment fragment = new ArtistCardFragment();
        mName = song.getArtistName();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(ARTIST_LOADER_ID, null, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mName = null;
        mPBHotttnesss = null;
        mLayoutSongs = null;
        mLayoutImages = null;
    }


    /***************************************************
     * VIEW
     ***************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        View view = inflater.inflate(R.layout.card_artist, container, false);

        ((TextView) view.findViewById(R.id.card_artist_name)).setText(mName);
        mPBHotttnesss = (ProgressBar) view.findViewById(R.id.card_artist_hotttnesss);
        mLayoutImages = (LinearLayout) view.findViewById(R.id.card_artist_pictures);
        mLayoutSongs = (LinearLayout) view.findViewById(R.id.card_artist_songs);

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
            try {
                double mHotttnesss = artists.get(0).getHotttnesss();
                mPBHotttnesss.setProgress((int)(mHotttnesss * 100));
            } catch (EchoNestException e) {
                e.printStackTrace();
            }
            try {
                List<Song> mSongs = artists.get(0).getSongs();
                int max = 5;
                if(mSongs.size() < 5) {
                    max = mSongs.size();
                }
                for (int i = 0 ; i < max ; i++) {
                    Song song = mSongs.get(i);
                    View view = mInflater.inflate(R.layout.simple_list_item, null);
                    ((TextView) view.findViewById(R.id.text1)).setText(song.getTitle());
                    mLayoutSongs.addView(view);
                }
            } catch (EchoNestException e) {
                e.printStackTrace();
            }
            try {
                List<Image> mImages = artists.get(0).getImages();
                int max = 5;
                if(mImages.size() < 5) {
                    max = mImages.size();
                }
                for (int i = 0 ; i < max ; i++) {
                    String imgURL = mImages.get(i).getURL();
                    View view = mInflater.inflate(R.layout.artist_image, null);
                    ImageView imgView = (ImageView) view.findViewById(R.id.imageView);
                    Picasso.with(getActivity().getApplicationContext()).load(imgURL).into(imgView);
                    mLayoutImages.addView(view);
                }
            } catch (EchoNestException e) {
                e.printStackTrace();
            }
        } else {
            // API error
            Toast toast = Toast.makeText(getActivity(), getString(R.string.error_api), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Artist>> listLoader) {

    }
}
