package main;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.marjolainevericel.senarai.R;

import artist.ArtistCardFragment;
import playlists.PlaylistAddFormFragment;
import playlists.PlaylistCardFragment;
import playlists.PlaylistListFragment;
import songs.SongCardFragment;
import songs.SongListFragment;

public class HomeActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        PlaylistListFragment.OnPlaylistListListener,
        PlaylistAddFormFragment.OnPlaylistAddFormListener,
        SongListFragment.OnSongListListener
        /*PlaylistsListFragment.OnPlaylistsListListener,
        PlaylistFormFragment.OnPlaylistFormListener*/ {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private FragmentManager fragmentManager;


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
    }


    /***************************************************
     * MENU
     ***************************************************/
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        fragmentManager = getSupportFragmentManager();
        if(position == 0) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
        else if(position == 1) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaylistListFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
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
     * PLAYLISTS LIST
     ***************************************************/
    @Override
    public void onGoToAddPlaylistButtonClicked() {
        Log.d("APP", ">>>> Goto Add Playlist Form");
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaylistAddFormFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }


    /***************************************************
     * PLAYLIST ADD FORM
     ***************************************************/
    @Override
    public void onAddPlaylistButtonClicked(String title, String description) {
        Log.d("APP", ">>>> Add a playlist " + title);
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaylistCardFragment.newInstance(title, description))
                .replace(R.id.container_bottom, SongListFragment.newInstance(title, description))
                .addToBackStack(null)
                .commit();
    }


    /***************************************************
     * SONG LIST
     ***************************************************/
    @Override
    public void onSongListClicked(String id) {
        Log.d("APP", "Chanson cliquée : " + id);
        fragmentManager.beginTransaction()
                .replace(R.id.container, SongCardFragment.newInstance(id))
                .replace(R.id.container_bottom, ArtistCardFragment.newInstance("Linkin Park"))
                .addToBackStack(null)
                .commit();
    }
    /*@Override
    public void onPlaylistClicked(Playlist playlist) {
        Log.d("APP", playlist.toString());
    }

    @Override
    public void goToFormAddPlaylistButtonClicked() {
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaylistFormFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }*/


    /***************************************************
     * ADD PLAYLIST
     ***************************************************/
    /*@Override
    public void addPlaylistButtonClicked(String title, String description) {
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaylistFragment.newInstance(title, description))
                .addToBackStack(null)
                .commit();
    }*/


    /***************************************************
     * PLAYLIST
     ***************************************************/
}
