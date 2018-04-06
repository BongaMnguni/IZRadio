package izradio.payghost.com;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.app.Fragment;

import android.app.FragmentTransaction;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import izradio.payghost.com.Delete.Delete;
import izradio.payghost.com.signup.Members;

public class AdminActivity extends AppCompatActivity {

    private Fragment fragment;
    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_gallery:
                    fragment = new AdminGalleryFragment();
                    FragmentManager fmGallery = getFragmentManager();
                    FragmentTransaction fragmentTransactionHome = fmGallery.beginTransaction();
                    fragmentTransactionHome.replace(R.id.content,fragment);
                    fragmentTransactionHome.commit();
                    return true;

                case R.id.navigation_news:
                    fragment = new AdminNewsFragment();
                    FragmentManager fmnNews = getFragmentManager();
                    FragmentTransaction fragmentTransactionNews = fmnNews.beginTransaction();
                    fragmentTransactionNews.replace(R.id.content,fragment);
                    fragmentTransactionNews.commit();
                    return true;

                case R.id.navigation_members:
                    fragment = new Members();
                    FragmentManager fmnMembers = getFragmentManager();
                    FragmentTransaction Members = fmnMembers.beginTransaction();
                    Members.replace(R.id.content,fragment);
                    Members.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setTitle("Dashboard");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragment = new AdminGalleryFragment();
        FragmentManager fmGallery = getFragmentManager();
        FragmentTransaction fragmentTransactionHome = fmGallery.beginTransaction();
        fragmentTransactionHome.replace(R.id.content,fragment);
        fragmentTransactionHome.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if(id == R.id.action_delete){
            fragmentManager.beginTransaction().replace(R.id.content,new Delete()).commit();

        }
        if (id == R.id.action_logout) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage(Html.fromHtml("<font color='#1b5e20'>Are you sure you want to logout?</font>"))
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(AdminActivity.this,LogInActivity.class));
                            finish();
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

        return super.onOptionsItemSelected(item);
    }

}
