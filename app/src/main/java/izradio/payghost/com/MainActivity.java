package izradio.payghost.com;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import izradio.payghost.com.Docs.Files;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Fragment fragment;
    AlertDialog dialog;
    TabLayout tabLayout;
    private boolean fabExpanded = false;
    private FloatingActionButton fabSettings,fabFacebook;
    private LinearLayout layoutFabFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");

         OnAirFragment mainfragment = new OnAirFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, mainfragment).commit();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

         tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        fabFacebook = (FloatingActionButton) this.findViewById(R.id.fabFacebook);
        layoutFabFacebook = (LinearLayout) this.findViewById(R.id.layoutFabFacebook);

        final String facebook = "https://www.facebook.com/izradio/";
        fabFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebook));
                startActivity(browserIntent);
            }
        });

        fabSettings = (FloatingActionButton) this.findViewById(R.id.fabSetting);
        fabSettings.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fabSettings.setVisibility(View.GONE);
                layoutFabFacebook.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fabSettings.setVisibility(View.VISIBLE);
                    }
                }, 5000);

                return false;
            }
        });

        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });
        closeSubMenusFab();

    }

    private void closeSubMenusFab(){
        //textViewSocail.setVisibility(View.GONE);
        layoutFabFacebook.setVisibility(View.INVISIBLE);
        fabSettings.setImageResource(R.mipmap.ic_chat_white_24dp);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabFacebook.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fabSettings.setImageResource(R.mipmap.ic_close_white_24dp);
        fabExpanded = true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            android.app.AlertDialog dialog;
            android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.aboutus_dialog, null);
            mBuilder.setView(mView);

            dialog = mBuilder.create();
            dialog.show();
            return true;
        }
        else if(id == R.id.action_exit){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage(Html.fromHtml("<font color='#627984'>Are you sure you want to exit?</font>"))
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if(id == R.id.action_contact){
            startActivity(new Intent(MainActivity.this,ContactUs.class));
        }
        else if(id == R.id.action_logIn){
          startActivity(new Intent(MainActivity.this,LogInActivity.class));
        }
        else if(id == R.id.action_media){
            startActivity(new Intent(MainActivity.this,Files.class));
        }
        else if(id == R.id.action_subscribe){
            startActivity(new Intent(MainActivity.this,SubscribeActivity.class));
        }else if(id == R.id.action_donate){

          /*  Animation upAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
            upAnim.reset();
            findViewById(R.id.bankLayout).clearAnimation();
            findViewById(R.id.bankLayout).setAnimation(upAnim);
            findViewById(R.id.bankLayout).setAnimation(upAnim);
*/
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.bank_details_dialog_layout, null);
            mBuilder.setView(mView);
            mBuilder.setCancelable(true);
            dialog = mBuilder.create();
            dialog.show();
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position){
                case 0:
                    fragment = new OnAirFragment();
                    tabLayout.getTabAt(0).setIcon(R.drawable.tab_radio);
                    return fragment;

                case 1:
                    fragment = new GallryFragment();
                    tabLayout.getTabAt(1).setIcon(R.drawable.tab_gallery);
                    return fragment;

                case 2:
                    fragment = new videoFragment();
                    tabLayout.getTabAt(2).setIcon(R.drawable.tab_video);
                    return fragment;

                    case 3:
                    fragment = new NewsFragment();
                    tabLayout.getTabAt(3).setIcon(R.drawable.tab_news);
                    return fragment;
            }

          return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }
}
