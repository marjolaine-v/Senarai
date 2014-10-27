package server;

import android.content.Context;
import android.widget.Toast;

import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Playlist;
import com.echonest.api.v4.PlaylistParams;
import com.echonest.api.v4.Song;
import com.echonest.api.v4.SongParams;
import com.marjolainevericel.senarai.R;

import java.util.List;

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

    public Playlist getArtistRadio(int results, String artist) throws EchoNestException {
        PlaylistParams params = new PlaylistParams();
        params.addIDSpace("7digital-US"); // Get external API
        params.includeTracks(); // Garde les données dans cet appel (on ne veut pas fare un nouvel appel)
        params.addArtist(artist);
        params.setType(PlaylistParams.PlaylistType.ARTIST_RADIO);
        params.setResults(results);
        return mApi.createStaticPlaylist(params);
    }

    public List<Song> getCustomPlaylist(String artist, String song, int dancability, int tempo, int energy, int mode, int numberResults) throws EchoNestException {
        PlaylistParams params = new PlaylistParams();
        // Artist
        if(artist != null && artist != "") {
            params.addArtist(artist);
        }
        // Dancability
        if(dancability != 0) {
            float _dance = dancability * 1 / 100;
            float _danceMin = _dance - (float) 0.1;
            float _danceMax = _dance + (float) 0.1;
            if (_danceMin < 0) {
                _danceMin = 0;
            }
            if (_danceMax > 1) {
                _danceMax = 1;
            }
            params.setMinDanceability(_danceMin);
            params.setMaxDanceability(_danceMax);
        }
        // Tempo
        if(tempo != 0) {
            float _tempo = tempo * 500 / 100;
            float _tempoMin = _tempo - (float) 20;
            float _tempoMax = _tempo + (float) 20;
            if (_tempoMin < 0) {
                _tempoMin = 0;
            }
            if (_tempoMax > 500) {
                _tempoMax = 500;
            }
            params.setMinTempo(_tempoMin);
            params.setMaxTempo(_tempoMax);
        }
        // Energy
        if(energy != 0) {
            float _energyMin = (float)(energy * 1 / 100) - (float)0.1;
            float _energyMax = (float)(energy * 1 / 100) + (float)0.1;
            if (_energyMin < 0) {
                _energyMin = 0;
            }
            if (_energyMax > 1) {
                _energyMax = 1;
            }
            params.setMinEnergy(_energyMin);
            params.setMaxEnergy(_energyMax);
        }
        // Mode
        if(mode != 0) {
            int _mode = Math.round(mode * 1 / 100);
            params.setMode(_mode);
        }
        // Number of results
        params.setResults(numberResults);
        return mApi.createStaticPlaylist(params).getSongs();
    }

    public List<Song> getSongsByKeys(String title, String artist) throws
            EchoNestException {
        SongParams params = new SongParams();
        params.setTitle(title);
        params.setArtist(artist);
        params.setResults(10);
        return mApi.searchSongs(params);
    }
}