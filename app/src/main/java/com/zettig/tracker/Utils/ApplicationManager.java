package com.zettig.tracker.Utils;

import android.app.Application;
import android.view.Gravity;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.zettig.tracker.Model.Character;

/**
 * Created by zettig on 06.02.17.
 */

public class ApplicationManager extends Application {

    private static ApplicationManager instance;
    private ManagerShared sharedManager;

    public static ApplicationManager getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        Configuration.Builder config = new Configuration.Builder(getApplicationContext());
//        config.addModelClass(Character.class);
//        ActiveAndroid.initialize(config.create());
        ActiveAndroid.initialize(this);
    }

    /**
     * Показ всплывающего окна в контексте прилояжения
     *
     * @param resString - id строки из ресурсов
     */
    public void showToast(int resString) {
        Toast toast = Toast.makeText(getBaseContext(), resString, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * Показ всплывающего окна в контексте прилояжения
     *
     * @param resString - id строки из ресурсов
     */
    public void showToast(String resString) {
        Toast toast = Toast.makeText(getApplicationContext(), resString, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public ManagerShared getSharedManager() {
        if (sharedManager == null) {
            setSharedManager(new ManagerShared(getApplicationContext()));
        }
        return sharedManager;
    }

    public void setSharedManager(ManagerShared sharedManager) {
        this.sharedManager = sharedManager;
    }
}
