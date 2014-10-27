package artist;

import android.content.Context;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;

import java.util.List;

import server.EchoNestWrapper;

public class ArtistLoader extends android.support.v4.content.AsyncTaskLoader<List<Artist>> {

    private String mName;
    private List<Artist> mList;

    public ArtistLoader(Context context) {
        super(context);
    }

    @Override
    public List<Artist> loadInBackground() {
        try {
            mList = EchoNestWrapper.with(getContext()).getArtist(mName);
        } catch (EchoNestException e) {
            mList = null;
            e.printStackTrace();
        }
        return mList;
    }

    @Override
    protected void onStartLoading() {
        if (mList != null) {
            deliverResult(mList);
        } else {
            forceLoad();
        }
    }

    public void setName(String name) {
        this.mName = name;
    }
}
