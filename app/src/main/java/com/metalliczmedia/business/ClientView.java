package com.metalliczmedia.business;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;

public class ClientView extends AppCompatActivity implements ActionBar.TabListener {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_view);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.ClientViewTabs);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return new ClientTab1();

                case 0:
                    return new ClientTab2();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Recee".toUpperCase(l);
                case 1:
                    return "Installed Products".toUpperCase(l);
            }
            return null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;    }
    CharSequence items[]={"Hangouts","Whatsapp","Gmail"};

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
          /*  case R.id.aboutthedev:
                Intent in=new Intent(getApplicationContext(),YourActivity.class);
                startActivity(in);
                return true;*/

            case R.id.likeapp:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                final String appPackageName = "com.metalliczmedia.business";

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                return true;

            case R.id.menu_item_share:
                showDialog(0);


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setIcon(R.drawable.share);
                builder1.setTitle("Share Using...");
                int checkedItem = -1;
                builder1.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which] == "Hangouts") {
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBody = "Hey There! Check out this amazing application \"Metallicz Media\" ! You should try it too. http://play.google.com/store/apps/details?id=com.metalliczmedia.business";
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Metallicz Media");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                            sharingIntent.setPackage("com.google.android.talk");
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        } else if (items[which] == "Whatsapp") {
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBody = "Hey There! Check out this amazing application \"Metallicz Media\" ! You should try it too. http://play.google.com/store/apps/details?id=com.metalliczmedia.business";
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Metallicz Media");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                            sharingIntent.setPackage("com.whatsapp");
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        } else if (items[which] == "Gmail") {
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBody = "Hey There! Check out this amazing application \"Metallicz Media\" ! \nYou should try it too. \n <a href=\"http://play.google.com/store/apps/details?id=com.metalliczmedia.business\">Metallicz Media</a>";
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Metallicz Media");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(shareBody));
                            sharingIntent.setPackage("com.google.android.gm");
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        } else
                            Toast.makeText(getApplicationContext(), "Wrong Choice", Toast.LENGTH_LONG).show();
                    }
                });
                return builder1.create();

            default: break;
        }
        return super.onCreateDialog(id);
    }

}


