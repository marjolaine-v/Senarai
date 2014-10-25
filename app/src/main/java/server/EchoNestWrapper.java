package server;

import android.content.Context;

import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Params;
import com.echonest.api.v4.Playlist;
import com.echonest.api.v4.PlaylistParams;
import com.echonest.api.v4.Song;
import com.marjolainevericel.senarai.R;

import java.util.List;

/**
 * Created by Marjolaine on 16/10/2014.
 */
public class EchoNestWrapper {
    private static EchoNestWrapper sInstance;
    private EchoNestAPI mApi;

    public EchoNestWrapper(String key) {
        mApi = new EchoNestAPI(key);
    }

    public static EchoNestWrapper with(Context context) {
        if (sInstance == null) {
            sInstance = new EchoNestWrapper(context.getString(R.string.echo_nest_api));
        }
        return sInstance;
    }

    public Playlist getArtistRadio(int results, String artist) throws
            EchoNestException {
        PlaylistParams params = new PlaylistParams();
        params.addIDSpace("7digital-US"); // Get external API
        params.includeTracks(); // Garde les données dans cet appel (on ne veut pas fare un nouvel appel)
        params.addArtist(artist);
        params.setType(PlaylistParams.PlaylistType.ARTIST_RADIO);
        params.setResults(results);
        return mApi.createStaticPlaylist(params);
    }
}