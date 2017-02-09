package com.zettig.tracker;

import android.support.v4.app.Fragment;

/**
 * Created by Altair on 06.02.2017.
 */

public interface CallbackActivity {
    void replaceFragment(Fragment fragment,boolean addToBackStack);
    void setTitle(String title);
    void hideKeyboard();
}
