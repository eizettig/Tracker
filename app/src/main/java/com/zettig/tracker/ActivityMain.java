package com.zettig.tracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.activeandroid.ActiveAndroid;
import com.zettig.tracker.Fragments.FragmentCharacterList;


public class ActivityMain extends AppCompatActivity implements CallbackActivity{


    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(onBackStackCanget);
        replaceFragment(new FragmentCharacterList(),false);
    }

    @Override
    public void replaceFragment(Fragment fragment,boolean addToBackStack){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//         transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.replace(R.id.container,fragment);
        if (addToBackStack) transaction.addToBackStack("backstack");
        transaction.commit();
    }

    private FragmentManager.OnBackStackChangedListener onBackStackCanget = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            if (fragmentManager.getBackStackEntryCount()>0){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackHomePressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
//        return super.onSupportNavigateUp();
        onBackPressed();
        return true;
    }
}
