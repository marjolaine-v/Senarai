package server;

import android.content.Context;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.ArtistParams;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.PlaylistParams;
import com.echonest.api.v4.Song;
import com.echonest.api.v4.SongParams;
import com.marjolainevericel.senarai.R;

import java.util.Collections;
import java.util.List;

public class EchoNestWrapper {
    private static EchoNestWrapper sInstance;
    private EchoNestAPI mApi;
    private List<String> genresList;

    public EchoNestWrapper(String key) {
        mApi = new EchoNestAPI(key);
    }

    public static EchoNestWrapper with(Context context) {
        if (sInstance == null) {
            sInstance = new EchoNestWrapper(context.getString(R.string.echo_nest_api));
        }
        return sInstance;
    }

    public List<Song> getRandomPlaylist(int numberResults) throws EchoNestException {
        PlaylistParams params = new PlaylistParams();
        params.addIDSpace("7digital-US");
        params.includeTracks();
        params.setType(PlaylistParams.PlaylistType.GENRE_RADIO);
        genresList = mApi.listGenres();
        Collections.shuffle(genresList);
        params.addGenre(genresList.get(0));
        params.setResults(numberResults);
        return mApi.createStaticPlaylist(params).getSongs();
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
            float _tempoMin = _tempo - (float) 50;
            float _tempoMax = _tempo + (float) 50;
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
            float _energyMin = (float)(energy * 1 / 100) - (float)0.2;
            float _energyMax = (float)(energy * 1 / 100) + (float)0.2;
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
        params.includeSongHotttnesss();
        params.setResults(numberResults);
        return mApi.createStaticPlaylist(params).getSongs();
    }

    public List<Song> getSongsByKeys(String title, String artist) throws
            EchoNestException {
        SongParams params = new SongParams();
        params.addIDSpace("7digital-US");
        params.setTitle(title);
        params.setArtist(artist);
        params.includeSongHotttnesss();
        params.includeAudioSummary();
        params.includeTracks();
        params.setResults(10);
        return mApi.searchSongs(params);
    }

    public List<Artist> getArtist(String name) throws EchoNestException {
        ArtistParams params = new ArtistParams();
        params.addName(name);
        params.includeHotttnesss();
        params.includeImages();
        params.includeSongs();
        params.setResults(1);
        return mApi.searchArtists(params);
    }
}