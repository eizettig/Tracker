package com.zettig.tracker.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zettig.tracker.Model.Character;
import com.zettig.tracker.R;
import com.zettig.tracker.Utils.ApplicationManager;

/**
 * Created by Altair on 06.02.2017.
 */

public class FragmentCharacterEdit extends Fragment {

    TextInputEditText name;
    TextInputEditText inivitative;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_edit,container,false);
        name = (TextInputEditText) view.findViewById(R.id.edit_name);
        inivitative = (TextInputEditText) view.findViewById(R.id.edit_initiative);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.character_edit,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.done:
                addNewCharacter();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNewCharacter() {
        String n = name.getText().toString().trim();
        String i = inivitative.getText().toString().trim();
        if (TextUtils.isEmpty(n)){
            makeToast(R.string.error_no_name);
            return;
        }
        if (TextUtils.isEmpty(i)){
            makeToast(R.string.error_no_initiative);
            return;
        }
        Character c = new Character();
        c.setName(n);
        c.setInitiative(Integer.parseInt(i));
        if (c.save() > 0){
            getActivity().onBackPressed();
        } else {
            makeToast(R.string.error_insert_character);
        }
    }

    private void makeToast(int message){
        ApplicationManager.getInstance().showToast(message);
    }

}
