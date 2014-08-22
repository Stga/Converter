package com.allizgwats.converter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;


public class converter_activity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter_layout);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments based on selected drawer item
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment newUserSelectedFragment;

        if(position==NavigationDrawerFragment.DrawerItemPositions.TIMECONVERTERA.ordinal()) {
            newUserSelectedFragment = new TimeConverterFragmentA().newInstance(position);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, newUserSelectedFragment)
                    .commit();
        }
        else if(position==NavigationDrawerFragment.DrawerItemPositions.TIMECONVERTERB.ordinal()) {
            newUserSelectedFragment = new TimeConverterFragmentB().newInstance(position);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, newUserSelectedFragment)
                    .commit();
        }
        else if(position==NavigationDrawerFragment.DrawerItemPositions.MASSCONVERTER.ordinal()) {
            newUserSelectedFragment = new MassConverter().newInstance(position);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, newUserSelectedFragment)
                    .commit();
        }
        else if(position==NavigationDrawerFragment.DrawerItemPositions.LENGTHCONVERTER.ordinal()){
            newUserSelectedFragment = new LengthConverter().newInstance(position);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, newUserSelectedFragment)
                    .commit();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.drawer_item_TimeA);
                break;
            case 1:
                mTitle = getString(R.string.drawer_item_TimeB);
                break;
            case 2:
                mTitle = getString(R.string.drawer_item_Weight);
                break;
            case 3:
                mTitle = getString(R.string.drawer_item_Distance);
                break;
        }
    }

    private void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.converter_menu, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
}
