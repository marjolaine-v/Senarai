package playlists;

import songs.SongsAdapter;

public class PlaylistCustom {

    protected String mTitle;
    protected String mDescription;
    protected SongsAdapter mSongs;

    public PlaylistCustom(String title) {
        mTitle = title;
        mDescription = "";
        mSongs = null;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getDescription() {
        return this.mDescription;
    }
    public void setDescription(String description) {
        mDescription = description;
    }
    public void setSongs(SongsAdapter songs) { mSongs = songs; }

    public SongsAdapter getSongs() {
        return mSongs;
    }
}
