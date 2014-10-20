package playlists;

public class Playlist {

    protected String mTitle;
    protected String mDescription;

    public Playlist(String title) {
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
