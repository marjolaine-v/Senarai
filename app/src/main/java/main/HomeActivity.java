package main;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.echonest.api.v4.Playlist;
import com.marjolainevericel.senarai.R;
import com.marjolainevericel.senarai.playlist.PlaylistFormFragment;
import com.marjolainevericel.senarai.playlist.PlaylistFragment;
import com.marjolainevericel.senarai.playlist.PlaylistsListFragment;

import java.util.HashMap;

public class HomeActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        PlaylistsListFragment.OnPlaylistsListListener,
        PlaylistFormFragment.OnPlaylistFormListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private FragmentManager fragmentManager;
    HashMap<Integer, String> fragmentMap;


    /***************************************************
     * INITIALISATION
     ***************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }


    /***************************************************
     * MENU
     ***************************************************/
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        fragmentManager = getSupportFragmentManager();
        if(position == 0) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commit();
        }
        else if(position == 1) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaylistsListFragment.newInstance())
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
    public void onPlaylistClicked(Playlist playlist) {
        Log.d("APP", playlist.toString());
    }

    @Override
    public void onButtonGoToFormAddPlaylistClicked() {
        Log.d("APP", ">>>>> On ajoute une playlist !");
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaylistFormFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onButtonAddPlaylistClicked(String title, String description) {
        Log.d("APP", ">>>>> La playlist est ajoutée !");
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaylistFragment.newInstance(title, description))
                .addToBackStack(null)
                .commit();
    }
}
