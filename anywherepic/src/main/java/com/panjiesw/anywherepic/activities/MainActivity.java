package com.panjiesw.anywherepic.activities;

import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.panjiesw.anywherepic.R;
import com.panjiesw.anywherepic.fragments.ContentFragment;
import com.panjiesw.anywherepic.fragments.HomeFragment;

/**
 * <p>
 *     Main Activity entry for AnywherePic Application.
 * <p/>
 * Blangszutan Project<BR/>
 * Project: AnywherePic<BR/>
 * Created At: 10/5/13 12:28 AM<BR/>
 *
 * @author Panjie SW
 */
public class MainActivity extends ActionBarActivity implements
        ContentFragment.ContentFragmentListener {
    private static final String TAG = "MainActivity";

    /**
     * ListView which holds the navigation list in DrawerLayout.
     */
    private ListView mDrawerList;
    /**
     * DrawerLayout which holds the ListView
     * and FrameLayout of this activity content.
     */
    private DrawerLayout mDrawerLayout;
    /**
     * ActionBarDrawerToggle ties together the proper interactions
     * between the sliding drawer and the action bar app icon.
     */
    ActionBarDrawerToggle mDrawerToggle;
    /**
     * The active ContentFragment which is currently
     * displayed in frame_content.
     */
    private ContentFragment mActiveContent;

    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews(savedInstanceState);
        initNavigationDrawer(savedInstanceState);
    }

    /**
     * Initialize this activity's general views.
     * For AnywherePic MainActivity this will:
     * <ul>
     * <li>Set the content view: R.layout.activity_main.</li>
     * <li>Setup the ActionBar behaviour.</li>
     * </ul>
     *
     * @param savedInstanceState Saved Instance
     */
    private void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        // Retrieve all required view reference.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_drawer);

        // Setup ActionBar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Home icon as navigation trigger.
        getSupportActionBar().setHomeButtonEnabled(true); // Enable the trigger behaviour.
    }

    /**
     * Initialize this activity's DrawerLayout
     * and its ListView as top view navigation.
     *
     * @param savedInstanceState Saved Instance
     */
    private void initNavigationDrawer(Bundle savedInstanceState) {
        mTitle = getTitle();
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.item_drawer_list, new String[]{"Home", "Where"}));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Setup ActionBarDrawerToggle.
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        // Set the ActionBarDrawerToggle above as listener
        // to our drawer layout.
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null)
            selectDrawerItem(0);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     *
     * @param savedInstanceState saved instance.
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectDrawerItem(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Trigger top level navigation which changes
     * content of the ContentFragment.
     *
     * @param position Position of the selected drawer list item.
     */
    private void selectDrawerItem(int position) {
        HomeFragment homeFragment = new HomeFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_content, homeFragment, HomeFragment.TAG)
                .commit();

        mActiveContent = null;
        mActiveContent = homeFragment;

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle("Home");
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void onContentAction(ContentFragment fragment, Object item) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
