package playlists;

import com.echonest.api.v4.Song;

public class PlaylistCustom {

    protected String mTitle;
    protected String mDescription;
    /*protected playlists.SongsAdapter songs;*/

    public PlaylistCustom(String title) {
        mTitle = title;
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
}
