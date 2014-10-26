package main;

import android.app.ActionBar;
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

import artist.ArtistCardFragment;
import playlists.PlaylistCustom;
import playlists.PlaylistAddFormFragment;
import playlists.PlaylistCardFragment;
import playlists.PlaylistListFragment;
import playlists.PlaylistsAdapter;
import songs.SongAddFormFragment;
import songs.SongCardFragment;
import songs.SongListFragment;
import songs.SongListResultsFragment;

public class HomeActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        PlaylistListFragment.OnPlaylistListListener,
        PlaylistAddFormFragment.OnPlaylistAddFormListener,
        SongListFragment.OnSongListListener,
        SongAddFormFragment.OnSongAddFormListener,
        PlaylistCardFragment.OnPlaylistCardListener,
        SongListResultsFragment.OnSongListResultsListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private FragmentManager fragmentManager;
    public PlaylistsAdapter playlistsAdapter;


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

        playlistsAdapter = new PlaylistsAdapter(this.getApplicationContext(), 30);
    }


    /***************************************************
     * MENU
     ***************************************************/
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        fragmentManager = getSupportFragmentManager();
        if(position == 0) {
            changeFragment(R.id.container, HomeFragment.newInstance(), false);
        }
        else if(position == 1) {
            changeFragment(R.id.container, PlaylistListFragment.newInstance(playlistsAdapter), false);
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
    public void changeFragment(Integer container, Fragment fragment, Boolean isBottomContainer) {
        fragmentManager.beginTransaction()
                .replace(container, fragment)
                .addToBackStack(null)
                .commit();
        if(!isBottomContainer && fragmentManager.findFragmentById(R.id.container_bottom) != null) {
            fragmentManager.beginTransaction()
                    .remove(fragmentManager.findFragmentById(R.id.container_bottom))
                    .commit();
        }
    }

    /***************************************************
     * PLAYLISTS LIST
     ***************************************************/
    @Override
    public void onGoToAddPlaylistButtonClicked() {
        changeFragment(R.id.container, PlaylistAddFormFragment.newInstance(), false);
    }
    @Override
    public void onPlaylistClicked(PlaylistCustom playlist) {
        changeFragment(R.id.container, PlaylistCardFragment.newInstance(playlist), false);
        changeFragment(R.id.container_bottom, SongListFragment.newInstance(playlist), true);
    }


    /***************************************************
     * PLAYLIST ADD FORM
     ***************************************************/
    @Override
    public void onAddPlaylistButtonClicked(String title, String description, Boolean isRandomPlaylist) {
        PlaylistCustom playlist = new PlaylistCustom(title);
        playlist.setDescription(description);
        playlistsAdapter.add(playlist);
        changeFragment(R.id.container, PlaylistCardFragment.newInstance(playlist), false);
        changeFragment(R.id.container_bottom, SongListFragment.newInstance(playlist), true);
    }


    /***************************************************
     * PLAYLIST CARD
     ***************************************************/
    @Override
    public void onRemovePlaylistButtonClicked(PlaylistCustom playlist) {
        Toast.makeText(this, "La playlist " + playlist.getTitle() + " a été supprimée.", Toast.LENGTH_SHORT).show();
        playlistsAdapter.remove(playlist);
        changeFragment(R.id.container, PlaylistListFragment.newInstance(playlistsAdapter), false);
    }


    /***************************************************
     * SONG LIST
     ***************************************************/
    @Override
    public void onSongListClicked(String id) {
        //changeFragment(R.id.container, SongCardFragment.newInstance(id), false);
        //changeFragment(R.id.container_bottom, ArtistCardFragment.newInstance("Linkin Park"), true);
    }
    @Override
    public void onGoToAddSongButtonClicked() {
        changeFragment(R.id.container, SongAddFormFragment.newInstance(), false);
    }


    /***************************************************
     * SONG ADD FORM
     ***************************************************/
    @Override
    public void onAddSongButtonClicked(String title, String artist) {
        changeFragment(R.id.container, SongListResultsFragment.newInstance(title, artist), false);
    }


    /***************************************************
     * SONG LIST RESULTS
     ***************************************************/
    @Override
    public void onSongListResultsClicked(Song song) {
        Toast.makeText(getApplicationContext(), "Chanson clickée : " + song.getTitle(), Toast.LENGTH_LONG).show();
        changeFragment(R.id.container, SongCardFragment.newInstance(song), false);
        changeFragment(R.id.container_bottom, ArtistCardFragment.newInstance(song), true);
    }
}
