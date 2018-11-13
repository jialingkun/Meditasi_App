package com.bekkostudio.meditasidanretret;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.bekkostudio.meditasidanretret.Article.ArticleFragment;
import com.bekkostudio.meditasidanretret.Chart.ProgressFragment;
import com.bekkostudio.meditasidanretret.Course.CourseFragment;
import com.bekkostudio.meditasidanretret.Timer.TimerFragment;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_timer:
                    fragment = new TimerFragment();
                    break;
                case R.id.navigation_article:
                    fragment = new ArticleFragment();
                    break;
                case R.id.navigation_community:
                    fragment = new CommunityFragment();
                    break;
                case R.id.navigation_course:
                    fragment = new CourseFragment();
                    break;
                case R.id.navigation_chart:
                    fragment = new ProgressFragment();
//                    Intent iProgress = new Intent(MainActivity.this, ProgressActivity.class);
//                    startActivity(iProgress);
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get meditation duration database
        Global.getDuration(getApplicationContext());

        //get note database
        Global.getNote(getApplicationContext());

        //loading the default fragment
        loadFragment(new TimerFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //get active retret database
        Global.getActiveRetret(getApplicationContext());

        //get active retret detail database
        Global.getActiveRetretDetail(getApplicationContext());

        //debug only, refresh database
        //Global.initializeRetretDetail();
        //Global.setActiveRetretDetail(getApplicationContext());


        //debug refresh retret end date
        //Global.setActiveRetret(getApplicationContext(),Global.activeRetretId,"");

    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (Global.currentWebview != null){
            if (Global.currentWebview.canGoBack()) {
                Global.currentWebview.goBack();
            } else {
                exitingApp();
            }
        } else {
            exitingApp();
        }
    }

    public void exitingApp(){
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments())
        {
            if (fragment != null)
            {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
