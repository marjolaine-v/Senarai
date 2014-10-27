package songs;

import android.content.Context;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;

import java.util.List;

import server.EchoNestWrapper;

public class SongLoader extends android.support.v4.content.AsyncTaskLoader<List<Song>> {

    private String mTitle;
    private String mArtist;
    private List<Song> mList;

    public SongLoader(Context context) {
        super(context);
    }

    @Override
    public List<Song> loadInBackground() {
        try {
            mList = EchoNestWrapper.with(getContext()).getSongsByKeys(mTitle, mArtist);
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

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setArtist(String artist) {
        this.mArtist = artist;
    }
}
