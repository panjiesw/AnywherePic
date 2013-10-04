package com.panjiesw.anywherepic.test;

import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.ListView;
import com.jayway.android.robotium.solo.Solo;
import com.panjiesw.anywherepic.R;
import com.panjiesw.anywherepic.activities.MainActivity;
import com.panjiesw.anywherepic.fragments.HomeFragment;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testActivity() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testNavigationDrawer() {
        DrawerLayout drawerLayout = (DrawerLayout) solo.getView(R.id.drawer_layout);
        ListView drawerList = (ListView) solo.getView(R.id.list_drawer);
        assertNotNull(drawerLayout);
        assertNotNull(drawerList);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solo.clickOnActionBarHomeButton();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(drawerLayout.isDrawerOpen(drawerList));
        assertTrue(solo.waitForFragmentByTag(HomeFragment.TAG, 1000));
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

