package main;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.echonest.api.v4.Song;
import com.marjolainevericel.senarai.R;

import java.util.List;

import artist.ArtistCardFragment;
import playlists.AddPlaylistRandomFormFragment;
import playlists.PlaylistCustom;
import playlists.AddPlaylistFormFragment;
import playlists.PlaylistCardFragment;
import playlists.AddPlaylistEmptyFormFragment;
import playlists.PlaylistListFragment;
import playlists.PlaylistsCustomAdapter;
import songs.SongAddFormFragment;
import songs.SongCardFragment;
import songs.SongListFragment;
import songs.SongListResultsFragment;
import songs.SongsAdapter;

public class HomeActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        HomeFragment.OnHomeListener,
        PlaylistListFragment.OnPlaylistListListener,
        AddPlaylistFormFragment.OnPlaylistAddFormListener,
        SongListFragment.OnSongListListener,
        SongAddFormFragment.OnSongAddFormListener,
        PlaylistCardFragment.OnPlaylistCardListener,
        SongCardFragment.OnSongCardListener,
        AlertDialogManager.OnAlertListener,
        SongsAdapter.OnSongsAdapterListener,
        PlaylistsCustomAdapter.OnPlaylistsCustomAdapterListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private FragmentManager fragmentManager;
    public PlaylistsCustomAdapter playlistsCustomAdapter;

    private int ADD_PLAYLIST_ALERT_DIALOG_ID = 0;


    /***************************************************
     * INITIALISATION
     ***************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        playlistsCustomAdapter = new PlaylistsCustomAdapter(this.getApplicationContext(), 30, HomeActivity.this);
    }


    /***************************************************
     * MENU
     ***************************************************/
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        fragmentManager = getSupportFragmentManager();
        if(position == 0) {
            changeFragment(HomeFragment.newInstance(), EmptyFragment.newInstance());
        }
        else if(position == 1) {
            changeFragment(PlaylistListFragment.newInstance(playlistsCustomAdapter), EmptyFragment.newInstance());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    /***************************************************
     * FRAGMENT MANAGER
     ***************************************************/
    public void changeFragment(Fragment fragment, Fragment fragment_bottom) {
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .replace(R.id.container_bottom, fragment_bottom)
                .addToBackStack(null)
                .commit();
    }

    /***************************************************
     * HOME
     ***************************************************/
    @Override
    public void onGoToPlaylistsListButtonClicked() {
        changeFragment(PlaylistListFragment.newInstance(playlistsCustomAdapter), EmptyFragment.newInstance());
    }
    @Override
    public void onGoToSearchButtonClicked() {
        Toast.makeText(getApplicationContext(), "La fonction de recherche n'est pas encore prévue dans l'application. Revenez plus tard !", Toast.LENGTH_LONG).show();
    }

    /***************************************************
     * PLAYLISTS LIST
     ***************************************************/
    @Override
    public void onPlaylistClicked(PlaylistCustom playlist) {
        changeFragment(PlaylistCardFragment.newInstance(playlist), SongListFragment.newInstance(this, HomeActivity.this, playlist));
    }
    @Override
    public void onGoToAddPlaylistButtonClicked() {
        AlertDialogManager alertDialogManager = new AlertDialogManager();
        alertDialogManager.newInstance(HomeActivity.this);
        alertDialogManager.setAlert(ADD_PLAYLIST_ALERT_DIALOG_ID);
        AlertDialog alertDialog = alertDialogManager.getBuilder().create();
        alertDialog.show();
    }


    /***************************************************
     * PLAYLIST ADD FORM
     ***************************************************/
    @Override
    public void onAddPlaylistButtonClicked(String title, String description, List<Song> list) {
        PlaylistCustom playlistCustom = new PlaylistCustom(title);
        playlistCustom.setDescription(description);
        SongsAdapter songs = new SongsAdapter(getApplicationContext(), HomeActivity.this);
        if(list != null && list.size() > 0) {
            songs.addAll(list);
            playlistCustom.setSongs(songs);
        }
        playlistsCustomAdapter.add(playlistCustom);
        changeFragment(PlaylistCardFragment.newInstance(playlistCustom), SongListFragment.newInstance(getApplicationContext(), HomeActivity.this, playlistCustom));
    }


    /***************************************************
     * PLAYLIST CARD
     ***************************************************/
    @Override
    public void onRemovePlaylistButtonClicked(PlaylistCustom playlist) {
        Toast.makeText(this, "La playlist " + playlist.getTitle() + " a été supprimée.", Toast.LENGTH_SHORT).show();
        playlistsCustomAdapter.remove(playlist);
        changeFragment(PlaylistListFragment.newInstance(playlistsCustomAdapter), EmptyFragment.newInstance());
    }


    /***************************************************
     * SONG LIST
     ***************************************************/
    @Override
    public void onSongListClicked(Song song) {
        changeFragment(SongCardFragment.newInstance(song), EmptyFragment.newInstance());
    }
    @Override
    public void onGoToAddSongButtonClicked() {
        Toast.makeText(getApplicationContext(), "L'ajout de chansons une à une n'est pas encore prévu dans l'application. Revenez plus tard !", Toast.LENGTH_LONG).show();
        //changeFragment(SongAddFormFragment.newInstance(), EmptyFragment.newInstance());
    }


    /***************************************************
     * SONG ADD FORM
     ***************************************************/
    @Override
    public void onAddSongButtonClicked(String title, String artist) {
        changeFragment(SongListResultsFragment.newInstance(title, artist), EmptyFragment.newInstance());
    }


    /***************************************************
     * SONG CARD
     ***************************************************/
    @Override
    public void onGoToArtistButtonClicked(Song song) {
        changeFragment(ArtistCardFragment.newInstance(song), EmptyFragment.newInstance());
    }


    /***************************************************
     * ALERTS
     ***************************************************/
    @Override
    public void onAlertPlaylistEmptyAddClicked() {
        changeFragment(AddPlaylistEmptyFormFragment.newInstance(), EmptyFragment.newInstance());
    }
    @Override
    public void onAlertPlaylistRandomAddClicked() {
        changeFragment(AddPlaylistRandomFormFragment.newInstance(getApplicationContext()), EmptyFragment.newInstance());
    }
    @Override
    public void onAlertPlaylistAddClicked() {
        changeFragment(AddPlaylistFormFragment.newInstance(getApplicationContext()), EmptyFragment.newInstance());
    }
}
