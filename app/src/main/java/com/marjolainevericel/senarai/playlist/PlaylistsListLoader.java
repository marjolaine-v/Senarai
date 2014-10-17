package com.marjolainevericel.senarai.playlist;

import android.content.Context;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Playlist;

import java.util.HashMap;

import server.EchoNestWrapper;

/**
 * Created by Marjolaine on 16/10/2014.
 */
public class PlaylistsListLoader extends android.support.v4.content.AsyncTaskLoader<Playlist> {
    private int mResults;
    private String mArtist;
    private Playlist mPlaylist;

    public PlaylistsListLoader(Context context) {
        super(context);
    }

    @Override
    public Playlist loadInBackground() {
        try {
            mPlaylist = EchoNestWrapper.with(getContext()).getArtistRadio(mResults, mArtist);
        } catch (EchoNestException e) {
            e.printStackTrace();
            mPlaylist = null;
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

    public void setArtist(String artist) {
        this.mArtist = artist;
    }

    public void setResults(int results) {
        this.mResults = results;
    }
}
