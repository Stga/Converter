package com.allizgwats.converter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class converter_activity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment sideDrawer;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence fragmentTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter_layout);

        sideDrawer = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        fragmentTitle = getTitle();

        // Set up the drawer.
        sideDrawer.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        //Update the main content by replacing fragments based on selected drawer item
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment newUserSelectedFragment;

        if(position==NavigationDrawerFragment.DrawerItemPositions.TIMECONVERTER.ordinal()) {
            newUserSelectedFragment = new TimeConverterFragment().newInstance(position + 1);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, newUserSelectedFragment)
                    .commit();
        }
        else if(position==NavigationDrawerFragment.DrawerItemPositions.WEIGHTCONVERTER.ordinal()) {
            //TODO attach weight converter fragment if user selects weight converter from drawer
        }
        else if(position==NavigationDrawerFragment.DrawerItemPositions.DISTANCECONVERTER.ordinal()){
            //TODO attach distance converter fragment if user selects distance converter from drawer
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                fragmentTitle = getString(R.string.drawer_item_Time);
                break;
            case 2:
                fragmentTitle = getString(R.string.drawer_item_Weight);
                break;
            case 3:
                fragmentTitle = getString(R.string.drawer_item_Distance);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(fragmentTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!sideDrawer.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.converter_menu, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id==R.id.action_settings || super.onOptionsItemSelected(item);
    }

}
