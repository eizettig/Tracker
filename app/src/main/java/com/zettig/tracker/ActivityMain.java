package com.zettig.tracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zettig.tracker.Fragments.FragmentCharacterList;


public class ActivityMain extends AppCompatActivity implements CallbackActivity{


    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        replaceFragment(new FragmentCharacterList(),false);
    }

    @Override
    public void replaceFragment(Fragment fragment,boolean addToBackStack){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragment);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

}
