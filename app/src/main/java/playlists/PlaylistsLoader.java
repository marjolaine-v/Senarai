package playlists;

import android.content.Context;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Playlist;
import com.echonest.api.v4.Song;

import java.util.List;

import server.EchoNestWrapper;

public class PlaylistsLoader extends android.support.v4.content.AsyncTaskLoader<Playlist> {

    private String mTitle;
    private String mArtist;
    private Playlist mPlaylist;

    public PlaylistsLoader(Context context) {
        super(context);
    }

    @Override
    public Playlist loadInBackground() {
        try {
            mPlaylist = EchoNestWrapper.with(getContext()).getArtistRadio(10, mArtist);
        } catch (EchoNestException e) {
            mPlaylist = null;
            e.printStackTrace();
        }
        return mPlaylist;
    }

    @Override
    protected void onStartLoading() {
        if (mPlaylist != null) {
            deliverResult(mPlaylist);
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
